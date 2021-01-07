package ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public abstract class AlertaGeneralDAO extends DAO {

	public AlertaGeneralDAO(Connection connection) {
		super( connection );
	}
	
	public List<IAlertaGeneralDTO> recuperar() throws ErrorEnBaseException {

		List<IAlertaGeneralDTO> lista = new ArrayList<IAlertaGeneralDTO>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( this.getRecuperarQuery() );
			
			rs = ps.executeQuery();
			
			while ( rs.next() )
				lista.add( this.extraer( rs ) );
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return lista;
	}

	protected abstract String getRecuperarQuery();

	protected abstract IAlertaGeneralDTO extraer(ResultSet rs) throws SQLException;

}
