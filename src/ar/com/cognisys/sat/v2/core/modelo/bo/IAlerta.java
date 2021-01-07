package ar.com.cognisys.sat.v2.core.modelo.bo;

import java.util.Date;

public interface IAlerta {

	String getTitulo();
	
	String getDescripcion();
	
	Date getFechaInicio();
	
	Date getFechaFin();
	
	String getRedireccion();
	
}
