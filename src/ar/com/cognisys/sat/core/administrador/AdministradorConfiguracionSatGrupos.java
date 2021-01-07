package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.configuraciones.ComparadorGrupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.ValidadorGrupo;
import ar.com.cognisys.sat.core.modelo.dao.DaoSatConfiguracionGrupos;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorConfiguracionSatGrupos {

	/**
	 * Metodo usado para HSAT
	 * @param grupoActual
	 * @throws ExcepcionControladaError
	 */
	public static void altaGrupo(Grupo grupoActual) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			new DaoSatConfiguracionGrupos( con ).altaGrupo(grupoActual);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}		
	}

	/**
	 * Metodo usado por HSAT
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static List<Pantalla> recuperarListaPantallas() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoSatConfiguracionGrupos( con ).recuperarListaPantallas());
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}		
	}
	
	/**
	 * Metodo usado por HSAT
	 * @param nuevo
	 * @param viejo
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void editarGrupo(Grupo nuevo, Grupo viejo) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		ValidadorGrupo v = new ValidadorGrupo(nuevo);
		
		if (!v.esCorrecto()) {
			throw new ExcepcionControladaAlerta(v.getMensajeError(), null);
		}
		
		try {
			ComparadorGrupo comparador = new ComparadorGrupo(viejo, nuevo);
			
			if (comparador.seModifico()) {
				con = AdministradorConexiones.obtenerConexion();
				
				if (comparador.seModificaronDatos()) {
					
					(new DaoSatConfiguracionGrupos( con )).editarGrupo(nuevo);
				}
			}
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
	public static void eliminarGrupo(Grupo grupo) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatConfiguracionGrupos( con )).eliminarGrupo(grupo);
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
	public static void cambiarDisponibilidad(Grupo grupo) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatConfiguracionGrupos( con )).cambiarDisponibilidad(grupo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado por HSAT
	 * @param grupo
	 * @param ordenNuevo
	 * @throws ExcepcionControladaError
	 */
	public static void cambiarOrden(Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatConfiguracionGrupos( con )).cambiarOrden(grupo, ordenNuevo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}	
	}
}