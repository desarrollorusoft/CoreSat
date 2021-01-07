package ar.com.cognisys.sat.v2.persistencia.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.AutomotorDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorCargandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;

public class AutomotorDAO extends DAO {

	private static final String CARGAR_DATOS_TEMPORALES = "{call sp_pad_veh_rod_cogmvl( ? , ? ) }";
	private static final String OBTENER_DATOS_TEMPORALES = "select * from tmp_pad_vehiculos";

	public AutomotorDAO(Connection connection) {
		super( connection );
	}

	public void cargarDatos(String dominio) throws ErrorCargandoDatos {
		
		CallableStatement cs = null;
		
		try {
			cs = super.prepareCall( CARGAR_DATOS_TEMPORALES );
			cs.setInt( 1, 31 /* TODO [RODRI] VER DONDE UBICAR CODIGO SISTEMA */ );
			cs.setString( 2, dominio );
			
			cs.execute();
			
		} catch ( SQLException e ) {
			throw new ErrorCargandoDatos( dominio, e );
		} finally {
			super.cerrarRecursoST( cs );
		}
	}

	public AutomotorDTO recuperarDatosCargados(Integer numeroCuenta) throws ErrorRecuperandoDatos {
		
		AutomotorDTO datos = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( OBTENER_DATOS_TEMPORALES );

			rs = ps.executeQuery();
			
			if ( rs.next() )
				datos = this.crearDatos( rs );
			
		} catch ( SQLException e ) {
			throw new ErrorRecuperandoDatos( numeroCuenta, e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return datos;
	}

	private AutomotorDTO crearDatos(ResultSet rs) throws SQLException {
		
		String catVehiculo = rs.getString("c_cat_veh");
		String codigoMarca = rs.getString("c_cod_marca");
		String marca = rs.getString("d_marca");
		String modelo = rs.getString("n_modelo");
		String modeloMarca = rs.getString("d_modelo");
		String tipoVehiculo = rs.getString("c_cod_marca");
		String valuacion = rs.getString("n_valuacion");

		return new AutomotorDTO( 
				catVehiculo, 
				codigoMarca, 
				marca, 
				modelo, 
				modeloMarca, 
				tipoVehiculo, 
				valuacion );
	}
	
}
