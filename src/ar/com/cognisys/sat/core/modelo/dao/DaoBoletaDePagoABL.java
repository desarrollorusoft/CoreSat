package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaAblAPagar;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DuplaTotal;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuotaAPagar;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudasCuentaAblAPagar;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDuplaTotal;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryTotalCuota;

public class DaoBoletaDePagoABL extends Dao {
	
	public DaoBoletaDePagoABL(Connection connection) {
		super(connection);
	}

	public DeudasCuentaAblAPagar recupearCuotasAblPorFecha(Integer cuenta, Date fecha) throws ExcepcionControladaError {

		CallableStatement cs = null;
		DeudasCuentaAblAPagar deudas = null;
		DuplaTotal duplaTotal = new DuplaTotal();

		try {
			duplaTotal.setTotalContado( this.recuperarTotalContado(cuenta, fecha) );
			duplaTotal.setTotalSimple( this.recuperarTotalSimple(cuenta, fecha) );

			deudas = FactoryDeudasCuentaAblAPagar.getInstance(this.recuperarCuotas(QueriesInformix.BOLETA_PAGO_CUOTAS.getQuery()), 
															  duplaTotal.getTotalSimple(),
															  duplaTotal.getTotalContado());
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException("Datos BOLETA DE PAGO",ex);
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}

		return deudas;
	}
	
	private TotalCuota recuperarTotalContado(Integer cuenta, Date fecha) throws ExcepcionControladaError {
		
		TotalCuota totalContado = null;
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BOLETA_PAGO_SP_CONTADO.getQuery());
			cs.setInt(1, cuenta);
			cs.setDate(2, new java.sql.Date(fecha.getTime()));
			cs.execute();	
		
			totalContado = this.recuperarTotal(QueriesInformix.BOLETA_PAGO_TOTAL.getQuery()).getTotalContado();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException("Error SP - " + QueriesInformix.BOLETA_PAGO_SP_CONTADO.getQuery(), e);
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return totalContado;
	}
	
	private TotalCuota recuperarTotalSimple(Integer cuenta, Date fecha) throws ExcepcionControladaError {
		
		TotalCuota totalSimple = null;
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BOLETA_PAGO_SP_COMUN.getQuery());
			cs.setInt(1, cuenta);
			cs.setDate(2, new java.sql.Date(fecha.getTime()));
			cs.execute();	
		
			totalSimple = this.recuperarTotal(QueriesInformix.BOLETA_PAGO_TOTAL.getQuery()).getTotalSimple();
		} catch (Exception e) {
			// SI ESTE METODO FALLA, ES PORQUE NECESITA ABRIR UNA NUEVA TRANSACCION
			throw new ErrorRecuperacionDatosException("Error SP - " + QueriesInformix.BOLETA_PAGO_SP_COMUN.getQuery(), e);
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return totalSimple;
	}
	
	private DuplaTotal recuperarTotal(String query) throws ExcepcionControladaError {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		TotalCuota total = null;
		TotalCuota totalContado = null;
		DuplaTotal duplaTotal = null;
		
		try {
			pstmt = this.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				total = FactoryTotalCuota.generarIntanciaCompleta(rs.getFloat("capital"), 
																  rs.getFloat("recargo"),
																  rs.getFloat("multa"), 
																  rs.getFloat("total"));
				
				totalContado = FactoryTotalCuota.generarIntanciaCompleta(rs.getFloat("capital"), 
																		 rs.getFloat(6), 
																		 new Float(0),
																		 rs.getFloat(5));

				duplaTotal = FactoryDuplaTotal.getInstance(total, totalContado);
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException("Totales BOLETA DE PAGO", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return duplaTotal;
	}
	
	public List<CuotaApagar> recuperarCuotas(String query) throws ExcepcionControladaError {

		ArrayList<CuotaApagar> listaCuotas = new ArrayList<CuotaApagar>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {			
			pstmt = this.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				boolean enProcesoJudicial = false;

				try {
					enProcesoJudicial = rs.getInt("n_registro") == 0 ? false : true;
				} catch (Exception e) {
				}

				listaCuotas.add(FactoryCuotaAPagar.getInstance( rs.getInt("C_tasa"),
																rs.getFloat("i_capital"),
																rs.getFloat("i_multa"),
																rs.getFloat("i_recargo"),
																rs.getString("d_tasa_abr"),
																rs.getInt("n_transac"),
																rs.getString("d_periodo").substring(0,
																rs.getString("d_periodo").lastIndexOf("-")),
																rs.getInt("vencida"),		
																enProcesoJudicial ));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException("Cuotas BELOTA DE PAGO", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return listaCuotas;
	}
}