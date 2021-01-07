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
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryItem;

public class DaoItem extends Dao {

	public DaoItem(Connection connection) {
		super(connection);
	}	
	
	public List<Item> recuperarItems(Grupo grupo) throws ExcepcionControladaError {
		
		List<Item> lista = new ArrayList<Item>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ITEMS.getSql() );
			ps.setLong(1, grupo.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Item i = FactoryItem.generarInstancia(rs.getLong("id"), 
													  rs.getString("titulo"), 
													  rs.getString("descripcion"), 
													  rs.getInt("disponible") == 1,
													  rs.getInt("orden"));
				
				i.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoItem(i));
				
				lista.add(i);
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
	
	public List<Item> recuperarItemsDisp(Grupo grupo) throws ExcepcionControladaError {
		
		List<Item> lista = new ArrayList<Item>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ITEMS.getSql() );
			ps.setLong(1, grupo.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Item i = FactoryItem.generarInstancia(rs.getLong("id"), 
													  rs.getString("titulo"), 
													  rs.getString("descripcion"), 
													  rs.getInt("disponible") == 1,
													  rs.getInt("orden"));
				if(i.isDisponible()){
					i.setArchivo(new DaoArchivo(this.getConnection()).recuperarArchivoItem(i));
					lista.add(i);
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
	
	public void insertarItem(Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.AGREGAR_ITEM.getSql() );
			ps.setString(1, item.getTitulo());
			ps.setString(2, item.getDescripcion());
			ps.setInt(3, item.getOrden());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException("Ocurrio un error al agregar el item", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public Long recuperarIdItem(Item item) throws ExcepcionControladaError {

		Long id = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.BUSCAR_ITEM.getSql() );
			ps.setString(1, item.getTitulo());
			ps.setString(2, item.getDescripcion());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				id = rs.getLong("id");
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

		return id;
	}
	
	public void relacionarItemGrupo(Grupo grupo, Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.AGREGAR_RELACION_ITEM_GRUPO.getSql() );
			ps.setLong(1, grupo.getId());
			ps.setLong(2, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al relacionar el item", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void editarDatosItem(Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.EDITAR_ITEM.getSql() );
			ps.setString(1, item.getTitulo());
			ps.setString(2, item.getDescripcion());
			ps.setLong(3, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al editar el item", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void eliminarItem(Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			if (item.tieneArchivo()) {
				new DaoArchivo(this.getConnection()).eliminarArchivoItem(item);
			}
			
			ps = this.getConnection().prepareStatement( Queries.ELIMINAR_RELACION_GRUPO_ITEM.getSql() );
			ps.setLong(1, item.getId());
			
			ps.executeUpdate();
			
			ps = this.getConnection().prepareStatement( Queries.ELIMINAR_ITEM.getSql() );
			ps.setLong(1, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al eliminar el item", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public Item recuperarItem(Grupo grupo, Integer orden) throws ExcepcionControladaError {
		Item item = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ITEM.getSql() );
			ps.setLong(1, grupo.getId());
			ps.setInt(2, orden);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				item = FactoryItem.generarInstancia(rs.getLong("id"), 
													rs.getString("titulo"), 
													rs.getString("descripcion"),
													rs.getInt("disponible") == 1, 
													rs.getInt("orden"));
			}
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al obtener el item", e);
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
		return item;
	}

	public Integer recuperarMaximoOrden(Grupo grupo) throws ExcepcionControladaError {
		Integer maximo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_MAXIMO_ORDEN_ITEM.getSql() );
			ps.setLong(1, grupo.getId());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				maximo = rs.getInt("max_orden");
			}
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al obtener el maximo orden del item", e);
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

	public void actualizarOrden(Item item, Integer orden) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ACTUALIZAR_ORDEN_ITEM.getSql() );
			ps.setInt(1, orden);
			ps.setLong(2, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al actualizar orden del item", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {}
		}
	}
	
	public void actualizarDisponibilidad(Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ACTUALIZAR_DISPONIBILIDAD_ITEM.getSql() );
			ps.setInt(1, item.isDisponible() ? 1 : 0);
			ps.setLong(2, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrio un error al actualizar disponibilidad del item", e);
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