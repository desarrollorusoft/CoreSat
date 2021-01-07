package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryGrupo;

public class DaoGrupo extends Dao {

	public DaoGrupo(Connection connection) {
		super(connection);
	}
	
	public List<Grupo> recuperarGrupos(Pantalla pantalla) throws ExcepcionControladaError {
		List<Grupo> lista = new ArrayList<Grupo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPOS.getSql() );
			ps.setLong(1, pantalla.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Grupo g = FactoryGrupo.generarInstancia(rs.getLong("id"), 
														rs.getString("nombre"), 
														rs.getInt("disponible") == 1, 
														rs.getInt("orden"));
				g.setPantalla(pantalla);
				g.setListaHijos(this.recuperarSubgrupos(g));
				g.setListaItems(new DaoItem(this.getConnection()).recuperarItems(g));
				g.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoGrupo(g));

				lista.add(g);
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al recuperar los grupos", e);
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
	
	public List<Grupo> recuperarGruposDisp(Pantalla pantalla) throws ExcepcionControladaError {
		List<Grupo> lista = new ArrayList<Grupo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPOS.getSql() );
			ps.setLong(1, pantalla.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Grupo g = FactoryGrupo.generarInstancia(rs.getLong("id"), 
														rs.getString("nombre"), 
														rs.getInt("disponible") == 1, 
														rs.getInt("orden"));
				if(g.isDisponible()){
					g.setPantalla(pantalla);
					g.setListaHijos(this.recuperarSubgruposDisp(g));
					g.setListaItems(new DaoItem(this.getConnection()).recuperarItemsDisp(g));
					g.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoGrupo(g));
					
					lista.add(g);
				}
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al recuperar los grupos", e);
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
	
	public List<Grupo> recuperarSubgrupos(Grupo grupo) throws ExcepcionControladaError {
		
		List<Grupo> lista = new ArrayList<Grupo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPOS_HIJOS.getSql() );
			ps.setLong(1, grupo.getId());
			rs = ps.executeQuery();

			
			while (rs.next()) {
				Grupo g = FactoryGrupo.generarInstancia(rs.getLong("id"), 
														rs.getString("nombre"), 
														rs.getInt("disponible") == 1,
														rs.getInt("orden"));
				
				g.setPadre(grupo);
				g.setListaHijos(this.recuperarSubgrupos(g));
				g.setListaItems(new DaoItem(this.getConnection()).recuperarItems(g));
				g.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoGrupo(g));
				
				lista.add(g);
			}	
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
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
	
	public List<Grupo> recuperarSubgruposDisp(Grupo grupo) throws ExcepcionControladaError {
		
		List<Grupo> lista = new ArrayList<Grupo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPOS_HIJOS.getSql() );
			ps.setLong(1, grupo.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Grupo g = FactoryGrupo.generarInstancia(rs.getLong("id"), 
														rs.getString("nombre"), 
														rs.getInt("disponible") == 1,
														rs.getInt("orden"));
				if (g.isDisponible()){
					g.setPadre(grupo);
					g.setListaHijos(this.recuperarSubgruposDisp(g));
					g.setListaItems(new DaoItem(this.getConnection()).recuperarItemsDisp(g));
					g.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoGrupo(g));
					
					lista.add(g);
				}
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
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
	
	public void insertarGrupo(Grupo grupo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			if (grupo.getPadre() == null){
				ps = this.getConnection().prepareStatement( Queries.INSERT_GRUPO_SPADRE.getSql() );
			} else {
				ps = this.getConnection().prepareStatement( Queries.INSERT_GRUPO_CPADRE.getSql() );
				ps.setLong(3, grupo.getPadre().getId());
			}
			
			ps.setString(1, grupo.getNombre());
			ps.setInt(2, grupo.getOrden());
			
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
	
	public void eliminarGrupo(Grupo grupo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			if (grupo.tieneArchivo()){
				new DaoArchivo(this.getConnection()).eliminarArchivoGrupo(grupo);
			}
			
			ps = this.getConnection().prepareStatement( Queries.ELIMINAR_GRUPO.getSql() );
			ps.setLong(1, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al eliminar el grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	public Grupo recuperarGrupo(Grupo grupo, Integer orden) throws ExcepcionControladaError {
		
		Grupo grupoProximo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			if (grupo.getPadre() == null){
				ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPO_POR_PANTALLA.getSql() );
				ps.setLong(1, grupo.getPantalla().getId());
				ps.setInt(2, orden);
			} else {
				ps = this.getConnection().prepareStatement( Queries.RECUPERAR_GRUPO_POR_PADRE.getSql() );
				ps.setLong(1, grupo.getPadre().getId());
				ps.setInt(2, orden);
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				grupoProximo = FactoryGrupo.generarInstancia(rs.getLong("id"), 
															 rs.getString("nombre"), 
															 rs.getInt("disponible") == 1, 
															 rs.getInt("orden"));
			}
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrió un error al obtener el maximo orden por pantalla", e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (Exception ex) {}
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {}
		}
		return grupoProximo;
	}
	
public Integer recuperarMaximoOrden(Grupo grupo) throws ExcepcionControladaError {
		
		Integer maximo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			if (grupo.getPadre() == null){
				ps = this.getConnection().prepareStatement( Queries.RECUPERAR_MAXIMO_ORDEN_POR_PANTALLA.getSql() );
				ps.setLong(1, grupo.getPantalla().getId());
			} else {
				ps = this.getConnection().prepareStatement( Queries.RECUPERAR_MAXIMO_ORDEN_POR_PADRE.getSql() );
				ps.setLong(1, grupo.getPadre().getId());
			}
			
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				maximo = rs.getInt("max_orden");
			}
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrió un error al obtener el maximo orden por pantalla", e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (Exception ex) {}
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {}
		}
		return maximo;
	}

	public void actualizarOrden(Grupo grupo, Integer orden) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ACTUALIZAR_ORDEN_GRUPO.getSql() );
			ps.setInt(1, orden);
			ps.setLong(2, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrió un error al actualizar orden en grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {}
		}
	}
	
	public void actualizarDisponibilidad(Grupo grupo) throws ExcepcionControladaError{
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ACTUALIZAR_DISPONIBILIDAD_GRUPO.getSql() );
			ps.setInt(1, grupo.isDisponible() ? 1 : 0);
			ps.setLong(2, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al actualizar la disponibilidad", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
}