package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;
import ar.com.cognisys.sat.core.modelo.comun.pago.DatosPago;
import ar.com.cognisys.sat.core.modelo.dao.DaoPagosOnline;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.*;

public class AdministradorPagosOnline {

	public static PaquetePago generarPagoTarjetaCredito(String cuenta, TiposCuentas tipoCuenta, String titular, Date fecha, Float monto,
														String numeroComprobante, Sistema sistema) {
		
		GeneradorPagoTarjetaCredito g = new GeneradorPagoTarjetaCredito(cuenta, 
																		numeroComprobante, 
																		fecha, 
																		monto, 
																		recuperarTasaPago(tipoCuenta), 
																		"https://www.vicentelopez.gov.ar/SAT/pages/exito/finalizacionTransaccion.xhtml", 
																		"0",
																		titular);
		return g.emitirPago(sistema);
	}
	
	public static PaquetePago generarPagoInterbanking(String cuenta, TiposCuentas tipoCuenta, String titular, Date fecha, Float monto,
													  String numeroComprobante, String correo, Sistema sistema) throws ExcepcionControladaError {

		GeneradorPagoInterbanking g = new GeneradorPagoInterbanking(cuenta, 
																	numeroComprobante, 
																	fecha, 
																	monto, 
																	recuperarTasaPago(tipoCuenta),
																	"https://www.vicentelopez.gov.ar/SAT/pages/exito/finalizacionTransaccion.xhtml", 
																	correo,
																	titular);
		return g.emitirPago(sistema);
	}

	public static PaquetePago generarPagoMisCuentas(String cuenta, TiposCuentas tipoCuenta, String titular, Date fecha, Float monto,
													String numeroComprobante, Sistema sistema) throws ExcepcionControladaError {
		
		GeneradorPagoMisCuentas g = new GeneradorPagoMisCuentas(cuenta, 
																numeroComprobante, 
																fecha, 
																monto, 
																recuperarTasaPago(tipoCuenta), 
																"https://www.vicentelopez.gov.ar/SAT/pages/exito/finalizacionTransaccion.xhtml",
																recuperarTributo(tipoCuenta),
																titular);
		return g.emitirPago(sistema);
	}
	
	public static String recuperarTasaPago(TiposCuentas tipoCuenta) {
		if (tipoCuenta == TiposCuentas.COMERCIOS)
			return "SEH";
		if (tipoCuenta == TiposCuentas.VEHICULOS)
			return "AUTOMOTORES";
		else
			return tipoCuenta.name().toUpperCase();
	} 
	
	public static String recuperarTributo(TiposCuentas tipoCuenta) {
		
		if (tipoCuenta == TiposCuentas.ABL) {
			return tipoCuenta.getNombre();
			
		} else if (tipoCuenta == TiposCuentas.CEMENTERIO) {
			return tipoCuenta.getNombre().toUpperCase();
		
		} else if (tipoCuenta == TiposCuentas.VEHICULOS) {
			return "Impuesto automotor";
		
		} else if (tipoCuenta == TiposCuentas.RODADOS) {
			return "Impuesto rodado";
		}
		
		return tipoCuenta.getNombre();
	}
	
	public static List<DatosPago> recuperarListaDatosPago(Integer inicio, Integer cantidad, String medioPago, 
			Float importeDesde, Float importeHasta, Date fechaDesde, Date fechaHasta, String filtro) throws ExcepcionControlada {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPagosOnline(con)).recuperarListaDatosPago(inicio, 
																	 cantidad,
																	 medioPago, 
																	 importeDesde, 
																	 importeHasta, 
																	 fechaDesde, 
																	 fechaHasta, 
																	 filtro);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static Integer recuperarCantidadDatosPago(String medioPago, Float importeDesde, 
			Float importeHasta, Date fechaDesde, Date fechaHasta, String filtro) throws ExcepcionControlada {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPagosOnline(con)).recuperarCantidadDatosPago(medioPago, 
																		 importeDesde, 
																		 importeHasta, 
																		 fechaDesde, 
																		 fechaHasta, 
																		 filtro);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static Float recuperarMaximoImporte(String medioPago, Date fechaDesde, Date fechaHasta, String filtro) throws ExcepcionControlada {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPagosOnline(con)).recuperarMaximoImporte(medioPago, 
																	 fechaDesde, 
																	 fechaHasta, 
																	 filtro);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static PaquetePago generarPagoLinkPagos(TiposCuentas tipoCuenta,String cuenta,String titular, 
												   String numeroComprobante,Date fecha, Float total, Sistema sistema) {
		
		GeneradorPagoLinkPagos g = new GeneradorPagoLinkPagos(recuperarTasaPago(tipoCuenta),
															  cuenta,
															  titular,
															  numeroComprobante,
															  fecha,
															  total,
															  "https://www.vicentelopez.gov.ar/SAT/pages/exito/finalizacionTransaccion.xhtml");
		
		return g.emitirPago(sistema);
	}


	public static PaquetePago generarPagoMercadoPago(String cuenta, TiposCuentas tipoCuenta, String titular, Date fecha, Float monto,
													 String numeroComprobante, String correo, Sistema sistema) {

		GeneradorPagoMercadoPago g = new GeneradorPagoMercadoPago(recuperarTasaPago(tipoCuenta),
																	cuenta,
																	titular,
																	numeroComprobante,
																	fecha,
																	monto,
																	"https://www.vicentelopez.gov.ar/SAT/pages/exito/finalizacionTransaccion.xhtml",
																	correo);
		return g.emitirPago(sistema);
	}
}