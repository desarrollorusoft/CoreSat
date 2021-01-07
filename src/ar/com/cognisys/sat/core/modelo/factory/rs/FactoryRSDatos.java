package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSActividad;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSDatos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSSolicitante;

@Deprecated
public class FactoryRSDatos {

	public static RSDatos generar(Long id, RSActividad actividad, RSSolicitante solicitante, boolean rechazo) {
		RSDatos datos = new RSDatos(id, actividad, solicitante);
		datos.setRechazo(rechazo);
		return datos;
	}
}