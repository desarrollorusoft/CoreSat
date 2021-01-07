package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;
import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public interface IPagoView extends Serializable {

	ICuotaDeudaView[] getCuotas();

	boolean isCancelacionDeuda();

	String getMedio();

	ICuentaView getCuenta();

	Date getFecha();	
	
	String getCorreo();
}