package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralCompleto;
import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralOrigen;
import ar.com.cognisys.sat.core.modelo.dao.DaoGeneral;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorEstadisticasGeneral {
	
	public static List<EstadisticasGeneralOrigen> recuperarWeb(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoGeneral(con)).recuperarWeb(escala, fechaDesde, fechaHasta);
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar los datos web", e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static List<EstadisticasGeneralOrigen> recuperarMobile(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoGeneral(con)).recuperarMobile(escala, fechaDesde, fechaHasta);
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar los datos mobile", e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static List<EstadisticasGeneralCompleto> recuperarListadoCompleto(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoGeneral(con)).recuperarListaCompleta(escala, fechaDesde, fechaHasta);
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar los datos del listado completo", e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}