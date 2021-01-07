package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comparador.ComparadorItem;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.dao.DaoSatGuiaTramite;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorGuiaTramite;

public class AdministradorConfiguracionSatGuiaTramites {

	/**
	 * Metodo usado en HSAT
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static Pantalla recuperarPantalla() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoSatGuiaTramite( con )).recuperarDatos();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}	
	
	/**
	 * Metodo usado en HSAT
	 * @param grupo
	 * @param item
	 * @throws ExcepcionControladaError
	 */
	public static void agregarItem(Grupo grupo, Item item) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).altaItem(grupo, item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}	
	
	/**
	 * Metodo usado en HSAT
	 * @param nuevo
	 * @param viejo
	 * @throws ExcepcionControladaError
	 */
	public static void editarItem(Item nuevo, Item viejo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			ValidadorGuiaTramite validador = new ValidadorGuiaTramite(nuevo);
			
			if (!validador.esCorrecto()) {
				throw new ExcepcionControladaAlerta(validador.getMensajeError(), null);
			}
			
			con = AdministradorConexiones.obtenerConexion();
			
			ComparadorItem comparador = new ComparadorItem(viejo, nuevo);
			
			if (comparador.seModificanDatos()) {
				(new DaoSatGuiaTramite( con )).modificarItem(nuevo);
			}
			
			if (comparador.seModificaArchivo()) {
				(new DaoSatGuiaTramite( con )).actualizarArchivoItem(nuevo, viejo.getArchivo());
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo usado en HSAT
	 * @param item
	 * @param grupo
	 * @throws ExcepcionControladaError
	 */
	public static void eliminarItem(Item item, Grupo grupo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).eliminarItem(item, grupo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param grupoPadre
	 * @throws ExcepcionControladaError
	 */
	public static void relacionarArchivoAGrupo(Grupo grupoPadre) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).agregarArchivoGrupo(grupoPadre);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param grupo
	 * @throws ExcepcionControladaError
	 */
	public static void eliminarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).eliminarArchivoGrupo(grupo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param item
	 * @throws ExcepcionControladaError
	 */
	public static void eliminarArchivoItem(Item item) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).eliminarArchivoItem(item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado en HSAT
	 * @param item
	 * @throws ExcepcionControladaError
	 */
	public static void relacionarArchivoAItem(Item item) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).agregarArchivo(item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}	
	
	/**
	 * Metodo usado en HSAT
	 * @param grupo
	 * @param ordenNuevo
	 * @throws ExcepcionControladaError
	 */
	public static void cambiarOrden(Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).cambiarOrden(grupo, ordenNuevo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo usado en HSAT
	 * @param item
	 * @throws ExcepcionControladaError
	 */
	public static void cambiarDisponibilidad(Item item) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatGuiaTramite( con )).cambiarDisponibilidad(item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}