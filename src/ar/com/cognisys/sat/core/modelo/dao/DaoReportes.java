package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.enums.QueriesMySQL;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorReportandoException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoReportes extends Dao {
	
	public DaoReportes(Connection connection) {
		super(connection);
	}

	public static void ingresaUsuario(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.INGRESO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablConsultarDeuda(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_CONSULTA_DEUDA.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablBoletaPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_BOLETA_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void ablSimulacionPPC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_SIMULACION.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPlanDePago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PLAN_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablGeneracionRecibo(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_RECIBO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void ablPagarInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PAGAR_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPagarCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PAGAR_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPagarPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PAGAR_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPagarLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PAGAR_LINKPAGOS.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresConsultarDeuda(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_CONSULTA_DEUDA.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresBoletaPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_BOLETA_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void automotoresSimulacionPPC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_SIMULACION.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPlanPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PLAN_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresGeneracionRecibo(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_RECIBO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void automotoresPagarInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PAGAR_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPagarCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PAGAR_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPagarPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PAGAR_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPagarLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PAGAR_LINKPAGOS.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablSimulacionPPCCalculado(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_SIMULACION_PPC_CALCULADO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresSimulacionPPCCalculado(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTOR_SIMULACION_PPC_CALCULADO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPlanInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PLAN_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPlanPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PLAN_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void ablPlanCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PLAN_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPlanPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PLAN_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPlanInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PLAN_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void automotoresPlanCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PLAN_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioConsultarDeuda(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_CONSULTA_DEUDA.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioBoletaPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_BOLETA_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioSimulacionPPC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_SIMULACION.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioSimulacionPPCCalculado(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_SIMULACION_PPC_CALCULADO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioGeneracionRecibo(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_RECIBO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPagarInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PAGAR_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioPagarCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PAGAR_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioPagarPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PAGAR_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void cementerioPagarLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PAGAR_LINKPAGOS.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void natatoriosIngreso(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.NATATORIOS_INGRESO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void natatoriosGeneracionRecibo(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.NATATORIOS_GENERACION.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void pagoMultipleIngreso(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.PAGO_MULTIPLE_INGRESO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void pagoMultipleGeneracion(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.PAGO_MULTIPLE_GENERACION.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsConsultarDeuda(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_CONSULTAR_DEUDA.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsBoletaPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_BOLETA_PAGO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsGeneracionRecibo(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_GENERACION_RECIBO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsPagarInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PAGO_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsPagarCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PAGO_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsPagarPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PAGO_PMC.getSql(), nombreUsuario, fecha, sistema);
	}
	
	public static void rsPagarLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_LINK_PAGOS.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void ablPagarMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PAGAR_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void automotoresPagarMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PAGAR_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPagarMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PAGAR_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPagarMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PAGAR_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void ablPlanMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.ABL_PLAN_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void automotoresPlanMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.AUTOMOTORES_PLAN_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPlanMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PLAN_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPlanMercadoPago(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PLAN_MP.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPlanInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PLAN_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPlanPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PLAN_PMC.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPlanCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PLAN_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsPlanLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_PLAN_LINK.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPlanInterbanking(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PLAN_INTERBANKING.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPlanPMC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PLAN_PMC.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPlanCredito(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PLAN_CREDITO.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void cementerioPlanLinkPagos(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.CEMENTERIO_PLAN_LINK.getSql(), nombreUsuario, fecha, sistema);
	}




	public static void rsSimulacionPPC(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_SIMULACION.getSql(), nombreUsuario, fecha, sistema);
	}

	public static void rsSimulacionPPCCalculado(String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
		registrar(QueriesMySQL.RS_SIMULACION_PPC_CALCULADO.getSql(), nombreUsuario, fecha, sistema);
	}

	private static void registrar(String insert, String nombreUsuario, Date fecha, Sistema sistema) throws ExcepcionControladaError {
				
		PreparedStatement pstmt = null;
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			pstmt = con.prepareStatement(insert);
			pstmt.setString(1, nombreUsuario);
			pstmt.setTimestamp(2, new Timestamp(fecha.getTime()));
			pstmt.setInt(3, (sistema == Sistema.SAT ? 1 : 2));
						
			pstmt.executeUpdate();

		} catch (Exception ex) {
			throw new ErrorReportandoException(ex);			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					AdministradorConexiones.cerrarConnection(con);;
				} catch (Exception e) {
				}
			}
		}
	}
}