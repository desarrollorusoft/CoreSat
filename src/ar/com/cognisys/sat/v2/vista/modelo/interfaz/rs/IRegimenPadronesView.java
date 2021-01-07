package ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs;

import java.io.Serializable;

@Deprecated public interface IRegimenPadronesView extends Serializable {

	IRegimenView getRegimen();

	IPadronRSView[] getPadrones();
	
	String getAuthToken();

	void setAuthToken(String authToken);
}
