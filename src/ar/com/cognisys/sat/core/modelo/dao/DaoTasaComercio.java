package ar.com.cognisys.sat.core.modelo.dao;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Tasa;
import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryUsuarioCF;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoTasaComercio extends Dao {

	private static final String SQL_BUSQUEDA_TASAS = "{call recaudaciones:sp_ddjj_tasas_cog()}";

	public DaoTasaComercio(Connection connection) {
		super(connection);
	}

	public Map<Integer, Tasa> buscar() throws ExcepcionControladaError {

		Map<Integer, Tasa> tasas = new HashMap<Integer, Tasa>();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.getConnection().prepareCall( SQL_BUSQUEDA_TASAS );
			cs.execute();

			rs = cs.getResultSet();

			while (rs.next())
				tasas.put(rs.getInt(1), new Tasa(rs.getInt(1), rs.getString(2)));
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(cs, rs);
		}
		
		return tasas;
	}
}