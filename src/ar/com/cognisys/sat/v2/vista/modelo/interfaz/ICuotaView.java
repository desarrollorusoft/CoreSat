package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;
import java.util.Date;

public interface ICuotaView extends Serializable {

	String getNumero();

	String getImporte();
	
	Date getVencimiento();
}