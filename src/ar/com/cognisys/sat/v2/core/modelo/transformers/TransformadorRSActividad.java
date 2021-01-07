package ar.com.cognisys.sat.v2.core.modelo.transformers;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSActividad;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSActividad;

public class TransformadorRSActividad {

	public static RSActividad generar(ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IActividadView actividad) {
		
		return FactoryRSActividad.generar(actividad.getCuit(),
										  actividad.getCorreo(),
										  actividad.getTelefono(),
										  actividad.getCelular());
	}	
}