package ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator.AlertaPublicitariaDTOCreator;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AlertaPublicitariaDAO extends AlertaGeneralDAO {

	private static final String RECUPERAR = 
			"select a.id_alerta, a.titulo, a.descripcion, ap.fecha_inicio, ap.fecha_fin, ap.redireccion \n" + 
			"from web_recau:alerta a, web_recau:alerta_publicitaria ap \n" + 
			"where a.id_alerta = ap.id_alerta \n";

	public AlertaPublicitariaDAO(Connection connection) {
		super( connection );
	}	

	@Override
	protected String getRecuperarQuery() {
		return RECUPERAR;
	}

	@Override
	protected IAlertaGeneralDTO extraer(ResultSet rs) throws SQLException {
		AlertaPublicitariaDTOCreator creator = new AlertaPublicitariaDTOCreator();
		
		return creator
				.id( rs.getInt( "id_alerta" ) )
				.titulo( rs.getString( "titulo" ) )
				.descripcion( rs.getString( "descripcion" ) )
				.fechaInicio( rs.getDate( "fecha_inicio" ) )
				.fechaFin( rs.getDate( "fecha_fin" ) )
				.redireccion( rs.getString( "redireccion" ) )
				.create();
	}
}
