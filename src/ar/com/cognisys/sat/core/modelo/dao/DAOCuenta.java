package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.dao.exception.ActualizacionAliasException;

public class DAOCuenta extends DAO {

    public DAOCuenta(Connection con) {
        super(con);
    }

    public void actualizarAlias(Cuenta cuenta, Integer idUsuario) throws ActualizacionAliasException {

        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(Queries.ACTUALIZAR_CUENTA_ALIAS.getSql());

            ps.setString(1, cuenta.getAlias());
            ps.setInt(2, idUsuario);
            ps.setString(3, cuenta.getDatoCuenta());

            ps.executeUpdate();

        } catch(SQLException e) {
            throw new ActualizacionAliasException(e);
        } finally {
            super.cerrarRecursoST(ps);
        }
    }
}
