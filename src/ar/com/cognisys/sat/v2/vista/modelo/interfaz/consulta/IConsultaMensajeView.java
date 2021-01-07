package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;

public interface IConsultaMensajeView extends Serializable {

	String getNumeroConsulta();
	
	IMensajeView getMensaje();
	
}
