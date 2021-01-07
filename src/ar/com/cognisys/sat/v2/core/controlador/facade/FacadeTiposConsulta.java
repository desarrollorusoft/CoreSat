package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorFormularioConsultas;
import ar.com.cognisys.sat.core.modelo.comun.consultas.TipoConsulta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;

public class FacadeTiposConsulta {

	/**
	 * Método utilizado para recuperar los tipos de consulta existentes 
	 * y mostrarlos en el Formulario de Consultas.
	 * 
	 * @return tiposConsulta
	 * @throws PersistenceSATException 
	 */
	public String[] recuperarTodos() throws PersistenceSATException {
		
		List<String> lista = new ArrayList<String>();
		try {
			List<TipoConsulta> listado = AdministradorFormularioConsultas.obtenerTipoConsultas();
			
			for (TipoConsulta tipoConsulta : listado)
				lista.add( tipoConsulta.getNombre() );
				
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible recuperar los Tipos de Consulta", e );
		}
		
		return lista.toArray( new String[] { } );
	}
}