package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

public interface IRegimenView extends Serializable {
	
	IActividadView getActividad();
	
	ISolicitanteView getSolicitante();

	boolean isCompleto();
	
	boolean isRechazado();
}