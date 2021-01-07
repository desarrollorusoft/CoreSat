package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.configuraciones.CorreoOficial;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.dao.DaoConfiguracionCorreoOficial;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorConfiguracionCorreoOficial {

	/**
	 * Metodo utilizado por el HSAT
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static List<CorreoOficial> getAllCorreoOficial() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoConfiguracionCorreoOficial(con)).getAllCorreoOficial();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado por el HSAT
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static List<Categoria> getAllCategorias() throws ExcepcionControladaError {
		Connection con = null;
		try {			
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoConfiguracionCorreoOficial(con)).getAllCategorias();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param correo
	 * @param categoriaSeleccionada
	 * @throws ExcepcionControladaError
	 */
	public static void crearCorreoOficial(String correo, Categoria categoriaSeleccionada) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoConfiguracionCorreoOficial(con)).crearCorreoOficial(correo, categoriaSeleccionada);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param correoSeleccionado
	 * @throws ExcepcionControladaError
	 */
	public static void eliminarCorreoOficial(CorreoOficial correoSeleccionado) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoConfiguracionCorreoOficial(con)).eliminarCorreoOficial(correoSeleccionado);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param categoria
	 * @param correo
	 * @throws ExcepcionControladaError
	 */
	public static void actualizarCorreoOficial(Categoria categoria, String correo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoConfiguracionCorreoOficial(con)).actualizarCorreoOficial(categoria, correo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e); 
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}