package ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs;

import java.io.Serializable;

@Deprecated public interface IActividadView extends Serializable {

	String getCuit();

	String getCorreo();
	
	String getTelefono();
	
	String getCelular();
	
	String getClave();
}