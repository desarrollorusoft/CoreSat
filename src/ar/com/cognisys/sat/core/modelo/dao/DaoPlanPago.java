package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanSimulacion;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.ResultadoSimulacion;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.SimulacionIncorrectaException;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryPlanSimulacion;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryResultadoSimulacion;

public class DaoPlanPago extends Dao {
	
	public DaoPlanPago(Connection connection) {
		super(connection);
	}

	public ResultadoSimulacion calcularPlanPago(List<Cuota> listaCuotas, Cuenta cuenta, Integer anticipo) throws ExcepcionControladaError {
		
		this.cargarTransacciones(listaCuotas);
		return this.simularPlan(cuenta, anticipo);
	}
	
	private void cargarTransacciones(List<Cuota> listaCuotas) throws ExcepcionControladaError {
		
		this.eliminarTablaTemporal();
		this.crearTablaTemporal();
		
		for (Cuota cuota : listaCuotas)
			this.insertarCuota(cuota);
	}
	
	private void eliminarTablaTemporal() throws ExcepcionControladaError {
		
		PreparedStatement pstmt = null;

		try {
			pstmt = this.getConnection().prepareStatement(QueriesInformix.SPP_ELIMINAR_TABLA.getQuery());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if (! e.getMessage().contains("The specified table (tmp_deuda_pago) is not in the database."))
				throw new ErrorEnBaseException("Error con la tabla temporal ", e.getCause());
		} finally {			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private void crearTablaTemporal() throws ExcepcionControladaError {

		PreparedStatement pstmt = null;

		try {
			pstmt = this.getConnection().prepareStatement(QueriesInformix.SPP_CREAR_TABLA.getQuery());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error con la tabla temporal", e.getCause());
		} finally {			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private void insertarCuota(Cuota cuota) throws ExcepcionControladaError {
		
		PreparedStatement pstmt = null;

		try {
			pstmt = this.getConnection().prepareStatement(QueriesInformix.SPP_INSERT_TRANSACCION.getQuery());
			pstmt.setInt(1, cuota.getNumeroTasa());
			pstmt.setString(2, cuota.getTasa());
			pstmt.setString(3, cuota.getPeriodo());
			pstmt.setFloat(4, cuota.getCapital());
			pstmt.setFloat(5, cuota.getRecargo());
			pstmt.setFloat(6, cuota.getMulta());
			pstmt.setInt(7, cuota.getNumeroTransaccion());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error al cargar la transacción ("+(cuota!=null? cuota.getNumeroTransaccion() : "SNT")+")", e.getCause());
		} finally {			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private ResultadoSimulacion simularPlan(Cuenta cuenta, Integer anticipo) throws ExcepcionControladaError {
		
		ResultadoSimulacion resultado = this.generarSimulacion(cuenta, anticipo);
		resultado.setListaPlanes( this.recuperarResultado( resultado.getNumeroSolicitud() ) );
		
		return resultado;
	}

	private ResultadoSimulacion generarSimulacion(Cuenta cuenta, Integer anticipo) throws ExcepcionControladaError {
		
		ResultadoSimulacion resultado = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.SPP_CALCULAR.getQuery());
			cs.setInt(1, cuenta.getNumero());
			cs.setInt(2, (cuenta.getClass().equals(CuentaCementerio.class)) ? ((CuentaCementerio) cuenta).getNumeroNomenclador() : 0);
			cs.setInt(3, cuenta.getSistema());
			cs.setInt(4, anticipo);
			
			cs.execute();
			
			rs = cs.getResultSet();
			
			if (rs.next())
				resultado = this.recuperarResultadoSimulacion(rs.getInt(1), 
															  rs.getInt(2), 
															  rs.getFloat(3), 
															  rs.getFloat(4));
			else
				throw new ExcepcionControladaError("No se pudo recuperar el resultado de la simulación ("+QueriesInformix.SPP_CALCULAR.getQuery()+")",null);
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error invocando al SP", e.getCause());
		} finally {			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {}
			}
		}
		
		return resultado;
	}

	private ResultadoSimulacion recuperarResultadoSimulacion(Integer numeroSolicitud, Integer anticipoCalculado, 
															 Float imoprteAnticipo, Float importePlan) throws ExcepcionControladaError {
		if (numeroSolicitud == -1)
			throw new SimulacionIncorrectaException();
			
		return FactoryResultadoSimulacion.generarInstancia(numeroSolicitud, anticipoCalculado, imoprteAnticipo, importePlan);
	}

	private List<PlanSimulacion> recuperarResultado(Integer numeroSolicitud) throws ExcepcionControladaError {
		
		List<PlanSimulacion> listaPlanes = new ArrayList<PlanSimulacion>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareCall(QueriesInformix.SPP_BUSQUEDA_PLANES.getQuery());
			ps.setInt(1, numeroSolicitud);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listaPlanes.add(FactoryPlanSimulacion.generarInstancia(rs.getInt("n_cuota"), 
																	   rs.getFloat("i_cuota")));
			}
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error recuperando los planes", e.getCause());
		} finally {			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		
		return listaPlanes;
	}
}