package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

public interface ISolicitanteView extends Serializable {

	String getNombre();
	
	String getApellido();
	
	String getCaracter();
	
	String getCorreo();
	
	String getTelefono();
	
	String getCelular();
}