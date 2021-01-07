package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import ar.com.cognisys.sat.core.administrador.AdministradorBoletaPago;
import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.administrador.AdministradorSeguimientos;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.seguimiento.FactoryNavegacionSAT;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.MedioPago;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.PaquetePago;
import ar.com.cognisys.sat.v2.core.modelo.exception.CoreSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.view.ClaveValorView;
import ar.com.cognisys.sat.v2.core.modelo.view.RedireccionamientoView;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaDeudaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IPagoView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRedireccionamientoView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class FacadePagos {

	/**
	 * M�todo utilizado para la ejecuci�n del pago.
	 * Devuelve como resultado la URL asociada al tipo de pago recibido.
	 * 
	 * @param pago
	 * @return redireccionamiento
	 * @throws PersistenceSATException 
	 */
	public IRedireccionamientoView pagar(IPagoView pago) throws PersistenceSATException {
		
		IRedireccionamientoView redireccionamiento = null;
		try {
			MediosPago medioPago = MediosPago.obtener( pago.getMedio() );
			
			TipoCuentaView tcv = TipoCuentaView.obtener( pago.getCuenta().getTipo() );
			TiposCuentas tipoCuenta = tcv.getBo();
			
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(tipoCuenta, pago.getCuenta().getCodigo());
			Deuda deuda = AdministradorCuenta.recuperarDeudas( tipoCuenta, pago.getCuenta().getCodigo() );
			
			Deuda deudaRecalculada = AdministradorCuenta.recalcularDeuda( 
					cuenta.obtenerCodigo(), 
					tipoCuenta == TiposCuentas.CEMENTERIO ? ( ( CuentaCementerio ) cuenta ).getNumeroNomenclador() : null, 
					tipoCuenta, 
					pago.getFecha(), 
					this.obtenerListaCuotas( pago.getCuotas(), deuda.getListaCuotas() ), 
					pago.isCancelacionDeuda(), 
					deuda.isTieneDeudaLegales() );
			
			PaquetePago paquete = AdministradorBoletaPago.generarPagoOnline( 
					cuenta.obtenerCodigo(), 
					tipoCuenta, 
					cuenta.getDescripcion(), 
					cuenta.getContribuyente().getNombreApellido(), 
					pago.getCorreo(), 
					pago.getFecha(), 
					deudaRecalculada.getTotalCoutasGeneral().getTotal(), 
					deudaRecalculada.getNumeroComprobante(), 
					this.transformarMedioPago( medioPago ), 
					Sistema.SAT );
			
			redireccionamiento = this.crearRedireccionamientoView( paquete );
			
			registrarEstadistica( pago, medioPago, tipoCuenta);
			
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible realizar el pago", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible realizar el pago", e );
		}
		
		
		return redireccionamiento;
	}

	private void registrarEstadistica( IPagoView pago, MediosPago medioPago, TiposCuentas tipoCuenta) {
		String etiqueta = "";
		
		if ( tipoCuenta.sos(TiposCuentas.ABL) && medioPago == MediosPago.INTERBANKING)
			etiqueta = "abl_pagar_interbanking";
		else if ( tipoCuenta.sos(TiposCuentas.ABL) && medioPago == MediosPago.TARJETAS_CREDITO)
			etiqueta = "abl_pagar_credito";
		else if ( tipoCuenta.sos(TiposCuentas.ABL) && medioPago == MediosPago.PAGOMISCUENTAS)
			etiqueta = "abl_pagar_PMC";
		else if (  tipoCuenta.sos(TiposCuentas.ABL) && medioPago == MediosPago.LINK )
			etiqueta = "abl_pagar_link_pagos";
		
		else if (( tipoCuenta.sos(TiposCuentas.VEHICULOS) ||  tipoCuenta.sos(TiposCuentas.RODADOS)) && medioPago == MediosPago.TARJETAS_CREDITO)
			etiqueta = "automotores_pagar_credito";
		else if (( tipoCuenta.sos(TiposCuentas.VEHICULOS) ||  tipoCuenta.sos(TiposCuentas.RODADOS)) && medioPago == MediosPago.PAGOMISCUENTAS)
			etiqueta = "automotores_pagar_PMC";
		else if ( ( tipoCuenta.sos(TiposCuentas.VEHICULOS) ||  tipoCuenta.sos(TiposCuentas.RODADOS) ) && medioPago == MediosPago.LINK )
			etiqueta = "automotores_pagar_link_pagos";
		
		else if ( tipoCuenta.sos(TiposCuentas.CEMENTERIO) && medioPago == MediosPago.TARJETAS_CREDITO)
			etiqueta = "cementerio_pagar_credito";
		else if ( tipoCuenta.sos(TiposCuentas.CEMENTERIO) && medioPago == MediosPago.PAGOMISCUENTAS)
			etiqueta = "cementerio_pagar_PMC";
		else if (  tipoCuenta.sos(TiposCuentas.CEMENTERIO) && medioPago == MediosPago.LINK )
			etiqueta = "cementerio_pagar_link_pagos";
		
		if ( tipoCuenta.sos(TiposCuentas.COMERCIOS) && medioPago == MediosPago.INTERBANKING)
			etiqueta = "rs_pagar_interbanking";
		else if ( tipoCuenta.sos(TiposCuentas.COMERCIOS) && medioPago == MediosPago.TARJETAS_CREDITO)
			etiqueta = "rs_pagar_credito";
		else if ( tipoCuenta.sos(TiposCuentas.COMERCIOS) && medioPago == MediosPago.PAGOMISCUENTAS)
			etiqueta = "rs_pagar_PMC";
		else if (  tipoCuenta.sos(TiposCuentas.COMERCIOS) && medioPago == MediosPago.LINK )
			etiqueta = "rs_pagar_link_pagos";
		
		/* Registro para estadistica */
		AdministradorSeguimientos.registrarAccesoWeb(pago.getCorreo(), FactoryNavegacionSAT.generarInstancia(etiqueta));		
	}

	private IRedireccionamientoView crearRedireccionamientoView(PaquetePago paquete) {
		String redireccionamiento = paquete.getUrl();
		
		IClaveValorView[] parametros = this.cargarParametros( paquete );
		
		return new RedireccionamientoView( redireccionamiento, parametros );
	}

	private IClaveValorView[] cargarParametros(PaquetePago paquete) {
		if ( paquete.getParametros() == null || paquete.getParametros().isEmpty() )
			return null;
		
		List<IClaveValorView> lista = new ArrayList<IClaveValorView>();
		
		for (Entry<String, String> parametroEntry : paquete.getParametros().entrySet())
			lista.add( new ClaveValorView( parametroEntry.getKey(), parametroEntry.getValue() ) );
		
		return lista.toArray( new IClaveValorView[] {} );
	}

	private MedioPago transformarMedioPago(MediosPago medioPago) {
		switch ( medioPago ) {
		case INTERBANKING:
			return MedioPago.interbanking;
		case LINK:
			return MedioPago.link_pagos;
		case PAGOMISCUENTAS:
			return MedioPago.pmc;
		case TARJETAS_CREDITO:
			return MedioPago.tarjeta_credito;
		default:
			break;
		}
		
		return null;
	}

	private List<Cuota> obtenerListaCuotas(ICuotaDeudaView[] cuotasDeuda, List<Cuota> listaCuotas) {
		List<Cuota> lista = new ArrayList<Cuota>();
		
		for (ICuotaDeudaView cuotaDeuda : cuotasDeuda)
			lista.add( this.buscarCuota( cuotaDeuda, listaCuotas ) );
		
		return lista;
	}

	private Cuota buscarCuota(ICuotaDeudaView cuotaDeuda, List<Cuota> listaCuotas) {
		for (Cuota cuota : listaCuotas)
			if ( cuotaDeuda.getTasa().equals( cuota.getTasa() ) 
					&& cuotaDeuda.getPeriodo().equals( cuota.getPeriodo() ) )
				return cuota;
				
		return null;
	}

	public String generarRecibo(IPagoView pago) throws PersistenceSATException, CoreSATException {
		String ruta = null;

		try {
			TipoCuentaView tipoCuentaView = TipoCuentaView.obtener( pago.getCuenta().getTipo() );
			TiposCuentas tipoCuenta = tipoCuentaView.getBo();
			
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(tipoCuenta, pago.getCuenta().getCodigo());
			Deuda deuda = AdministradorCuenta.recuperarDeudas( tipoCuenta, pago.getCuenta().getCodigo() );
			
			Deuda deudaRecalculada = AdministradorCuenta.recalcularDeuda( 
					cuenta.obtenerCodigo(), 
					tipoCuenta == TiposCuentas.CEMENTERIO ? ( ( CuentaCementerio ) cuenta ).getNumeroNomenclador() : null, 
					tipoCuenta, 
					pago.getFecha(), 
					this.obtenerListaCuotas( pago.getCuotas(), deuda.getListaCuotas() ), 
					pago.isCancelacionDeuda(), 
					deuda.isTieneDeudaLegales() );
			
			ruta = AdministradorBoletaPago.generarRecibo( cuenta.obtenerCodigo(), 
														  tipoCuenta, 
														  deudaRecalculada.getNumeroComprobante(), 
														  pago.getFecha(), 
														  deudaRecalculada.getTotalCoutasGeneral().getTotal(), 
														  deudaRecalculada.getListaCuotas(), 
														  pago.getCorreo(), 
														  cuenta.getDescripcion(), 
														  cuenta.getContribuyente().getNombreApellido() );
			
			
			String etiqueta = "";
			if ( tipoCuenta.sos( TiposCuentas.ABL ) ) {
				etiqueta = "abl_generacion_recibo";
			} else if (tipoCuenta.sos( TiposCuentas.VEHICULOS) || tipoCuenta.sos( TiposCuentas.RODADOS ) ) {
				etiqueta = "automotores_generacion_recibo";
			} else if ( tipoCuenta.sos( TiposCuentas.CEMENTERIO ) ) {
				etiqueta = "cementerio_generacion_recibo";
			} else if ( tipoCuenta.sos( TiposCuentas.COMERCIOS ) ) {
				etiqueta = "rs_generacion_recibo";
			}
			
			/* Registro para estadistica */
			AdministradorSeguimientos.registrarAccesoWeb(pago.getCorreo() , FactoryNavegacionSAT.generarInstancia(etiqueta));
			
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible generar el recibo", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible generar el recibo", e );
		}
		
		return ruta;
	}

}
