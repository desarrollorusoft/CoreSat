package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public interface IRedireccionamientoView extends Serializable {

	String getRedireccionamiento();
	
	IClaveValorView[] getParametros();
}
