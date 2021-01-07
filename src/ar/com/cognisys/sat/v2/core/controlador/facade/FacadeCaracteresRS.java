package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.List;

import ar.com.cognisys.sat.core.contenedor.ContenedorCaracterSAT;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

public class FacadeCaracteresRS {

	/**
	 * Método utilizado para recuperar los caracteres existentes 
	 * y mostrarlos en el Formulario de Régimen Simplificado.
	 * 
	 * @return caracteres
	 */
	public String[] recuperarTodos() {

		List<CaracterSAT> lista = ContenedorCaracterSAT.getInstancia().getListaCaracteres();

		String[] caracteres = new String[lista.size()];

		for ( int i = 0; i < lista.size(); i++ )
			caracteres[i] = lista.get( i ).getNombre();
		
		return caracteres;
	}
}