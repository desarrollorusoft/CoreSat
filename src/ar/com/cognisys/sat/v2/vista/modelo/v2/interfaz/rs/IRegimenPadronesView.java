package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

public interface IRegimenPadronesView extends Serializable {
	
	IRegimenView getRegimen();
	
	IPadronRSView[] getPadrones();
}