package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz;

import java.util.Date;

public interface IAlertaGeneralDTO {

	String getTitulo();
	
	String getDescripcion();
	
	Date getFechaInicio();
	
	Date getFechaFin();
	
	String getRedireccion();
}
