package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.pago.DatosPago;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoTracePagos extends Dao {
	
	public DaoTracePagos(Connection con) {
		super(con);
	}

	public void tracear(String pago, MediosPago mp) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement(QueriesInformix.TRACE_PAGO.getQuery());
			ps.setString(1, mp.getDescripcion());
			ps.setString(2, pago);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudo registrar el pago", null);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void tracear(DatosPago datos, MediosPago mp) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement(QueriesInformix.TRACE_PAGO_DATOS.getQuery());
			ps.setString(1, mp.getDescripcion());
			ps.setString(2, datos.getDatos());
			ps.setString(3, datos.getTasa());
			ps.setString(4, datos.getCuenta());
			ps.setString(5, datos.getRazonSocial());
			ps.setString(6, datos.getRecibo());
			ps.setDate(7, new java.sql.Date(datos.getFechaPago().getTime()));
			ps.setFloat(8, datos.getValor());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudo registrar el pago", null);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}