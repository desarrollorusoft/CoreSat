package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuota;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudaAdapter;

public class DaoCementerio extends DAO {
	
	public DaoCementerio(Connection connection) {
		super( connection );
	}
	
	public DeudaAdapter recuperarDeudaNormal(Integer numeroCuenta, Integer numeroCuentanoInteger, Date fechaPago) throws ExcepcionControladaError {
		
		return this.recuperarDeuda(numeroCuenta, 
								   numeroCuentanoInteger, 
								   fechaPago, 
								   QueriesInformix.BUSCAR_DEUDAS_CUENTA_CEMENTERIO.getQuery());
	}
	
	private DeudaAdapter recuperarDeuda(Integer numeroCuenta, Integer numeroCuentanoInteger, Date fechaPago, String sql) throws ExcepcionControladaError {
		
		DeudaAdapter deudas = FactoryDeudaAdapter.generar();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.prepareCall(sql);
			cs.setInt(1, numeroCuenta);
			cs.setInt(2, numeroCuentanoInteger);
			cs.setDate(3, new java.sql.Date(fechaPago.getTime()));
			cs.execute();
			
			rs = cs.getResultSet();
			
			while (rs.next()) {
				boolean esVencida = (rs.getDate("f_vencimiento").before(new Date()));
				Cuota cuota = FactoryCuota.generarIntanciaCompleta("CEMENTERIO", // c_tasa = 600
																   "-", 
																   rs.getDate("f_vencimiento"), 
																   rs.getString("n_ano") + "-"+ rs.getString("n_cuota"), 
																   rs.getFloat("i_capital"), 
																   rs.getFloat("i_multa"), 
																   rs.getFloat("i_recargo"), 
																   Cuota.calcularTotalPeriodo(rs.getDouble("i_capital"), rs.getDouble("i_multa"), rs.getDouble("i_recargo")),
																   rs.getInt("n_transac"),
																   (rs.getInt("n_registro") == 1),
																   600);
				deudas.agregarCouta(cuota, esVencida);
			}
			
			deudas.caulcularTotales();
			deudas.generarAdapters();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
		
		return deudas;
	}

}