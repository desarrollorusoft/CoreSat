package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorFormularioConsultas;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Caracter;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.vista.modelo.creator.CategoriaViewCreator;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICategoriaView;

public class FacadeCategorias {

	/**
	 * Método utilizado para recuperar las categorías existentes 
	 * y mostrarlas en el Formulario de Consultas.
	 * 
	 * @return categorias
	 * @throws PersistenceSATException 
	 */
	public ICategoriaView[] recuperarTodos() throws PersistenceSATException {
		
		List<ICategoriaView> lista = new ArrayList<ICategoriaView>();
		
		try {

			List<Categoria> listaCategorias = AdministradorFormularioConsultas.obtenerCategorias();
			for (Categoria categoria : listaCategorias)
				lista.add( this.crearCategoriaView( categoria ) );
			
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible recuperar las categorias", e );
		}
		
		return lista.toArray( new ICategoriaView[] {} );
	}

	private ICategoriaView crearCategoriaView(Categoria categoria) {
		List<String> lista = new ArrayList<String>();

		for ( Caracter caracter : categoria.getCaracteresPosibles() )
			lista.add( caracter.getNombre() );

		String[] caracteres = lista.toArray( new String[] {} );
		
		CategoriaViewCreator creator = new CategoriaViewCreator();
		
		return creator
				.caracteres( caracteres )
				.categoria( categoria.getNombre() )
				.expresionRegular( categoria.getDato().getExpresionRegular() )
				.nombreDato( categoria.getDato().getNombre() )
				.create();
	}	
}