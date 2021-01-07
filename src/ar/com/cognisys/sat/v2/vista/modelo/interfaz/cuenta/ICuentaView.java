package ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta;

import java.io.Serializable;

@Deprecated public interface ICuentaView extends Serializable {

	String getTipo();

	String getCodigo();

	String getDescripcion();
	
	String getAlias();

}
