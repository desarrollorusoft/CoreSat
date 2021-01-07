package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoPermisoUsuario extends DAO {

	public DaoPermisoUsuario(Connection connection) {
		super(connection);
	}

	public void registrar(Integer idUsuario, Cuenta cuenta) throws ExcepcionControladaError {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		
		String sql = "Insert Into web_recau:permiso_gestion(id_usuario_titular, id_usuario_gestor, tipo_cuenta, cuenta, id_permiso_sat, id_estado_permiso_sat) \n"
				   + "Values ( ? , ? , ? , ? , (select id_permiso_sat from web_recau:permiso_sat where clave = 'COMPLETO') , (select id_estado_permiso_sat from web_recau:estado_permiso_sat where clave = 'ACTIVO') )";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, idUsuario);
			ps.setInt( 3, cuenta.getTipoCuenta().getCodigo_usuario());
			ps.setString( 4, cuenta.getDatoCuenta() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo registrar el permiso de nivel 3", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}