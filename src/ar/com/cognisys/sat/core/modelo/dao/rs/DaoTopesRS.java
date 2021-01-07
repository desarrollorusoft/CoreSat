package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoTopesRS extends DAO {

	private static final String SQL_RECUPERAR_TOPES = "Select n_ano as ano, f_adhesion as fecha, i_sh_anual as facturacion_padron, \n"
													+ "		  i_sh_total as facturacion_acumulada, c_pyp as metros_pyp, c_personas as cantidad_personas, \n"
													+ "		  c_cuenta as cuenta_limite, c_oep_mt as metros_toldo, c_oep_ud as unidades_poste, c_sv as unidades_sv \n" 
													+ "From recaudaciones:regimen_simplificado_topes t \n"
													+ "Order by n_ano desc ";

	public DaoTopesRS(Connection connection) {
		super(connection);
	}

	public Map<Integer, TopeRS> recuperar() throws ExcepcionControladaError {
		Map<Integer, TopeRS> mapa = new HashMap<Integer, TopeRS>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERAR_TOPES );
			rs = ps.executeQuery();

			while (rs.next())
				mapa.put(rs.getInt("ano"), new TopeRS(rs.getInt("cuenta_limite"), 
												 	  rs.getFloat("facturacion_padron"), 
												 	  rs.getFloat("facturacion_acumulada"), 
												 	  rs.getInt("cantidad_personas"), 
												 	  rs.getFloat("metros_pyp"), 
												 	  rs.getDate("fecha"),
												 	  rs.getFloat("metros_toldo"),
												 	  rs.getFloat("unidades_poste"),
												 	  rs.getInt("unidades_sv")));
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return mapa;
	}	
}