package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;

public interface IArchivoConsultaView extends Serializable {

	String getNombreArchivo();
	
	IConsultaDetalleView getConsulta();
	
}
