package ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator.AlertaVencimientoDTOCreator;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AlertaVencimientoDAO extends AlertaGeneralDAO {

	private static final String RECUPERAR = 
			"select c.nombre as titulo, h.nombre as descripcion, to_date(h.descripcion, '%d / %m / %Y') as fecha \n" + 
			"from info_tributaria:item i, info_tributaria:rel_categoria_item r, info_tributaria:item h, info_tributaria:categoria c \n" + 
			"where r.id_tipo_item = 3 \n" + 
			"and i.flag_publico = 1 \n" + 
			"and i.id_item = r.id_item \n" + 
			"and r.id_categoria = c.id_categoria \n" + 
			"and h.id_padre = i.id_item \n" + 
			"order by c.nombre, fecha \n";

	public AlertaVencimientoDAO(Connection connection) {
		super( connection );
	}	

	@Override
	protected String getRecuperarQuery() {
		return RECUPERAR;
	}

	@Override
	protected IAlertaGeneralDTO extraer(ResultSet rs) throws SQLException {
		AlertaVencimientoDTOCreator creator = new AlertaVencimientoDTOCreator();
		
		return creator
				.titulo( rs.getString( "titulo" ) )
				.descripcion( rs.getString( "descripcion" ) )
				.fecha( rs.getDate( "fecha" ) )
				.create();
	}

}
