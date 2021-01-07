package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz;

import java.io.Serializable;

public interface IUsuarioAlertaView extends Serializable {

	String getNombreUsuario();
	
	IAlertaView getAlerta();
}
