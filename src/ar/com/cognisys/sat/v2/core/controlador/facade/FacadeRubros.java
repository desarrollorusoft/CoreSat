package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRubroView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.creator.rs.RubroViewCreator;

public class FacadeRubros {

	/**
	 * Método utilizado para recuperar los rubros existentes 
	 * y mostrarlos en el Formulario de Régimen Simplificado.
	 * 
	 * @return rubros
	 * @throws PersistenceSATException 
	 */
	public IRubroView[] recuperarTodos() throws PersistenceSATException {

		List<IRubroView> listaRubros = new ArrayList<IRubroView>();

		return listaRubros.toArray( new IRubroView[] {} );
	}
}