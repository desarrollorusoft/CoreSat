package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta;

import java.io.Serializable;

public interface ICuentaView extends Serializable {

	String getTipo();

	String getCodigo();

	String getDescripcion();
	
	String getAlias();

}
