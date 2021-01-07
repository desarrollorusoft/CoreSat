package ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz;

public interface IRSActividad {

	String getCuit();

	String getCorreo();
	
	String getTelefono();
	
	String getCelular();
	
	String getClave();

	boolean isCompleto();
}