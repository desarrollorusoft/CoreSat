package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryUsuarioCF;

public class DaoUsuarioCF extends Dao {

	private static final String SQL_BUSQUEDA_USUARIO_CUIT = "Select u.cuit, u.password as clave, lower(p.email) as correo, p.telefono, p.razon_social\n"  
														  + "From cf_usuarios u, cf_padron p\n"
														  + "Where u.cuit = p.cuit\n"
														  + "And u.cuit = ?";
	
	private static final String SQL_REGISTRO_USUARIO_CABECERA = "Insert Into cf_usuarios(cuit, password) Values ( ? , ? )";

	private static final String SQL_REGISTRO_USUARIO_DATOS = "Insert Into cf_padron(cuit, email, telefono, razon_social) Values ( ? , ? , ? , ? )";

	private static final String UPDATE_CLAVE_USUARIO = "Update recaudaciones:cf_usuarios\n"
													 + "Set password = ?\n"
													 + "Where cuit = ?";

	public DaoUsuarioCF(Connection connection) {
		super(connection);
	}

	public UsuarioCF buscar(String cuit) throws ExcepcionControladaError {
		
		UsuarioCF usuario = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_BUSQUEDA_USUARIO_CUIT );
			ps.setString(1, cuit);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				usuario = FactoryUsuarioCF.generar(cuit, 
												   rs.getString("correo"), 
												   rs.getString("clave"), 
												   rs.getString("telefono"), 
												   rs.getString("razon_social"));	
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return usuario;
	}
	
	public void registrar(UsuarioCF usuario) throws ExcepcionControladaError {
		
		this.registrarCabecera(usuario);
		this.registrarDatos(usuario);
	}

	private void registrarCabecera(UsuarioCF usuario) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_REGISTRO_USUARIO_CABECERA );
			ps.setInt(1, Integer.parseInt(usuario.getCuit()));
			ps.setString(1, usuario.getClave());
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	private void registrarDatos(UsuarioCF usuario) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_REGISTRO_USUARIO_DATOS );
			ps.setInt(1, Integer.parseInt(usuario.getCuit()));
			ps.setString(2, usuario.getCorreo());
			ps.setString(3, usuario.getTelefono());
			ps.setString(4, usuario.getRazonSocial());
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	public void actualizarClave(String cuit, String clave) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( UPDATE_CLAVE_USUARIO );
			ps.setString(1, clave);
			ps.setLong(2, Long.parseLong(cuit));
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}
}