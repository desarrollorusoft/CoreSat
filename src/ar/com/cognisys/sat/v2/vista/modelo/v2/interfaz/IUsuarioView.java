package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaAsociadaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenPadronesView;

public interface IUsuarioView extends Serializable {

	String getCorreo();

	String getCuit();
	
	String getTelefono();
	
	String getTelefono2();
	
	boolean isNewsletter();
	
	ICuentaAsociadaView[] getCuentasAsociadas();
	
	IRegimenPadronesView getRegimenPadrones();

	IAlertaView[] getAlertas();
	
	boolean isMuestroRS();
	
	boolean isMigrado();

	String getAuthToken();

	void setAuthToken(String authToken);		
}