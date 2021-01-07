package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

public interface IRechazoPadronView extends Serializable {

	String getCuit();
	
	String getCodigoPadron();
	
	ICodigoErrorValidacionRSView[] getErrores();
}