package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaView;

public interface IDeudaRSView extends Serializable {

	String getPadron();
	
	ICuotaView[] getCuotas();
}