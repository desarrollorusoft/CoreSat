package ar.com.cognisys.sat.core.administrador;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import ar.com.cognisys.sat.core.correo.EnviadorCorreo;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeRecibo;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboABL;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboCementerio;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboComercio;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboNatatorios;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboRodados;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboVehiculos;

public class AdministradorGeneracionRecibos {

	public static String generarReciboABL(CuentaABL cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
										  String numeroComprobante) throws ExcepcionControladaError {
		
		return generarABL(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getPathArchibo();
	}
	
	public static byte[] generarReciboABL_Datos(CuentaABL cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
			  							  String numeroComprobante) throws ExcepcionControladaError {

		return generarABL(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getDatos();
	}
	
	private static GeneradorReciboABL generarABL(CuentaABL cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
			  										   String numeroComprobante) throws ExcepcionControladaError {
		
		GeneradorReciboABL g = new GeneradorReciboABL(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return g;
	}

	public static String generarReciboCementerio(CuentaCementerio cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas,
												 String numeroComprobante) throws ExcepcionControladaError {
		
		return generarCementerio(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getPathArchibo();
	}
	
	public static byte[] generarReciboCementerio_Datos(CuentaCementerio cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas,
					 							 	   String numeroComprobante) throws ExcepcionControladaError {
			
		GeneradorReciboCementerio g = new GeneradorReciboCementerio(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return generarCementerio(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getDatos();
	}
	
	private static GeneradorReciboCementerio generarCementerio(CuentaCementerio cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas,
					 							 			   String numeroComprobante) throws ExcepcionControladaError {
		
		GeneradorReciboCementerio g = new GeneradorReciboCementerio(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return g;
	}
	
	public static String generarReciboVehiculo(CuentaVehiculos cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
										  	   String numeroComprobante) throws ExcepcionControladaError {
	
		return generarVehiculos(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getPathArchibo();
	}
	
	public static byte[] generarReciboVehiculo_Datos(CuentaVehiculos cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
				  									 String numeroComprobante) throws ExcepcionControladaError {
	
		return generarVehiculos(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getDatos();
	}
	
	private static GeneradorReciboVehiculos generarVehiculos(CuentaVehiculos cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
							   					 String numeroComprobante) throws ExcepcionControladaError {
	
		GeneradorReciboVehiculos g = new GeneradorReciboVehiculos(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return g;
	}

	public static String generarReciboRodado(CuentaRodados cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas,
											 String numeroComprobante) throws ExcepcionControladaError {
		
		return generarRodados(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getPathArchibo();
	}
	
	public static byte[] generarReciboRodado_Datos(CuentaRodados cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
						 						   String numeroComprobante) throws ExcepcionControladaError {
		
		return generarRodados(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getDatos();
	}
	
	private static GeneradorReciboRodados generarRodados(CuentaRodados cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas, 
					 									 String numeroComprobante) throws ExcepcionControladaError {
		
		GeneradorReciboRodados g = new GeneradorReciboRodados(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return g;
	}
	
	public static String generarReciboComercios(CuentaComercios cuenta, String correo, Date fecha, Float monto, List<Cuota> listaCuotas,
					 						    String numeroComprobante) throws ExcepcionControladaError {
		
		return generarComercios(cuenta, correo, fecha, monto, listaCuotas, numeroComprobante).getPathArchibo();
	}
	
	private static GeneradorReciboComercio generarComercios(CuentaComercios cuenta, String correo, Date fecha, Float monto, 
															List<Cuota> listaCuotas, String numeroComprobante) throws ExcepcionControladaError {
		
		GeneradorReciboComercio g = new GeneradorReciboComercio(cuenta, numeroComprobante, fecha, monto, listaCuotas);
		g.generarRecibo();
		
		return g;
	}
	
	public static String generarReciboNatatorios(CuentaPileta usuario) throws ExcepcionControladaError {
	
		return generarNatatorios(usuario).getPathArchibo();
	}
	
	public static byte[] generarReciboNatatorios_Datos(CuentaPileta usuario) throws ExcepcionControladaError {
	
		return generarNatatorios(usuario).getDatos();
	}
	
	private static GeneradorReciboNatatorios generarNatatorios(CuentaPileta usuario) throws ExcepcionControladaError {
	
		GeneradorReciboNatatorios g = new GeneradorReciboNatatorios(usuario, 
																	usuario.getComprobante().toString().replace("-",""), 
																	usuario.getVencimiento(), 
																	usuario.getDeuda());
		g.generarRecibo();
		
		return g;
	}

	public static void enviarMail(String url, String correo) throws ExcepcionControladaError {		
		try {
			Date f = new Date();
			String fecha = new SimpleDateFormat("dd-MM-yyyy_HH.mm").format(f);
			String nombreArchivo = f.getTime() + "";
			
			File file = File.createTempFile(nombreArchivo, ".pdf");
			FileUtils.copyURLToFile(new URL(url), file, 10000, 10000);			
			
			EnviadorCorreo.getInstance().enviarMensajeProveedorAdjunto(new MensajeRecibo(correo, file.getAbsolutePath(), nombreArchivo+"-"+fecha+".pdf"));
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudo enviar la boleta", e);
		}
	}
}