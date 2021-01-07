package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import java.util.Date;
import java.util.HashMap;

import ar.com.cognisys.sat.core.administrador.AdministradorTracePagoOnline;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.pago.FactoryDatosPago;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorPagosOnline;

public class GeneradorPagoTarjetaCredito extends GeneradorPagosOnline {

	private String descuento;
	
	public GeneradorPagoTarjetaCredito(String cuenta, String numeroComprobante, Date fechaVencimiento, Float monto, 
									   String tasa, String retorno, String descuento, String titular) {
		
		super(cuenta, numeroComprobante, fechaVencimiento, monto, tasa, retorno);
		this.setDescuento(descuento);
		this.setTitular(titular);
	}

	@Override
	public PaquetePago emitirPago(Sistema sistema) {
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		parametros.put("funcion", generarLLamadoAPI(sistema));
		
		return (new PaquetePago((new URLsPago()).getURL_TARJETA_CREDITO(), parametros));
	}
	
	public String generarLLamadoAPI(Sistema sistema) {
		
		String funcion = "";
		
		if (sistema == Sistema.SAT) {
			funcion = "decidirParam('_TASA_', '_CUENTA_', '_RAZONSOCIAL_', '_COMPROBANTE_', '_IMPORTE_', '_DESCUENTO_', '_RETORNO_')";
		} else if (sistema == Sistema.SAT_Mobile) {
			funcion = "decidirParamMobile('_TASA_', '_CUENTA_', '_RAZONSOCIAL_', '_COMPROBANTE_', '_IMPORTE_', '_DESCUENTO_', '_RETORNO_')";
		}
		
		funcion = funcion.replace("_TASA_", this.getTasa());
		funcion = funcion.replace("_CUENTA_", this.getCuenta());
		funcion = funcion.replace("_RAZONSOCIAL_", super.escapeTilde(this.getTitular()));
		funcion = funcion.replace("_COMPROBANTE_", this.getNumeroComprobante());
		funcion = funcion.replace("_IMPORTE_", super.ajustarMonto(this.getMonto()));
		funcion = funcion.replace("_DESCUENTO_", this.getDescuento());
		funcion = funcion.replace("_RETORNO_", this.getRetorno());
		
		try {
			AdministradorTracePagoOnline.registrarPago(FactoryDatosPago.generarInstancia(funcion, 
																						 this.getTasa(), 
																						 this.getCuenta(), 
																						 this.getTitular(), 
																						 this.getNumeroComprobante(), 
																						 new Date(), 
																						 this.getMonto()), 
													   this.getMedioPago());
		} catch (ExcepcionControladaError e) {
			// Esto tiene que llamar al Logger
		}
		
		return funcion;
	}
	
	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	
	@Override
	public MediosPago getMedioPago() {
		return MediosPago.TARJETAS_CREDITO;
	}
}