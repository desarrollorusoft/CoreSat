package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public interface IUsuarioCuentaView extends Serializable {

	String getNombreUsuario();
	
	ICuentaView getCuenta();
}