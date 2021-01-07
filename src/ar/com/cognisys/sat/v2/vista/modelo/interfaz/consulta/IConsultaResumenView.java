package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;

public interface IConsultaResumenView extends Serializable {

	String getNumero();
	
	String getTipo();
	
	String getEstado();
	
	String getCategoria();
	
}
