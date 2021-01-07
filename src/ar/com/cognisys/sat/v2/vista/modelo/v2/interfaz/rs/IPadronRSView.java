package ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs;

import java.io.Serializable;
import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public interface IPadronRSView extends Serializable {

	String getCodigoCuenta();

	String getRazonSocial();
	
	String getImporte();
	
	String getCodigoCuentaABL();

	ICarteleriaView[] getCartelerias();
	
	IClaveValorView[] getOcupaciones();

	String getMotoresSV();

	String getCalderasSV();
	
	String getCantidadPersonas();
	
	IRubroView getRubroPrincipal();
	
	String[] getRubrosSecundarios();
	
	Date getFechaHabilitacion();
	
	boolean isCompleto();
}