package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.MD5;
import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuarioCF;
import ar.com.cognisys.sat.core.modelo.excepcion.ClaveInvalidaException;
import ar.com.cognisys.sat.core.modelo.excepcion.ClaveTemporalException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.InconsistenciaCorreoExcepcion;
import ar.com.cognisys.sat.core.modelo.excepcion.UsuarioInactivoException;

public class AdministradorUsuarioCF {

	/**
	 * Metodo utilizado para buscar un usuario tanto en SAT como en C.F.
	 * @param cuit
	 * @param clave
	 * @return
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static Usuario buscar(String cuit, String clave) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		Usuario usuario = buscarUsuarioSAT(cuit);
		
		if (usuario == null)
			usuario = buscarUsuarioCF(cuit, clave);
		
		if (clave.equals(MD5.getMD5(AdministradorUsuario.CLAVE_TEMPORAL)) && usuario.sos(clave))
			throw new ClaveTemporalException();
		
		if (! (usuario.sos(clave) || usuario.esCalveMaestra(clave)))
			throw new ClaveInvalidaException();
		// Esto se agrega por tema de incosistencia de datos
		if (!usuario.tieneCorreo())
			throw new InconsistenciaCorreoExcepcion();
		if (!usuario.isActivo())
			throw new UsuarioInactivoException();
		
		return usuario;
	}
	
	private static Usuario buscarUsuarioSAT(String cuit) throws ExcepcionControladaError {
		return AdministradorUsuario.buscar(cuit);
	}

	private static Usuario buscarUsuarioCF(String cuit, String clave) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		UsuarioCF ucf = recuperarUsuarioCF(cuit);
		
		if (ucf == null)
			throw new ExcepcionControladaAlerta("Los datos ingresados no se encuentran en los sistemas Clave Fiscal y SAT", null);
		else if (!ucf.getClave().equals(clave))
			throw new ClaveInvalidaException();
		else {
			if (ucf.getCorreo() == null || AdministradorUsuario.existeUsuarioPorCorreo(ucf.getCorreo()))
				ucf.setCorreo("vacio");
			
			registrarUsuarioSAT(ucf);
			Usuario u = buscarUsuarioSAT(cuit);
			u.setMigrado(true);
			return u;
		}
	}

	private static UsuarioCF recuperarUsuarioCF(String cuit) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuarioCF(con)).buscar(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static void registrarUsuarioSAT(UsuarioCF usuario) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).registrarCF(usuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void actualizarClaveCF(Usuario u) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuarioCF(con)).actualizarClave(u.getCuit(), u.getClave());
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}