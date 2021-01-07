package ar.com.cognisys.sat.core.modelo.dao.ppc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.ppc.PorcentajeRecMul;

public class DaoCalculaCod130 extends DaoCalculaCod {

	private static final String RECUPERAR_PORCENTAJE = 
			"select i_porrec, i_pormul from transac_cod_130 where n_transac = ? and n_cod = 130 and c_plan = 0 and ( n_plan = 0 or n_plan = ?) and n_acta = 0";

	public DaoCalculaCod130(Connection connection) {
		super( connection );
	}

	@Override
	protected PorcentajeRecMul porcentaje() throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_PORCENTAJE );
			st.setLong( 1, this.calculaCod.getNroTransaccion() );
			st.setInt( 2, this.calculaCod.getNroPlan() );
			rs = st.executeQuery();
			
			return rs.next() ? new PorcentajeRecMul( rs.getInt( "i_porrec" ), rs.getInt( "i_pormul" ) ) : null;
		
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar los porcentajes del codigo 130 para la transaccion " + this.calculaCod.getNroTransaccion(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

}
