package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryTipoRechazoRS;

public class DaoTipoRechazoRS extends Dao {

	private static final String SQL_RECUPERO_LISTA = "Select codigo, descripcion, clave From web_recau:regimen_simplificado_rechazo";

	public DaoTipoRechazoRS(Connection connection) {
		super(connection);
	}
	
	public List<TipoRechazoRS> obtener() throws ExcepcionControladaError {
		
		List<TipoRechazoRS> lista = new ArrayList<TipoRechazoRS>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERO_LISTA );
			rs = ps.executeQuery();
			
			while (rs.next())
				lista.add(FactoryTipoRechazoRS.generar(rs.getInt("codigo"), rs.getString("descripcion"), rs.getString("clave")));
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return lista;
	}
}