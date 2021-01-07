package ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public interface IRegistroView extends Serializable {

	String getCorreo();

	String getClave();

	String getCuit();

	ICuentaView[] getCuentasAsociadas();
}