package ar.com.cognisys.sat.core.modelo.factory.pago;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.pago.DatosPago;


public class FactoryDatosPago {

	public static DatosPago generarInstancia() {
		
		DatosPago datos = new DatosPago();
		
		return datos;
	}
	
	public static DatosPago generarInstancia(String dato, String tasa, String cuenta, String razonSocial, 
			String recibo, Date fechaPago, Float valor) {
		
		DatosPago datos = generarInstancia();
		
		datos.setDatos(dato);
		datos.setTasa(tasa);
		datos.setCuenta(cuenta);
		datos.setRazonSocial(razonSocial);
		datos.setRecibo(recibo);
		datos.setFechaPago(fechaPago);
		datos.setValor(valor);
		
		return datos;
	}
	
	public static DatosPago generarInstanciaCompleta(Long id, String tipo, String dato, String tasa, String cuenta, 
			String razonSocial, String recibo, Date fechaPago, Float valor, Date fechaSistema) {
		
		DatosPago datos = generarInstancia();
		
		datos.setId(id);
		datos.setTipo(tipo);
		datos.setDatos(dato);
		datos.setTasa(tasa);
		datos.setCuenta(cuenta);
		datos.setRazonSocial(razonSocial);
		datos.setRecibo(recibo);
		datos.setFechaPago(fechaPago);
		datos.setValor(valor);
		datos.setFechaSistema(fechaSistema);
		
		return datos;
	}

	@Deprecated
	public static DatosPago generarInstancia(String dato, String tasa, String cuenta, String razonSocial, 
			String recibo, Float valor) {
		
		DatosPago datos = generarInstancia();
		
		datos.setDatos(dato);
		datos.setTasa(tasa);
		datos.setCuenta(cuenta);
		datos.setRazonSocial(razonSocial);
		datos.setRecibo(recibo);
		datos.setValor(valor);
		
		return datos;
	}
}