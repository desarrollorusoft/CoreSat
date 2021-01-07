package ar.com.cognisys.sat.core.modelo.dao.categoria;

import ar.com.cognisys.conexiones.recursos.AbstractDao;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.consultas.FactoryCategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoCategoria extends AbstractDao {

    private static final String GET =
            "select * from web_recau:consulta_categoria where id_consulta_categoria = ?";

    public DaoCategoria(Connection connection) {
        super(connection);
    }

    public Categoria get(int id) throws ExcepcionControladaError {

        if (id == 0) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = super.getConnection().prepareStatement(GET);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            return rs.next() ? this.newCategoria(rs) : null;

        } catch (SQLException e) {
            throw new ExcepcionControladaError("No es posible recuperar la categoría " + id, e);
        } finally {
            super.cerrarRecursos(rs, ps);
        }

    }

    private Categoria newCategoria(ResultSet rs) throws SQLException {
        return FactoryCategoria.generarInstancia(rs.getLong("id_consulta_categoria"), rs.getString("nombre"));
    }
}
