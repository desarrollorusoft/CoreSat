package ar.com.cognisys.sat.v2.persistencia.modelo.dao.notificacion;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.notificacion.NotificacionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDao extends DAO {

    private static final String RECUPERAR_NOTIFICACIONES =
            "select n.id_notificacion, n.id_alerta, n.id_usuario, n.flag_visto, n.titulo, n.descripcion, au.fecha \n" +
                    "from web_recau:notificacion n, web_recau:alerta a, web_recau:alerta_usuario au, web_recau:usuario_cogmvl u \n" +
                    "where u.cuit = ? " +
                    "and n.id_alerta = a.id_alerta\n" +
                    "and a.id_alerta = au.id_alerta \n" +
                    "and n.id_usuario = u.id_usuario;";

    private static final String RECUPERAR_NOTIFICACIONES_DEL_DIA =
            "select n.id_notificacion, n.id_alerta, n.id_usuario, n.flag_visto, n.titulo, n.descripcion, au.fecha \n" +
                    "from web_recau:notificacion n, web_recau:alerta a, web_recau:alerta_usuario au, web_recau:usuario_cogmvl u \n" +
                    "where au.fecha = TODAY\n" +
                    "and n.id_alerta = a.id_alerta\n" +
                    "and a.id_alerta = au.id_alerta \n" +
                    "and n.id_usuario = u.id_usuario;";

    private static final String CREAR_NOTIFICACION =
            "insert into web_recau:notificacion (id_alerta, id_usuario, titulo, descripcion) values (?, ?, ?, ?);";

    private static final String VISAR_NOTIFICACION =
            "update web_recau:notificacion set flag_visto = 't' \n" +
                    "where id_usuario = (select id_usuario from web_recau:usuario_cogmvl where cuit = ?)";
    private static final String ELIMINAR_NOTIFICACION = "delete from  web_recau:notificacion where id_alerta = ?";


    public NotificacionDao(Connection connection) {
        super(connection);
    }

    public List<NotificacionDTO> recuperar(String cuit) throws ErrorEnBaseException {

        List<NotificacionDTO> lista = new ArrayList<NotificacionDTO>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = super.prepareStatement(RECUPERAR_NOTIFICACIONES);
            ps.setString(1, cuit);
            rs = ps.executeQuery();

            while (rs.next())
                lista.add(this.convertirNotificacion(rs));

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursoRS(rs);
            super.cerrarRecursoST(ps);
        }

        return lista;
    }

    public void crear(NotificacionDTO dto) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        try {
            ps = super.prepareStatement(CREAR_NOTIFICACION);
            ps.setInt(1, dto.getIdAlerta());
            ps.setInt(2, dto.getIdUsuario());
            ps.setString(3, dto.getTitulo());
            ps.setString(4, dto.getDescripcion());
            ps.executeUpdate();


        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursoST(ps);
        }
    }

    public void visarNotificacion(String cuit) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        try {
            ps = super.prepareStatement(VISAR_NOTIFICACION);
            ps.setString(1, cuit);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursoST(ps);
        }
    }

    public List<NotificacionDTO> recuperarDelDia() throws ErrorEnBaseException {
        List<NotificacionDTO> lista = new ArrayList<NotificacionDTO>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = super.prepareStatement(RECUPERAR_NOTIFICACIONES_DEL_DIA);
            rs = ps.executeQuery();

            while (rs.next())
                lista.add(this.convertirNotificacion(rs));

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursoRS(rs);
            super.cerrarRecursoST(ps);
        }

        return lista;
    }

    public void eliminarPorAlerta(Integer idAlerta) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        try {
            ps = super.prepareStatement(ELIMINAR_NOTIFICACION);
            ps.setInt(1, idAlerta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursoST(ps);
        }
     }

    private NotificacionDTO convertirNotificacion(ResultSet rs) throws SQLException {
        return new NotificacionDTO(
                rs.getInt("id_notificacion"),
                rs.getInt("id_alerta"),
                rs.getInt("id_usuario"),
                rs.getBoolean("flag_visto"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getDate("fecha")
        );
    }
}
