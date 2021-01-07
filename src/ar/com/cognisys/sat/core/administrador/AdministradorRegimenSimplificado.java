package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSDeudaPadron;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoActividadComercial;
import ar.com.cognisys.sat.core.modelo.dao.DaoRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.dao.rs.BonificacionRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJActividades;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJOEP;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJSV;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoTipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoTopesRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoVersionDDJJ;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRecuperadorRS;

public class AdministradorRegimenSimplificado {
	
	public static boolean permitoRegimen(String cuit) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoRS(con)).validoCuit(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static Map<Integer, List<ActividadComercial>> recuperarActividades() throws ExcepcionControladaError {
		
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoActividadComercial(con)).recuperar();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<TipoRechazoRS> obtenerTiposRechazos() throws ExcepcionControladaError {
		
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoTipoRechazoRS( con )).obtener();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static void cargarDeclaraciones(Comercio comercio) throws ExcepcionControladaError {
		
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			for (DDJJRS ddjj : comercio.getListaDeclaraciones()) {
				RecuperadorVersionesRS recuperador = FactoryRecuperadorRS.generar( ddjj, con );
				recuperador.cargar();
			}
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}		
	}

	public static void actualizarInicioRectificacion(DDJJRS declaracion, PadronRS padron, Usuario usuario) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion( con );
			
			DaoRS daoRS = new DaoRS(con);
			for (PadronRS p : declaracion.getListaPadrones())
				daoRS.actualizarEstado( declaracion, p );
			
			DaoVersionDDJJ daoVersion = new DaoVersionDDJJ(con);
			
			if (padron.tieneVersionNueva())
				daoVersion.guardar(padron.getNumero(), padron.obtenerUltimaVersion(), usuario);
			else
				daoVersion.actualizarEstado( padron );
		
			AdministradorConexiones.completarTransaccion( con );
		} catch (ExcepcionControladaError e) {
			AdministradorConexiones.cancelarTransaccion( con );
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Map<Integer, TopeRS> obtenerTopes() throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoTopesRS(con)).recuperar();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static void guardar(PadronRS padron, Usuario usuario) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion( con );
			
			VersionPadronRS version = padron.obtenerUltimaVersion();
			
			(new DaoVersionDDJJ(con)).guardar( padron.getNumero(), version, usuario );
			
			switch (version.getAno()) {
				case 2018: guardarDatos( (VersionPadronRS2018) version, con ); break;
				case 2019: guardarDatos( (VersionPadronRS2019) version, con ); break;
				case 2020: guardarDatos( (VersionPadronRS2020) version, con ); break;
				default: break;
			}
			
			AdministradorConexiones.completarTransaccion( con );
		} catch (ExcepcionControladaError e) {
			AdministradorConexiones.cancelarTransaccion( con );
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static void guardarDatos(VersionPadronRS2018 version, Connection con) throws ExcepcionControladaError {
		(new DaoDDJJCarteleria(con)).guardar( version.getId(), version.getListaCarteleria() );
		(new DaoDDJJOEP(con)).guardar( version.getId(), version.getListaOEP() );
		(new DaoDDJJActividades(con)).guardar( version.getId(), version.getActividades() );
		(new DaoDDJJSV(con)).guardar( version.getId(), version.getServiciosVarios() );
	}

	private static void guardarDatos(VersionPadronRS2019 version, Connection con) throws ExcepcionControladaError {
		(new DaoDDJJCarteleria(con)).guardar( version.getId(), version.getListaCarteleria() );
		(new DaoDDJJOEP(con)).guardar( version.getId(), version.getListaOEP() );
		(new DaoDDJJActividades(con)).guardar( version.getId(), version.getActividades() );
		(new DaoDDJJSV(con)).guardar( version.getId(), version.getServiciosVarios() );
	}
	
	private static void guardarDatos(VersionPadronRS2020 version, Connection con) throws ExcepcionControladaError {
		(new DaoDDJJCarteleria(con)).guardar( version.getId(), version.getListaCarteleria() );
		(new DaoDDJJOEP(con)).guardar( version.getId(), version.getListaOEP() );
		(new DaoDDJJActividades(con)).guardar( version.getId(), version.getActividades() );
		(new DaoDDJJSV(con)).guardar( version.getId(), version.getServiciosVarios() );
	}

	public static void rechazar(DDJJRS ddjj, Integer codigoRechazo) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion( con );
			
			ddjj.rechazada();
			
			(new DaoRS(con)).registrarRechazo(ddjj, codigoRechazo);
			(new DaoVersionDDJJ(con)).registrarRechazo( ddjj.getListaPadrones(), codigoRechazo );
			
			AdministradorConexiones.completarTransaccion( con );
		} catch (ExcepcionControladaError e) {
			AdministradorConexiones.cancelarTransaccion( con );
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<RSDeudaPadron> recuperarDeudaRS(DDJJRS ddjj, Usuario usuario) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoRS(con)).registrarConfirmaciones(ddjj, usuario);
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static boolean confirmarDDJJRS(String cuit, DDJJRS declaracion) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoRS( con )).registrarAceptacion(cuit, declaracion);
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void cancelarRectificativa(DDJJRS declaracion, PadronRS padron) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion( con );
			
			VersionPadronRS version = padron.obtenerUltimaVersion();
			
			(new DaoDDJJCarteleria( con )).borrar( version.getId() );
			(new DaoDDJJActividades( con )).borrar( version.getId() );
			(new DaoDDJJOEP( con )).borrar( version.getId() );
			(new DaoDDJJSV( con )).borrar( version.getId() );
			(new DaoVersionDDJJ( con )).borrar( version );
			
			declaracion.cancelaRectificativa(padron, version);
			
			(new DaoRS( con )).actualizarEstado(declaracion, padron);
			
			AdministradorConexiones.completarTransaccion( con );
		} catch (ExcepcionControladaError e) {
			AdministradorConexiones.cancelarTransaccion( con );
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static VersionPadronRS buscarDeclaracionConfirmada(PadronRS padron, VersionPadronRS version) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoVersionDDJJ( con )).buscarDeclaracionConfirmada(padron.getNumero(), version.getAno(), version.getVersion());
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	

	public static BonificacionRS recuperarBonificacion(Integer padron) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoRegimenSimplificado(con)).recuperarBonificacion(padron);
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static boolean puedeRectificar(DDJJRS declaracion, PadronRS padron) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoRS(con)).puedeRectificar(padron.getNumero(), declaracion.getAno());
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
}