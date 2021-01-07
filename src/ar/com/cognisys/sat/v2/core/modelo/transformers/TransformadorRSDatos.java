package ar.com.cognisys.sat.v2.core.modelo.transformers;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSActividad;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSDatos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSSolicitante;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenView;

public class TransformadorRSDatos {

	public static RSDatos generar(IRegimenView view) {
		
		RSActividad actividad = TransformadorRSActividad.generar(view.getActividad());
		
		RSSolicitante solicitante = TransformadorRSSolicitante.generar(view.getSolicitante());
		
		return (new RSDatos(null, actividad, solicitante));
	}
	
	public static RSDatos generar(IRegimenView view, String claveUsuario) {
		
		RSActividad actividad = TransformadorRSActividad.generar(view.getActividad());
		actividad.setClave(claveUsuario);
		
		RSSolicitante solicitante = TransformadorRSSolicitante.generar(view.getSolicitante());
		
		return (new RSDatos(null, actividad, solicitante));
	}
}