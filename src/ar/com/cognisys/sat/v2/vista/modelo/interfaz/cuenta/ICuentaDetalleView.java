package ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public interface ICuentaDetalleView extends Serializable {

	String getContribuyente();
	
	IClaveValorView[] getDatosExtra();
	
	String getCaracter();
	
	String getTelefono();

	String getDomicilioFiscal();	
}