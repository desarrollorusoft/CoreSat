package ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.generics.assistant.timestamp.TimestampAssistant;
import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator.AlertaUsuarioDTOCreator;

public class AlertaUsuarioDAO extends DAO {

    private static final String RECUPERAR =
			"select a.id_alerta, a.titulo, a.descripcion, au.id_usuario, au.fecha, au.flag_notificada \n" +
                    "from web_recau:alerta a, web_recau:alerta_usuario au, web_recau:usuario_cogmvl u \n" +
                    "where u.cuit = ?\n" +
                    "and a.id_alerta = au.id_alerta \n" +
                    "and au.id_usuario = u.id_usuario;";
    private static final String RECUPERAR_DEL_DIA =
			"select a.id_alerta, a.titulo, a.descripcion, au.id_usuario, au.fecha, au.flag_notificada \n" +
                    "from web_recau:alerta a, web_recau:alerta_usuario au, web_recau:usuario_cogmvl u \n" +
                    "where au.fecha = TODAY\n" +
                    "and a.id_alerta = au.id_alerta \n" +
                    "and au.id_usuario = u.id_usuario;";
    private static final String NOTIFICAR_ALERTAS =
            "update web_recau:alerta_usuario set flag_notificada = 't' where fecha = TODAY;";

    private static final String CREAR_ALERTA =
			"insert into web_recau:alerta (titulo, descripcion) \n" + 
			"values (?, ?) ";

    private static final String CREAR_ALERTA_USUARIO =
			"insert into web_recau:alerta_usuario (id_alerta, id_usuario, fecha) \n" + 
			"values (?, (select id_usuario from web_recau:usuario_cogmvl where cuit = ?), ?) ";

    private static final String ELIMINAR_ALERTA_USUARIO =
			"delete from web_recau:alerta_usuario \n" + 
			"where id_alerta = ? ";

    private static final String ELIMINAR_ALERTA =
			"delete from web_recau:alerta \n" +
			"where id_alerta = ? ";


	private static final String RECUPERAR_UNICA = 
			"select a.id_alerta, a.titulo, a.descripcion, au.id_usuario, au.fecha, au.flag_notificada " +
			"from web_recau:alerta a, web_recau:alerta_usuario au, web_recau:usuario_cogmvl u " +
			"where u.cuit = ? " +
			"and a.titulo = ? " +
			"and a.descripcion = ? " +
			"and date(au.fecha) = date(?) " +
			"and a.id_alerta = au.id_alerta " +
			"and au.id_usuario = u.id_usuario ";

	public AlertaUsuarioDAO(Connection connection) {
		super( connection );
	}

	public List<AlertaUsuarioDTO> recuperar(String cuit) throws ErrorEnBaseException {

		List<AlertaUsuarioDTO> lista = new ArrayList<AlertaUsuarioDTO>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( RECUPERAR );
			ps.setString( 1, cuit );
			
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

	public List<AlertaUsuarioDTO> recuperarDelDia() throws ErrorEnBaseException {

		List<AlertaUsuarioDTO> lista = new ArrayList<AlertaUsuarioDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = super.prepareStatement( RECUPERAR_DEL_DIA );

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

	private AlertaUsuarioDTO extraer(ResultSet rs) throws SQLException {
		AlertaUsuarioDTOCreator creator = new AlertaUsuarioDTOCreator();
		
		return creator
				.id( rs.getInt( "id_alerta" ) )
				.titulo( rs.getString( "titulo" ) )
				.descripcion( rs.getString( "descripcion" ) )
				.idUsuario( rs.getInt( "id_usuario" ) )
				.fecha( rs.getDate( "fecha" ) )
                .notificada( rs.getBoolean("flag_notificada"))
				.create();
	}

	public void crearAlerta(AlertaDTO alertaDTO) throws ErrorEnBaseException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( CREAR_ALERTA, PreparedStatement.RETURN_GENERATED_KEYS );
			
			ps.setString( 1, alertaDTO.getTitulo() );
			ps.setString( 2, alertaDTO.getDescripcion() );
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			while ( rs.next() )
				alertaDTO.setId( rs.getInt( 1 ) );
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public void crearAlertaUsuario(AlertaUsuarioDTO alertaDTO) throws ErrorEnBaseException {

		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( CREAR_ALERTA_USUARIO );
			
			ps.setInt( 1, alertaDTO.getId() );
			ps.setString( 2, alertaDTO.getCuit() );
			ps.setTimestamp( 3, TimestampAssistant.transform( alertaDTO.getFecha() ) );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void eliminarAlertaUsuario(AlertaUsuarioDTO alertaDTO) throws ErrorEnBaseException {
		
		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( ELIMINAR_ALERTA_USUARIO );
			
			ps.setInt( 1, alertaDTO.getId() );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void eliminarAlerta(AlertaUsuarioDTO alertaDTO) throws ErrorEnBaseException {

		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( ELIMINAR_ALERTA );
			
			ps.setInt( 1, alertaDTO.getId() );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public AlertaUsuarioDTO recuperar(String cuit, String titulo, String descripcion, Date fecha) throws ErrorEnBaseException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( RECUPERAR_UNICA );
			
			ps.setString( 1, cuit );
			ps.setString( 2, titulo );
			ps.setString( 3, descripcion );
			ps.setTimestamp( 4, TimestampAssistant.transform( fecha ) );
			
			
			rs = ps.executeQuery();
			
			if ( rs.next() )
				return this.extraer( rs );
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}

    public void actualizarDelDia() throws ErrorEnBaseException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement( NOTIFICAR_ALERTAS );
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new ErrorEnBaseException( e.getMessage(), e );
        } finally {
            super.cerrarRecursoST( ps );
        }

    }
}
