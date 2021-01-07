package ar.com.cognisys.sat.v2.core.modelo.contenedor.loader;

import ar.com.cognisys.sat.core.administrador.AdministradorPyP;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorCartelerias;

public class ContenedorCarteleriasLoader implements Runnable {

	private ContenedorCartelerias contenedor;

	public ContenedorCarteleriasLoader(ContenedorCartelerias contenedor) {
		this.contenedor = contenedor;
	}

	@Override
	public void run() {
		try {
			this.contenedor.setMapa( AdministradorPyP.recuperarCarteleria() );
		} catch ( ExcepcionControladaError e ) {
			// TODO [RODRI] Loguear
			e.printStackTrace();
		}
	}

}
