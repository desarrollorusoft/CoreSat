package ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz;

public interface ICuenta {

	String obtenerContribuyente();
	
	String obtenerCodigo();
	
	String obtenerTipo();
	
	boolean esCorrecto(String validacion);

}
