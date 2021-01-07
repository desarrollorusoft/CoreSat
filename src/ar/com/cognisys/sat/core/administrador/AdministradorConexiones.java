package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.conexiones.AdministradorConexionDS;
import ar.com.cognisys.conexiones.modelo.AdministradorConexion;
import ar.com.cognisys.sat.core.logger.CoreSATLogger;
import ar.com.cognisys.sat.core.modelo.excepcion.ConectandoException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorConexiones {

	private static AdministradorConexiones instancia;
	private AdministradorConexion admConexion;

	public AdministradorConexiones(AdministradorConexion admConexion) {
		this.setAdmConexion( admConexion );
	}

	public static void iniciarInstancia(String jndi, String sqlCheck) throws ExcepcionControladaError {

		try {
			instancia = new AdministradorConexiones( new AdministradorConexionDS( jndi, sqlCheck ) );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible establecer la comunicaci�n con el sistema.", e );
		}
	}

	public static Connection obtenerConexion() throws ExcepcionControladaError {

		try {
			return getInstancia().getAdmConexion().obtenerConnection();
		} catch ( Exception e ) {
			throw new ConectandoException( e );
		}
	}

	public static void cerrarConnection(Connection con) throws ExcepcionControladaError {

		try {
			getInstancia().getAdmConexion().devolverConnection( con );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible cerrar la conexi�n", e );
		}
	}

	public static void abrirTransaccion(Connection con) throws ExcepcionControladaError {

		try {
			if ( con.getAutoCommit() )
				con.setAutoCommit( false );
		} catch ( Exception e ) {
			throw new ErrorEnBaseException( "No es posible iniciar la transacci�n", e );
		}
	}

	public static void completarTransaccion(Connection con) throws ExcepcionControladaError {

		try {
			if ( con != null && !con.isClosed() && !con.getAutoCommit() )
				con.commit();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible ejecutar el commit", e );
		}
	}

	public static void cancelarTransaccion(Connection con) {

		try {
			if ( con != null && !con.isClosed() && !con.getAutoCommit() )
				con.rollback();
		} catch ( Exception e ) {
			CoreSATLogger.getLogger().fatal( "No es posible ejecutar el rollback", e );
		}
	}

	public AdministradorConexion getAdmConexion() {
		return admConexion;
	}

	public void setAdmConexion(AdministradorConexion admConexion) {
		this.admConexion = admConexion;
	}

	public static AdministradorConexiones getInstancia() {
		return instancia;
	}
}
