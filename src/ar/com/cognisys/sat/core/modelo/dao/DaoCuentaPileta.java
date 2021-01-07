package ar.com.cognisys.sat.core.modelo.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.natatorio.FactoryCuentaPileta;

public class DaoCuentaPileta extends Dao {
	
	public DaoCuentaPileta(Connection connection) {
		super(connection);
	}
	
	public List<Cuenta> recuperarCuentas(Integer idUsuario) throws ErrorRecuperacionDatosException {
		List<Cuenta> lista = new ArrayList<Cuenta>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_CUENTAS_NATATORIOS.getQuery());
			ps.setInt(1, idUsuario);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				CuentaPileta c = this.recuperarCuenta(rs.getString("tipo_documento"), rs.getBigDecimal("nro_documento"));
				c.setAlias( rs.getString("descripcion") );
				lista.add( c );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return lista;
	}

	public List<Cuenta> recuperarCuentas(Integer idUsuario, int page) throws ErrorRecuperacionDatosException {
		List<Cuenta> lista = new ArrayList<Cuenta>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement(QueriesInformix.BUSQUEDA_CUENTAS_NATATORIOS_PAGINADA.getQuery());
			ps.setInt(1, page*10);
			ps.setInt(2, 10);
			ps.setInt(3, idUsuario);
			rs = ps.executeQuery();

			while (rs.next()) {
				CuentaPileta c = this.recuperarCuenta(rs.getString("tipo_documento"), rs.getBigDecimal("nro_documento"));
				c.setAlias( rs.getString("descripcion") );
				lista.add( c );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}

	public CuentaPileta recuperarCuenta(String tipoDocumento, BigDecimal documento) throws ExcepcionControladaError {
		
		CallableStatement cs = null;

		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_BOLETA_NATATORIOS.getQuery());
			cs.setString(1, tipoDocumento);
			cs.setBigDecimal(2, documento);
			cs.execute();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(cs);
		}
		
		return this.recuperarDatosCuenta(tipoDocumento, documento);
	}

	private CuentaPileta recuperarDatosCuenta(String tipoDocumento, BigDecimal documento) throws ExcepcionControladaError  {
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		CuentaPileta cuenta = null;
		
		try {
			ps = this.getConnection().prepareStatement(QueriesInformix.RECUPERACION_DATOS_NATATORIOS.getQuery());
			ps.setString(1, tipoDocumento);
			ps.setBigDecimal(2, documento);
			rs = ps.executeQuery();
			
			if (rs.next())
				cuenta = FactoryCuentaPileta.generarInstancia(rs.getString("d_nombre").trim(),
																	rs.getString("d_apellido").trim(), 
																	documento, 
																	TiposDocumento.getTipoDocumentoPorNombrePiletas(tipoDocumento),
																	rs.getString("d_sede").trim(), 
																	rs.getInt("n_socio"), 
																	rs.getInt("id_unico"), 
																	rs.getLong("n_comprob"), 
																	rs.getFloat("i_deuda"), 
																	rs.getDate("f_vto"));

		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return cuenta;
	}
}