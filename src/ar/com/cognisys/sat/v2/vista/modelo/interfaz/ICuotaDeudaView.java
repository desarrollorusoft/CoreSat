package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;
import java.util.Date;

public interface ICuotaDeudaView extends Serializable {

	String getTasa();
	
	String getPeriodo();
	
	String getCapital();
	
	String getRecargo();
	
	String getMulta();
	
	String getTotal();
	
	Date getVencimiento();
	
	boolean isSeleccionado();
	
	boolean isEnMora();
	
	ICuotaDeudaView getCuotaAsociada();
	
}
