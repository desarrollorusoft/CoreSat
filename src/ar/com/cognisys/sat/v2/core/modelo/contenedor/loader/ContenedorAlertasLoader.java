package ar.com.cognisys.sat.v2.core.modelo.contenedor.loader;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorAlertas;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorAlertas;

public class ContenedorAlertasLoader implements Runnable {

	private ContenedorAlertas contenedor;

	public ContenedorAlertasLoader(ContenedorAlertas contenedor) {
		this.contenedor = contenedor;
	}

	@Override
	public void run() {
		try {
			this.contenedor.setLista( new AdministradorAlertas().recuperarAlertasGenerales() );
		} catch ( ExcepcionControladaError e ) {
			// TODO [RODRI] Loguear
			e.printStackTrace();
		}
	}

}
