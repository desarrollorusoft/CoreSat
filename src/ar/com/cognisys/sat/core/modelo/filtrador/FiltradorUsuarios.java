package ar.com.cognisys.sat.core.modelo.filtrador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public abstract class FiltradorUsuarios {

    public List<Usuario> buscar(int first, int pageSize, String filtro, Integer activo) throws ExcepcionControladaError {
        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();

            return new DaoUsuario(con).recuperarTodosUsuarios(this.queryFiltro(), first, pageSize, filtro, activo);
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
    }

    protected abstract Queries queryFiltro();

    public int total(String filtro, Integer activo) throws ExcepcionControladaError {

        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();

            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            return new DaoUsuario(con).buscarCantidadUsuarios(this.queryFiltroTotal(), filtro, activo);
        } catch (SQLException e) {
            throw new ExcepcionControladaError(e.getMessage(), e);
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
    }

    protected abstract Queries queryFiltroTotal();

}
