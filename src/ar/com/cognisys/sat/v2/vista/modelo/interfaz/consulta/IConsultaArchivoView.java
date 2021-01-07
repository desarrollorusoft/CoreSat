package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IArchivoView;

public interface IConsultaArchivoView extends Serializable {

	String getNumeroConsulta();
	
	IArchivoView getArchivo();
	
}
