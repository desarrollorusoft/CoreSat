package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz;

import java.io.Serializable;
import java.util.Date;

public interface IAlertaView extends Serializable {

	Date getStart();

	Date getEnd();

	String getTitle();
	
	String getClave();
	
	String getRedireccion();
}
