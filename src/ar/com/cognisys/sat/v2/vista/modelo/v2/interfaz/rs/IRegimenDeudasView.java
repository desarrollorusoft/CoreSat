package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

public interface IRegimenDeudasView extends Serializable {
	
	IDeudaRSView[] getDeudas();
}