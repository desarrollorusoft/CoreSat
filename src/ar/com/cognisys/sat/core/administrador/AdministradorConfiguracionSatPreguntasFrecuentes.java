package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comparador.ComparadorItem;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.dao.DaoSatPreguntasFrecuentes;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorGuiaTramite;

public class AdministradorConfiguracionSatPreguntasFrecuentes {
	
	@SuppressWarnings("deprecation")
	public static Grupo recuperarGrupo() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoSatPreguntasFrecuentes( con )).recuperarDatos();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	@SuppressWarnings("deprecation")
	public static Grupo recuperarGrupoDisp() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoSatPreguntasFrecuentes( con )).recuperarDatosDisp();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void agregarItem(Grupo grupo, Item item) throws ExcepcionControladaError {
		Connection con = null;
		try {
			ValidadorGuiaTramite validador = new ValidadorGuiaTramite(item);
			
			if (!validador.esCorrecto()) {
				throw new ExcepcionControladaAlerta(validador.getMensajeError(), null);
			}
			
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatPreguntasFrecuentes( con )).altaItem(grupo, item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void actualizarItem(Item viejo, Item nuevo) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			ValidadorGuiaTramite validador = new ValidadorGuiaTramite(nuevo);
			
			if (!validador.esCorrecto()) {
				throw new ExcepcionControladaAlerta(validador.getMensajeError(), null);
			}
			
			con = AdministradorConexiones.obtenerConexion();
			
			ComparadorItem comparador = new ComparadorItem(viejo, nuevo);
			
			if (comparador.seModificanDatos()) {
				(new DaoSatPreguntasFrecuentes( con )).modificarItem(nuevo);
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void eliminarItem(Item item, Grupo grupo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatPreguntasFrecuentes( con )).eliminarItem(item, grupo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static Pantalla recuperarPantalla() throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoSatPreguntasFrecuentes( con )).recuperarPantalla();
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void cambiarOrden(Item item, Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatPreguntasFrecuentes( con )).cambiarOrden(item, grupo, ordenNuevo);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void cambiarDisponibilidad(Item item) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoSatPreguntasFrecuentes( con )).cambiarDisponibilidad(item);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}