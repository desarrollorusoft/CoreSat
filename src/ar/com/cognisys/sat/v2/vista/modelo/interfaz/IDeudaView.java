package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

public interface IDeudaView extends Serializable {

	String getAviso();
	
	ICuotaDeudaView[] getCuotasVencidas();
	
	String getTotalVencidas();
	
	ICuotaDeudaView[] getCuotasProximas();
	
	String getTotalProximas();
	
}
