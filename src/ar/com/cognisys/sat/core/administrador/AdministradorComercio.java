package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.EstadoDeclaracion;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoComercio;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaComercio;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoSolicitanteRS;
import ar.com.cognisys.sat.core.modelo.excepcion.CuentaYaAsociadaExcepcion;
import ar.com.cognisys.sat.core.modelo.excepcion.DebeCompletarRsException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.InvalidaDatoCuentaException;

public class AdministradorComercio {

	public static Comercio recuperarSimple(String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoComercio(con)).buscarComercioBase(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static Comercio recuperarCompleto(String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			Comercio c = (new DaoComercio(con)).buscarComercioBase(cuit);
			
			List<DDJJRS> lista = new ArrayList<DDJJRS>();
			
			for (DDJJRS ddjj : (new DaoRS(con)).recuperarDeclaraciones( cuit ))
				if ( ! ddjj.getEstado().equals( EstadoDeclaracion.NO_APLICA ) && 
					 ! ddjj.getEstado().equals( EstadoDeclaracion.BAJA ) )
					lista.add( ddjj );
			
			/* Si ni se recupera ninguna declaracion VALIDA, es como si no tuviese RS */
			if (lista.isEmpty())
				return null;
			
			c.setListaDeclaraciones( lista );
			c.setSolicitanteRS( (new DaoSolicitanteRS(con)).buscarSolicitante( cuit ) );
			
			return c;
		} catch(InvalidaDatoCuentaException e) {
			return null;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * - Se debe revisar si existe el usuario
	 * 		Si existe, se actualizan los datos
	 * 		Sino, se crea el usuario
	 * 	
	 * - Se debe registrar el solicitante
	 * 		Si existe, se actualizan los datos
	 * 		Sino, se inserta
	 */
	public static void registrarRS(Usuario usuario) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Usuario actual = AdministradorUsuario.buscar( usuario.getCuit() );
		
		if (actual != null)
			AdministradorContribuyente.actualizarDatosUsuario( actual.getIdUsuario(), 
															   usuario.getCorreo(), 
															   usuario.getTelefono1(), 
															   usuario.getTelefono2(), 
															   actual.getNombre(), 
															   actual.getApellido(), 
															   actual.getNivel());
		else 
			AdministradorUsuario.registrarUsuarioPorRS( usuario );
		 
		AdministradorUsuarioCF.actualizarClaveCF( usuario );
			
		AdministradorSolicitanteRS.registrar( usuario.getComercio() );
	}
	
	public static List<Cuenta> recuperarCuentasUsuario(String cuit) throws ExcepcionControladaError {
		
		Integer idUsuario = AdministradorUsuario.recuperarIdUsuario(cuit);
		
		return recuperarCuentasAgregadas(idUsuario);
	}

	private static List<Cuenta> recuperarCuentasAgregadas(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoCuentaComercio(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static Comercio recuperarComercioAsociar(String cuit, Usuario usuario) throws ExcepcionControladaError,DebeCompletarRsException, ExcepcionControladaAlerta {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();

			if( ! new DaoComercio(con).noEstaAgregada( cuit, usuario.getIdUsuario() ) )
				throw new CuentaYaAsociadaExcepcion();
			else
				return recuperarComercioBase( cuit );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static Comercio recuperarComercioBase(String cuit) throws ExcepcionControladaError,DebeCompletarRsException, ExcepcionControladaAlerta {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoComercio(con)).buscarComercioBase( cuit );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}