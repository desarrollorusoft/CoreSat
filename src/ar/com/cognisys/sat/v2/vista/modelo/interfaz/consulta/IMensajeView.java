package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;
import java.util.Date;

public interface IMensajeView extends Serializable {

	Date getFecha();
	
	String getRemitente();
	
	String getTexto();
	
	boolean isEmisor();

}
