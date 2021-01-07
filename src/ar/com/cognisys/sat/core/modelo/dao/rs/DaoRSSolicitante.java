package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSSolicitante;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoRSSolicitante extends Dao {

	private static final String SQL_REGISTRO_DATO = "Insert Into web_recau:regimen_simplificado_solicitante(nombre, apellido, id_caracter, correo, telefono, celular)\n"
												  + "Values ( ? , ? , ? , ? , ? , ? )";
	
	private static final String SQL_RECUPERO_ID = "Select id_regimen_simplificado_solicitante as id\n"
												+ "From web_recau:regimen_simplificado_solicitante\n"
												+ "Where nombre = ?\n"
												+ "And apellido = ?\n"
												+ "And id_caracter = ?\n"
												+ "And correo = ?\n"
												+ "And telefono = ?\n"
												+ "And celular = ?";

	private static final String UPDATE_ACTUALIZACION_DATOS = "Update web_recau:regimen_simplificado_solicitante\n"
														   + "Set nombre = ?, apellido = ?, id_caracter = ?, correo = ?, telefono = ?, celular = ?\n"
														   + "Where id_regimen_simplificado_solicitante = (select id_regimen_simplificado_solicitante\n"
														   + "											   from web_recau:regimen_simplificado_cabecera\n"
														   + "											   where cuit = ?)";

	public DaoRSSolicitante(Connection connection) {
		super(connection);
	}

	public Integer registrar(IRSSolicitante solicitante) throws ExcepcionControladaError {
		
		this.registrarDatos(solicitante);
		return this.recuperarId(solicitante);
	}
	
	private void registrarDatos(IRSSolicitante solicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareCall( SQL_REGISTRO_DATO );
			ps.setString(1, solicitante.getNombre());
			ps.setString(2, solicitante.getApellido());
			ps.setInt(3, solicitante.getCaracter().getId());
			ps.setString(4, solicitante.getCorreo());
			ps.setString(5, solicitante.getTelefono());
			ps.setString(6, solicitante.getCelular());
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}
	
	public Integer recuperarId(IRSSolicitante solicitante) throws ExcepcionControladaError {
		
		Integer id = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareCall( SQL_RECUPERO_ID );
			ps.setString(1, solicitante.getNombre());
			ps.setString(2, solicitante.getApellido());
			ps.setInt(3, solicitante.getCaracter().getId());
			ps.setString(4, solicitante.getCorreo());
			ps.setString(5, solicitante.getTelefono());
			ps.setString(6, solicitante.getCelular());
			
			rs = ps.executeQuery();
			
			if (rs.next())
				id = rs.getInt("id");
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return id;
	}

	public void actualizar(String cuit, IRSSolicitante solicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareCall( UPDATE_ACTUALIZACION_DATOS );
			ps.setString(1, solicitante.getNombre());
			ps.setString(2, solicitante.getApellido());
			ps.setInt(3, solicitante.getCaracter().getId());
			ps.setString(4, solicitante.getCorreo());
			ps.setString(5, solicitante.getTelefono());
			ps.setString(6, solicitante.getCelular());
			ps.setString(7, cuit);
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorActualizandoRegistracionException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}
}