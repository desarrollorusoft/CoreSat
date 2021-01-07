package ar.com.cognisys.sat.core.administrador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.MedioPago;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.PaquetePago;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.exception.CoreSATException;

public class AdministradorBoletaPago extends Administrador {

	private static final long serialVersionUID = -3961753145519409023L;
	
	/**
	 * Metodo usado para Mobile, que permite generar el recibo correspondiente a cada tributo.
	 * Luego de generar el archivo, lo envia por mail al correo proporcionado.
	 * 
	 * @param cuenta
	 * @param tipoCuenta
	 * @param numeroComprobante
	 * @param fecha
	 * @param total
	 * @param listaCuotas
	 * @param correo
	 * @return 
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static String generarRecibo(String cuenta, TiposCuentas tipoCuenta, String numeroComprobante, Date fecha, Float total, 
									   List<Cuota> listaCuotas, String correo, String titular, String descripcion) throws CoreSATException, ExcepcionControladaAlerta, ExcepcionControladaError {
		
		try {
			String path = obtenerRecibo(cuenta, tipoCuenta, numeroComprobante, fecha, total, listaCuotas, correo, titular, descripcion);
			
			String directorioFtp = AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_RECIBOS_FTP" ) + "/" + cuenta + "/";
			
			SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-YYYY" );
			
			String nombre = cuenta + "-" + sdf.format( new Date() ) + "-" + new Date().getTime() + ".pdf";
			
			AdministradorArchivo.guardarEnFTP( directorioFtp , nombre, new FileInputStream( path ) );
			
			return AdministradorProperties.getInstancia().getPropiedad( "ENCABEZADO_FTP_PUBLICO_DESCARGAS" ) + directorioFtp + nombre;
		} catch ( FileNotFoundException e ) {
			throw new ExcepcionControladaError( "Ocurrio un error al recuperar el archivo", e );
		}		
	}
	
	/**
	 * Metodo usado en el SAT Web
	 * 
	 * @param cuenta
	 * @param tipoCuenta
	 * @param numeroComprobante
	 * @param fecha
	 * @param total
	 * @param listaCuotas
	 * @param correo
	 * @param titular
	 * @param descripcion
	 * @return
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static String obtenerRecibo(String cuenta, TiposCuentas tipoCuenta, String numeroComprobante, Date fecha, Float total, 
			 						   List<Cuota> listaCuotas, String correo, String titular, String descripcion) throws ExcepcionControladaAlerta, ExcepcionControladaError {	
		
		String pathArchivo = "";
		
		if (tipoCuenta == TiposCuentas.ABL) {
			pathArchivo = generarReciboAbl(cuenta, descripcion, titular, correo, fecha, total, listaCuotas, numeroComprobante);
			
		} else if (tipoCuenta == TiposCuentas.CEMENTERIO) {
			pathArchivo = generarReciboCementerio(cuenta, correo, fecha, total, listaCuotas, numeroComprobante);
			
		} else if (tipoCuenta == TiposCuentas.VEHICULOS) {
			pathArchivo = generarReciboVehiculo(cuenta, correo, fecha, total, listaCuotas, numeroComprobante);
			
		} else if (tipoCuenta == TiposCuentas.RODADOS) {
			pathArchivo = generarReciboRodado(cuenta, correo, fecha, total, listaCuotas, numeroComprobante);
			
		}  else if (tipoCuenta == TiposCuentas.COMERCIOS) {
			pathArchivo = generarReciboComercio(cuenta, descripcion, titular, correo, fecha, total, listaCuotas, numeroComprobante);
		}
		
		return pathArchivo;
	}
	
	private static String generarReciboAbl(String numeroCuenta, String descripcion, String titular, String correo, Date fecha,
										   Float total, List<Cuota> listaCuotas, String numeroComprobante) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		CuentaABL c = (CuentaABL) AdministradorCuenta.buscarCuenta(TiposCuentas.ABL, numeroCuenta);
		return AdministradorGeneracionRecibos.generarReciboABL(c, correo, fecha, total, listaCuotas, numeroComprobante);
	}

	private static String generarReciboCementerio(String numeroCuenta, String correo, Date fecha, Float total, List<Cuota> listaCuotas,
				  								  String numeroComprobante) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		CuentaCementerio c = (CuentaCementerio) AdministradorCuenta.buscarCuenta(TiposCuentas.CEMENTERIO, numeroCuenta);
		return AdministradorGeneracionRecibos.generarReciboCementerio(c, correo, fecha, total, listaCuotas, numeroComprobante);
	}
	
	private static String generarReciboVehiculo(String dominio, String correo, Date fecha, Float total, List<Cuota> listaCuotas, 
												String numeroComprobante) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		CuentaVehiculos c = (CuentaVehiculos) AdministradorCuenta.buscarCuenta(TiposCuentas.VEHICULOS, dominio);
		return AdministradorGeneracionRecibos.generarReciboVehiculo(c, correo, fecha, total, listaCuotas, numeroComprobante);
	}
	
	private static String generarReciboRodado(String dominio, String correo, Date fecha, Float total, List<Cuota> listaCuotas, 
											  String numeroComprobante) throws ExcepcionControladaError, ExcepcionControladaAlerta {
	
		CuentaRodados c = (CuentaRodados) AdministradorCuenta.buscarCuenta(TiposCuentas.RODADOS, dominio);
		return AdministradorGeneracionRecibos.generarReciboRodado(c, correo, fecha, total, listaCuotas, numeroComprobante);
	}
	
	private static String generarReciboComercio(String numeroCuenta, String descripcion, String titular, String correo, Date fecha,
				   						   		Float total, List<Cuota> listaCuotas, String numeroComprobante) throws ExcepcionControladaError, ExcepcionControladaAlerta {
	
		CuentaComercios c = (CuentaComercios) AdministradorCuenta.buscarCuenta(TiposCuentas.COMERCIOS, numeroCuenta);
		return AdministradorGeneracionRecibos.generarReciboComercios(c, correo, fecha, total, listaCuotas, numeroComprobante);
	}
	
	/**
	 * Metodo usado por Mobile, que permite obtener los parametros para permitirle al usuario realizar el pago online.
	 * @param cuenta
	 * @param tipoCuenta
	 * @param descripcion
	 * @param titular
	 * @param correo
	 * @param fecha
	 * @param total
	 * @param numeroComprobante
	 * @param mp
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static PaquetePago generarPagoOnline(String cuenta, TiposCuentas tipoCuenta, String descripcion, String titular, String correo,
			 							   		Date fecha, Float total, String numeroComprobante, MedioPago mp, Sistema sistema) throws ExcepcionControladaError {
		PaquetePago pp = null;
		
		if (mp == MedioPago.tarjeta_credito)
			pp = AdministradorPagosOnline.generarPagoTarjetaCredito(cuenta, tipoCuenta, titular, fecha, total, numeroComprobante, sistema);
		
		else if (mp == MedioPago.interbanking)
			pp = AdministradorPagosOnline.generarPagoInterbanking(cuenta, tipoCuenta, titular, fecha, total, numeroComprobante, correo, sistema);
		
		else if (mp == MedioPago.pmc)
			pp = AdministradorPagosOnline.generarPagoMisCuentas(cuenta, tipoCuenta, titular, fecha, total, numeroComprobante, sistema);
			
		else if ( mp == MedioPago.link_pagos)
			pp = AdministradorPagosOnline.generarPagoLinkPagos(tipoCuenta, cuenta, titular, numeroComprobante, fecha, total, sistema);

		else if (mp == MedioPago.mercado_pago)
			pp = AdministradorPagosOnline.generarPagoMercadoPago(cuenta, tipoCuenta, titular, fecha, total, numeroComprobante, correo, sistema);

		return pp;
	}
}