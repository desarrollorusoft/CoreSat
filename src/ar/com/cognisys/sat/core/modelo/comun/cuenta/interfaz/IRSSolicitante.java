package ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

public interface IRSSolicitante {

	String getNombre();
	
	String getApellido();
	
	CaracterSAT getCaracter();
	
	String getCorreo();
	
	String getTelefono();
	
	String getCelular();

	String getNombreCompleto();

	boolean isCompleto();
}