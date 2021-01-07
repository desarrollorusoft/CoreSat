package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

public interface IPlanDePagoView extends Serializable {
	
	String getNombre();
	
	String getAnticipo();
	
	String getPorcentaje();

	String getImporteTotal();
	
	String getImporteAnticipo();
	
	String getImportePlan();
	
	ICuotaView[] getCuotas();
}
