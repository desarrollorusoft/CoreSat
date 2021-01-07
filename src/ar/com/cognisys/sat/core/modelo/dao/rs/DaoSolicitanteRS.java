package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.rs.SolicitanteRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactorySolicitanteRS;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryCaracterSAT;

public class DaoSolicitanteRS extends DAO {

	private static final String SQL_RECUPERO_ID = "Select id_regimen_simplificado_solicitante as id \n"
												+ "From web_recau:regimen_simplificado_solicitante \n"
												+ "Where cuit_solicitante = ? \n"
												+ "Union \n"
												+ "Select id_regimen_simplificado_solicitante as id \n"
												+ "From web_recau:regimen_simplificado_solicitante \n"
												+ "Where nombre = ? \n"
												+ "And apellido = ? \n"
												+ "And id_caracter = ? \n"
												+ "And correo = ? \n"
												+ "And telefono = ? \n"
												+ "And celular = ? ";
	
	private static final String SQL_REGISTRO_DATO = "Insert Into web_recau:regimen_simplificado_solicitante(nombre, apellido, id_caracter, correo, telefono, celular, cuit_solicitante)\n"
			  									  + "Values ( ? , ? , ? , ? , ? , ? , ? )";
	
	private static final String UPDATE_ACTUALIZACION_DATOS = "Update web_recau:regimen_simplificado_solicitante\n"
														   + "Set nombre = ?, apellido = ?, id_caracter = ?, correo = ?, telefono = ?, celular = ?, cuit_solicitante = ? \n"
														   + "Where id_regimen_simplificado_solicitante = ? ";

	private static final String SQL_EXISTE_RELACION = "Select 1 \n"
													+ "From web_recau:rs_solicitante \n"
													+ "Where id_regimen_simplificado_solicitante = ? \n"
													+ "And cuit_comercio = ? ";

	private static final String REGISTRAR_RELACION = "Insert Into web_recau:rs_solicitante(cuit_comercio, id_regimen_simplificado_solicitante)\n"
												   + "Values ( ? , ? ) ";

	private static final String SQL_RECUPERAR_POR_CUIT = "Select s.nombre, s.apellido, s.correo, s.telefono, s.celular, s.fecha_alta, s.cuit_solicitante as cuit, \n"
													   + "		 c.id_caracter, c.clave as clave_caracter, c.descripcion as descripcion_caracter \n"
													   + "From web_recau:rs_solicitante r, web_recau:regimen_simplificado_solicitante s, web_recau:caracter c \n"
													   + "Where r.cuit_comercio = ? \n"
													   + "And r.id_regimen_simplificado_solicitante = s.id_regimen_simplificado_solicitante \n"
													   + "And s.id_caracter = c.id_caracter ";
	
	public DaoSolicitanteRS(Connection connection) {
		super(connection);
	}

	public Integer buscarSolicitanteRS(String cuitComercio, SolicitanteRS solicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_ID );
			ps.setString(1, cuitComercio);
			ps.setString(2, solicitante.getNombre());
			ps.setString(3, solicitante.getApellido());
			ps.setInt(4, solicitante.getCaracter().getId());
			ps.setString(5, solicitante.getCorreo());
			ps.setString(6, solicitante.getTelefono());
			ps.setString(7, solicitante.getCelular());
			
			rs = ps.executeQuery();
			
			if (rs.next())
				return rs.getInt("id");
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return null;
	}

	public Integer registrar(String cuitComercio, SolicitanteRS solicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement(SQL_REGISTRO_DATO, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, solicitante.getNombre());
			ps.setString(2, solicitante.getApellido());
			ps.setInt(3, solicitante.getCaracter().getId());
			ps.setString(4, solicitante.getCorreo());
			ps.setString(5, solicitante.getTelefono());
			ps.setString(6, solicitante.getCelular());
			ps.setString(7, solicitante.getCuit());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return null;
	}

	public void actualizar(Integer idSolicitante, SolicitanteRS solicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( UPDATE_ACTUALIZACION_DATOS );
			ps.setString(1, solicitante.getNombre());
			ps.setString(2, solicitante.getApellido());
			ps.setInt(3, solicitante.getCaracter().getId());
			ps.setString(4, solicitante.getCorreo());
			ps.setString(5, solicitante.getTelefono());
			ps.setString(6, solicitante.getCelular());
			ps.setString(7, solicitante.getCuit());
			ps.setInt(8, idSolicitante);
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoST(ps);
		}
	}

	public boolean existeRelacionSolicitanteComercio(String cuit, Integer idSolicitante) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_EXISTE_RELACION );
			ps.setInt(1, idSolicitante);
			ps.setString(2, cuit);
			
			rs = ps.executeQuery();
			
			return (rs.next());
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
	}

	public void relacionar(String cuit, Integer idSolicitante) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( REGISTRAR_RELACION );
			ps.setString(1, cuit);
			ps.setInt(2, idSolicitante);
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoST(ps);
		}
	}
	
	public SolicitanteRS buscarSolicitante(String cuitComercio) throws ExcepcionControladaError {
		
		SolicitanteRS solicitante = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERAR_POR_CUIT );
			ps.setString(1, cuitComercio);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				solicitante = FactorySolicitanteRS.generar(rs.getString("cuit"), 
														   rs.getString("nombre"), 
														   rs.getString("apellido"), 
														   FactoryCaracterSAT.generar(rs.getInt("id_caracter"), rs.getString("clave_caracter"), rs.getString("descripcion_caracter")), 
														   rs.getString("correo"), 
														   rs.getString("telefono"), 
														   rs.getString("celular"));
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return solicitante;
	}
}