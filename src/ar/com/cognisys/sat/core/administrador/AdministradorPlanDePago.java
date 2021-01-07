package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPPC;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoPlanDePago;
import ar.com.cognisys.sat.core.modelo.dao.ppc.DaoPPC;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorPlanDePago extends Administrador {

	private static final long serialVersionUID = 7345543840575100799L;

	@Deprecated
	public static PlanDePago recuperarDatosConsulta(Cuenta cuentaSeleccionada) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			if (cuentaSeleccionada instanceof CuentaABL) {
				return new DaoPlanDePago(con).recuperarDatosConsultaAbl((CuentaABL) cuentaSeleccionada);

			} else if (cuentaSeleccionada instanceof CuentaVehiculos) {
				return new DaoPlanDePago(con).recuperarDatosConsultaVehiculos((CuentaVehiculos) cuentaSeleccionada);

			} else if (cuentaSeleccionada instanceof CuentaRodados) {
				return new DaoPlanDePago(con).recuperarDatosConsultaRodados((CuentaRodados) cuentaSeleccionada);

			} else {
				throw new ExcepcionControladaError("Tipo de cuenta no implementado", null);
			}
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static CuotaPPC obtenerCancelacionPlan(PlanDePagoAPagar plan) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoPlanDePago(con)).cancelacionPlan(plan);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<CuotaPPC> obtenerCuotas(Integer nroPlan) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoPPC(con)).recuperarCuotas(nroPlan);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	@Deprecated
	public static List<CuotaPlanDePago> obtenerCuotasPlanDePago(PlanDePagoAPagar plan) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoPlanDePago(con)).getCuotas(plan);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<PlanDePagoAPagar> obtenerPlanesDePago(Cuenta cuenta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoPPC(con)).recuperarPlanes(cuenta.getTipoCuenta().getCodigo(), cuenta.getNumero());
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<PlanDePagoAPagar> obtenerPlanesDePagoUsuario(String cuit) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoPPC(con)).recuperarPlanesUsuario(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static PlanDePagoAPagar buscarPlan(Integer numeroPlan) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoPPC(con)).recuperarPlan(numeroPlan);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void asociarPlan(PlanDePagoAPagar plan, Usuario usuario, String alias) throws ExcepcionControladaError {
		asociar(plan.getNroPlan(), usuario.getIdUsuario(), alias);

		usuario.getPlanes().add(plan);
		plan.setAsociado(true);
	}

	public static void asociar(Integer numeroPlan, Integer idUsuario, String alias) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoPPC(con)).asociarPlan(numeroPlan, idUsuario, alias);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void deasociarPlan(Usuario usuario, PlanDePagoAPagar plan) throws ExcepcionControladaError {
		desasociar(plan.getNroPlan(), usuario.getIdUsuario());

		usuario.getPlanes().remove(plan);
	}

	public static void desasociar(Integer idUsuario, Integer numeroPlan) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoPPC(con)).deasociarPlan(numeroPlan, idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void actualizarAlias(PlanDePagoAPagar plan, Integer idUsuario) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			DaoPPC dao = new DaoPPC(con);

			if ( !dao.estaAsociado(plan.getNroPlan(), idUsuario) )
				dao.asociarPlan( plan.getNroPlan(), idUsuario, plan.getAlias() );
			else
				dao.actualizarAlias(plan.getNroPlan(), idUsuario, plan.getAlias());
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	@Deprecated
	public static boolean tienePlanPago(Cuenta cuenta) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPlanDePago(con)).tienePlanPagoABL(cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	@Deprecated
	public static boolean tienePlanPagoAutomotor(Cuenta cuenta) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPlanDePago(con)).tienePlanPagoVehiculos(cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}