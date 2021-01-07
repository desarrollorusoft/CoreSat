package ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta;

import java.io.Serializable;

public interface ICuentaBusquedaView extends ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView, Serializable {

	String getContribuyente();
	
	String getDigitoVerificador();
	
	void setContribuyente(String contribuyente);
	
	void setDescripcion(String descripcion);
}