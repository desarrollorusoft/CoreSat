package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public interface ISimulacionView extends Serializable {

	ICuentaView getCuenta();
	
	String getAnticipo();	
}