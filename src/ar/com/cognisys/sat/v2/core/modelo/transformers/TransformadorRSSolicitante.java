package ar.com.cognisys.sat.v2.core.modelo.transformers;

import ar.com.cognisys.sat.core.contenedor.ContenedorCaracterSAT;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSSolicitante;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSSolicitante;

public class TransformadorRSSolicitante {

	public static RSSolicitante generar(ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ISolicitanteView solicitante) {
		
		CaracterSAT c = ContenedorCaracterSAT.getInstancia().buscar( solicitante.getCaracter() );
		
		return FactoryRSSolicitante.generar(solicitante.getNombre(), 
										    solicitante.getApellido(),
										    c,
										    solicitante.getCorreo(),
										    solicitante.getTelefono(),
										    solicitante.getCelular());
	}
}