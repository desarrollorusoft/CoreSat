package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.CorreoOficial;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryCorreoOficial;
import ar.com.cognisys.sat.core.modelo.factory.consultas.FactoryCategoria;

public class DaoConfiguracionCorreoOficial extends Dao {

	public DaoConfiguracionCorreoOficial(Connection connection) {
		super(connection);
	}
	
	public List<CorreoOficial> getAllCorreoOficial() throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CorreoOficial> lista = new ArrayList<CorreoOficial>();
		try {
			ps = this.getConnection().prepareStatement(Queries.OBTENER_LISTA_CORREO_OFICIAL.getSql());
			rs = ps.executeQuery();
			while ( rs.next() ) {
				CorreoOficial c = FactoryCorreoOficial.generarInstancia(rs.getLong("ID"), 
																		rs.getString("Categoria"),
																		rs.getString("Correo"));
				lista.add(c);
			}
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al obtener los correos oficiales", e);
		} finally {
			try {if( rs != null ){rs.close();}}catch (Exception e) {}
			try {if( ps != null ){ps.close();}}catch (Exception e) {}
		}
	}

	public List<Categoria> getAllCategorias() throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Categoria> lista = new ArrayList<Categoria>();
		try {
			ps = this.getConnection().prepareStatement(Queries.OBTENER_CATEGORIAS_CONSULTAS.getSql());
			rs = ps.executeQuery();
			while ( rs.next() ) {
				lista.add(FactoryCategoria.generarInstancia(rs.getLong("id_consulta_categoria"), 
														    rs.getString("nombre")));
			}
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al obtener las categorias", e);
		} finally {
			try {if( ps != null ){ps.close();}}catch (Exception e) {}
			try {if( rs != null ){rs.close();}}catch (Exception e) {}
		} 
	}

	public void crearCorreoOficial(String correo, Categoria categoriaSeleccionada) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		try {
			ps = this.getConnection().prepareStatement(Queries.CREAR_CORREO_OFICIAL.getSql());
			ps.setLong(1, categoriaSeleccionada.getId());
			ps.setString(2, correo);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al intentar crear un nuevo correo oficial", e);
		} finally {
			try {if( ps != null ){ps.close();}}catch (Exception e) {}
		}
	}

	public void eliminarCorreoOficial(CorreoOficial correoSeleccionado) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		try {
			ps = this.getConnection().prepareStatement(Queries.ELIMINAR_CORREO_OFICIAL.getSql());
			ps.setLong(1, correoSeleccionado.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al querer eliminar la cuenta seleccionada", e);
		} finally {
			try {if( ps != null ){ps.close();}}catch (Exception e) {}
		}
	}

	public void actualizarCorreoOficial(Categoria categoria, String correo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		try {
			ps = this.getConnection().prepareStatement(Queries.ACTUALIZAR_CORREO_OFICIAL.getSql());
			ps.setString(1, correo);
			ps.setLong(2, categoria.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al querer editar la cuenta seleccionada", e);
		} finally {
			try {if( ps != null ){ps.close();}}catch (Exception e) {}
		}
	}
}