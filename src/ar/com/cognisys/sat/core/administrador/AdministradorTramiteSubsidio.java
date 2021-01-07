package ar.com.cognisys.sat.core.administrador;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPPC;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.DatosComercio;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.EstadoTramite;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.TramiteSubsidio;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoPlanDePago;
import ar.com.cognisys.sat.core.modelo.dao.DaoSubsidio;
import ar.com.cognisys.sat.core.modelo.dao.ppc.DaoPPC;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class AdministradorTramiteSubsidio extends Administrador {

	private static final long serialVersionUID = 7345543840575100799L;

	public static TramiteSubsidio recuperarTramite(String cuit) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoSubsidio(con)).recuperarTramite( cuit );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void guardar(TramiteSubsidio tramite) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoSubsidio(con)).actualizar(tramite);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void enviar(TramiteSubsidio tramite) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoSubsidio(con)).enviar(tramite);

			tramite.setEstado( EstadoTramite.ENVIADO );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

    public static List<TramiteSubsidio> recuperarListaTramites(int inicio, int cantidad, Integer padron, Date fechaDesde, Date fechaHasta, EstadoTramite estado) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoSubsidio(con)).recuperarLista(inicio, cantidad, padron, fechaDesde, fechaHasta, estado);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static int recuperarCantidadTramites(Integer padron, Date fechaDesde, Date fechaHasta, EstadoTramite estado) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoSubsidio(con)).recuperarCantidadLista(padron, fechaDesde, fechaHasta, estado);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

    public static void cerrar(TramiteSubsidio tramite) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoSubsidio(con)).cerrar(tramite);

			tramite.setEstado( EstadoTramite.CERRADO );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
    }
}