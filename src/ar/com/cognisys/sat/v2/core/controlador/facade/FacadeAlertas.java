package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorAlertas;
import ar.com.cognisys.sat.v2.core.modelo.adapter.AlertaParticularFromViewAdapter;
import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorAlertas;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.vista.modelo.creator.AlertaViewCreator;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuitView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IAlertaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IUsuarioAlertaView;

public class FacadeAlertas {

	/**
	 * Método utilizado para agregar una alerta a un usuario específico.
	 * 
	 * @param usuarioAlerta
	 * @throws PersistenceSATException 
	 */
	public void agregar(IUsuarioAlertaView usuarioAlerta) throws PersistenceSATException {
		try {
			new AdministradorAlertas()
					.crear( usuarioAlerta.getNombreUsuario(), 
								  new AlertaParticularFromViewAdapter( usuarioAlerta.getAlerta() ) );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible agregar la alerta", e );
		}
	}
	
	/**
	 * Método utilizado para borrar una alerta de un usuario específico.
	 * 
	 * @param usuarioAlerta
	 * @throws PersistenceSATException 
	 */
	public void borrar(IUsuarioAlertaView usuarioAlerta) throws PersistenceSATException {
		try {
			new AdministradorAlertas()
					.eliminar( usuarioAlerta.getNombreUsuario(), 
								  new AlertaParticularFromViewAdapter( usuarioAlerta.getAlerta() ) );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible borrar la alerta", e );
		}
	}

	public IAlertaView[] recuperar(ICuitView cuitView) throws PersistenceSATException {
		List<IAlertaView> lista = new ArrayList<IAlertaView>();
		try {
			List<IAlerta> listaAlertas = new AdministradorAlertas().recuperar( cuitView.getCuit() );
			
			for ( IAlerta alerta : listaAlertas )
				lista.add( this.crearAlertaView( alerta ) );

		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar las alertas", e );
		}
		return lista.toArray( new IAlertaView[] {} );
	}
	

	public IAlertaView[] recuperar() {
		List<IAlertaView> lista = new ArrayList<IAlertaView>();
		
		for ( IAlerta alerta : new AdministradorAlertas().recuperar() )
			lista.add( this.crearAlertaView( alerta ) );

		return lista.toArray( new IAlertaView[] {} );
	}

	private IAlertaView crearAlertaView(IAlerta alerta) {
		AlertaViewCreator creator = new AlertaViewCreator();
		return creator
				.title( alerta.getDescripcion() )
				.clave( alerta.getTitulo() )
				.start( alerta.getFechaInicio() )
				.end( alerta.getFechaFin() )
				.redireccion( alerta.getRedireccion() )
				.create();
	}

	public void reiniciarContenedor() throws PersistenceSATException {
		try {
			ContenedorAlertas.reiniciarInstancia();
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible reiniciar el contenedor", e );
		}
	}
}