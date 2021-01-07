package ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs;

import java.io.Serializable;
import java.util.Date;

@Deprecated public interface IPadronRSView extends Serializable {

	String getCodigoCuenta();

	String getRazonSocial();
	
	String getImporte();

	ICarteleriaView[] getCartelerias();

	String getMetrosOEP();

	String getUnidadesOEP();

	String getMotoresSV();

	String getCalderasSV();
	
	String getCantidadPersonas();
	
	String getRubroPrincipal();
	
	String[] getRubrosSecundarios();
	
	Date getFechaHabilitacion();
	
	boolean isCompleto();
}