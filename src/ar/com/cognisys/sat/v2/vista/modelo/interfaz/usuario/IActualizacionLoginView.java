package ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario;

import java.io.Serializable;

public interface IActualizacionLoginView extends Serializable {

	String getNombreUsuarioAnterior();
	
	String getNombreUsuario();

	String getClave();
}
