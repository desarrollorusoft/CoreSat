package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryPantalla;

public class DaoSatConfiguracionGrupos extends Dao{

	public DaoSatConfiguracionGrupos(Connection con) {
		super(con);
	}
	
	public void altaGrupo(Grupo grupo) throws ExcepcionControladaError  {
		
		DaoGrupo dao = new DaoGrupo(this.getConnection());
		grupo.setOrden(dao.recuperarMaximoOrden(grupo)+1);
		dao.insertarGrupo(grupo);
		
		/* Si es raiz */
		if (grupo.getPantalla() != null) {
			grupo.setId( this.recuperarIdGrupoPadre(grupo) );
			this.relacionarGrupo(grupo);
		}
	}
	
	private Long recuperarIdGrupoPadre(Grupo grupo) throws ExcepcionControladaError {

		Long id = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ID_GRUPO.getSql() );
			ps.setString(1, grupo.getNombre());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				id = rs.getLong("idgrupo");
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al recuperar id del nuevo padre", e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
		
		return id;
	}
	
	private void relacionarGrupo(Grupo grupo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RELACIONAR_PANTALLA_GRUPO.getSql() );
			ps.setLong(1, grupo.getPantalla().getId());
			ps.setLong(2, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al agregar el grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	public void editarGrupo(Grupo grupo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.UPDATE_GRUPO.getSql() );
			ps.setString(1, grupo.getNombre());
			ps.setLong(2, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al editar el grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void reubicarGrupo(Grupo grupo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.UPDATE_REUBICAR_GRUPO.getSql() );
			ps.setLong(1, grupo.getPadre().getId());
			ps.setLong(2, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al reubicar el grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	public List<Pantalla> recuperarListaPantallas() throws ExcepcionControladaError {
		
		List<Pantalla> lista = new ArrayList<Pantalla>();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_PANTALLAS.getSql() );
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Pantalla p = FactoryPantalla.generarInstancia(rs.getLong("id"), rs.getString("nombre"));
				p.setListaGrupos(this.recuperarListaGrupos(p));
				
				lista.add(p);
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al recuperar las pantallas", e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
		
		return lista;
	}

	public List<Grupo> recuperarListaGrupos(Pantalla pantalla) throws ExcepcionControladaError {
		return (new DaoGrupo(this.getConnection())).recuperarGrupos(pantalla);
	}

	public void eliminarGrupo(Grupo grupo) throws ExcepcionControladaError {
		for (Item item : grupo.getListaItems()) {
			new DaoItem(this.getConnection()).eliminarItem(item);
		}
		
		for (Grupo hijo : grupo.getListaHijos()) {
			this.eliminarGrupo(hijo);
		}
		
		this.reordenarProximos(grupo);
		
		new DaoGrupo(this.getConnection()).eliminarGrupo(grupo);
	}
	
	private void reordenarProximos(Grupo grupo) throws ExcepcionControladaError {
		DaoGrupo daoGrupo = new DaoGrupo(this.getConnection());
		for(int i = grupo.getOrden(); i < daoGrupo.recuperarMaximoOrden(grupo); i++){
			daoGrupo.actualizarOrden(daoGrupo.recuperarGrupo(grupo, i+1), i);
		}
	}
	
	public void cambiarDisponibilidad(Grupo grupo) throws ExcepcionControladaError {
		new DaoGrupo(this.getConnection()).actualizarDisponibilidad(grupo);
	}
	
	public void cambiarOrden(Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		DaoGrupo daoGrupo = new DaoGrupo(this.getConnection());
		daoGrupo.actualizarOrden(daoGrupo.recuperarGrupo(grupo, ordenNuevo), grupo.getOrden());
		daoGrupo.actualizarOrden(grupo, ordenNuevo);
	}
	
}