package ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario;

import java.io.Serializable;

public interface IPerfilView extends Serializable {

	String getCuit();

	String getCorreo();

	String getTelefono();

	String getTelefono2();
	
	boolean isNewsletter();
}
