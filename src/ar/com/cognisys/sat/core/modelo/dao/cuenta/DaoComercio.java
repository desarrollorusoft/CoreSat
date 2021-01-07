package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.InvalidaDatoCuentaException;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryComercio;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;

public class DaoComercio extends DAO {

	public static final String OBTENER_COMERCIO = "Select d_razon_social as razon_social \n"
												+ "From recaudaciones:comercios c \n"
												+ "Where n_cuit = ? \n"
												+ "And c_baja = 0 \n"
												+ "Order by c_cuenta desc";

	private static final String ESTUVO_ADHERIDO_RS = "{call sp_rs_valido_regimen_cog( ? , ? )}";
	
	private static final String YA_ESTA_AGREGADO = "select * from web_recau:usuario_cuentas_rel_cogmvl where cuenta = ? and id_usuario = ? ";

	private static final String RECUPERAR_PADRONES_ASOCIADOS = "SELECT c.c_cuenta, c.d_razon_social, c.n_cuit \n" +
																"FROM web_recau:usuario_cuentas_rel_cogmvl u, recaudaciones:comercios c \n" +
																"WHERE r.id_usuario = ? \n" +
																"AND r.tipo_cuenta = 2 \n" +
																"AND r.cuenta = c.n_cuit \n" +
																"AND c.c_baja = 0";

	public DaoComercio(Connection connection) {
		super( connection );
	}

	public Comercio buscarComercioBase(String cuit) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( OBTENER_COMERCIO );
			ps.setString( 1, cuit );
			
			rs = ps.executeQuery();

			if (rs.next()) 
				return FactoryComercio.generar( cuit, rs.getString( "razon_social" ) );

			throw new InvalidaDatoCuentaException( cuit );
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	public boolean puedeAgregarse(String cuit, Integer ano) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareCall( ESTUVO_ADHERIDO_RS );
			ps.setString( 1, cuit );
			ps.setInt( 2, ano );
			
			rs = ps.executeQuery();
			
			return rs.next() && rs.getInt( 1 ) == 1;
			
		} catch ( SQLException e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally{
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	public boolean noEstaAgregada(String codigo, Integer idUsuario) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( YA_ESTA_AGREGADO );
			ps.setString( 1, codigo );
			ps.setInt(2, idUsuario );
			rs = ps.executeQuery();

			return !rs.next();
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public List<Cuenta> recuperarPadrones(Integer idUsuario) throws ErrorRecuperacionDatosException {
		List<Cuenta> lista = new ArrayList<Cuenta>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( RECUPERAR_PADRONES_ASOCIADOS );
			ps.setInt(1, idUsuario );
			rs = ps.executeQuery();

			while (rs.next()) {
				String nroCuit = rs.getString("n_cuit");
				CuentaComercios c = FactoryCuenta.generarIntanciaCompletaComercios(rs.getInt("c_cuenta"),
																					rs.getString("d_razon_social"),
																					"",
																					false,
																					nroCuit == null? null:nroCuit.trim());
				lista.add( c );
			}
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return lista;
	}
}