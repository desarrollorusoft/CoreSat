package ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs;

import java.io.Serializable;

@Deprecated public interface ISolicitanteView extends Serializable {

	String getNombre();
	
	String getApellido();
	
	String getCaracter();
	
	String getCorreo();
	
	String getTelefono();
	
	String getCelular();

	String getNombreCompleto();
}