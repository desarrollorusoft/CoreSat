package ar.com.cognisys.sat.core.modelo.dao.ppc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.ppc.PorcentajeRecMul;

public class DaoCalculaCod139 extends DaoCalculaCod {

	private static final String RECUPERAR_PORCENTAJE = 
			"select i_porrec, i_pormul from transac_cod_139 where n_transac = ?";

	public DaoCalculaCod139(Connection connection) {
		super( connection );
	}

	@Override
	protected PorcentajeRecMul porcentaje() throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_PORCENTAJE );
			st.setLong( 1, this.calculaCod.getNroTransaccion() );
			rs = st.executeQuery();
			
			return rs.next() ? new PorcentajeRecMul( rs.getInt( "i_porrec" ), rs.getInt( "i_pormul" ) ) : null;
		
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar los porcentajes del codigo 139 para la transaccion " + this.calculaCod.getNroTransaccion(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

}
