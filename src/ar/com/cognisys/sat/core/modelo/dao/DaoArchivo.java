package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.FactoryArchivo;

public class DaoArchivo extends Dao {

	public DaoArchivo(Connection connection) {
		super(connection);
	}

	public void agregarArchivoItem(Item item) throws ExcepcionControladaError {
		this.insertarArchivo(item.getArchivo());
		this.relacionarArchivoItem(item, this.completarIdArchivo(item.getArchivo()));
	}
	
	private void relacionarArchivoItem(Item item, Archivo archivo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.AGREGAR_RELACION_ARCHIVO_ITEM.getSql() );
			ps.setLong(1, item.getId());
			ps.setLong(2, archivo.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al relacionar el item con el archivo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void agregarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		this.insertarArchivo(grupo.getArchivo());
		this.relacionarArchivoGrupo(grupo, this.completarIdArchivo(grupo.getArchivo()));
	}
	
	private void relacionarArchivoGrupo(Grupo grupo, Archivo archivo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.AGREGAR_RELACION_ARCHIVO_GRUPO.getSql() );
			ps.setLong(1, grupo.getId());
			ps.setLong(2, archivo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al relacionar el grupo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void insertarArchivo(Archivo archivo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {			
			ps = this.getConnection().prepareStatement( Queries.INSERT_ARCHIVO.getSql() );
			ps.setString(1, archivo.getNombre());
			ps.setString(2, archivo.getTipoContenido());
			ps.setBytes(3, archivo.getData());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("No se pudo actualizar el documento", e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	private Archivo completarIdArchivo(Archivo archivo) throws ExcepcionControladaError {
		
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ID_ARCHIVO.getSql() );
			ps.setString(1, archivo.getNombre());
			rs = ps.executeQuery();
			
			if (rs.next()) {
				archivo.setId(rs.getLong("id_archivo"));
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
		}
		
		return archivo;
	}
	
	public void eliminarArchivo(Long id) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement(Queries.ELIMINAR_ARCHIVO.getSql());
			ps.setLong(1, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("No se pudo actualizar el documento", e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public Archivo recuperarArchivo(Long id) throws ExcepcionControladaError {
		
		Archivo archivo = null; 
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( Queries.BUSCAR_ARCHIVO.getSql() );
			ps.setLong(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				archivo = FactoryArchivo.generarInstancia(rs.getLong("ID_ARCHIVO"),
														  rs.getString("NOMBRE"), 
														  rs.getString("TIPO_CONTENIDO"),
														  rs.getString("ruta"));
			}
		} catch (Exception e) {
			throw new ErrorEnBaseException("No se pudo recuperar el archivo", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
		}
		
		return archivo;
	}
	
	public Archivo recuperarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		Archivo archivo = null;
		Long id = this.recuperarIdArchivoGrupo(grupo);
		
		if (id != null) {
			archivo = this.recuperarArchivo(id);
		}
		
		return archivo;
	}
	
	private Long recuperarIdArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		
		Long id = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ID_ARCHIVO_GRUPO.getSql() );
			ps.setLong(1, grupo.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				id = rs.getLong("id_archivo");
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
	
	public Archivo recuperarArchivoItem(Item item) throws ExcepcionControladaError {
		Archivo archivo = null;
		Long id = this.recuperarIdArchivoItem(item);
		
		if (id != null) {
			archivo = this.recuperarArchivo(id);
		}
		
		return archivo;
	}
	
	private Long recuperarIdArchivoItem(Item item) throws ExcepcionControladaError {
		Long id = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_ID_ARCHIVO_ITEM.getSql() );
			ps.setLong(1, item.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				id = rs.getLong("id_archivo");
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar archivo", e);
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
	
	public void eliminarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		this.eliminarRelacionGrupo(grupo);
		this.eliminarArchivo(grupo.getArchivo().getId());
	}
	
	public void eliminarArchivoGrupo(Grupo grupo, Archivo archivoViejo) throws ExcepcionControladaError {
		this.eliminarRelacionGrupo(grupo);
		this.eliminarArchivo(archivoViejo.getId());		
		
	}

	private void eliminarRelacionGrupo(Grupo grupo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ELIMINAR_RELACION_GRUPO_ARCHIVO.getSql() );
			ps.setLong(1, grupo.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al eliminar el archivo", e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public void eliminarArchivoItem(Item item) throws ExcepcionControladaError {
		this.eliminarRelacionItem(item);
		this.eliminarArchivo(item.getArchivo().getId());		
		
	}
	
	public void eliminarArchivoItem(Item item, Archivo archivoViejo) throws ExcepcionControladaError {
		this.eliminarRelacionItem(item);
		this.eliminarArchivo(archivoViejo.getId());		
		
	}
	
	private void eliminarRelacionItem(Item item) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.ELIMINAR_RELACION_ITEM_ARCHIVO.getSql() );
			ps.setLong(1, item.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new ErrorEnBaseException("Ocurrio un error al eliminar el archivo", e);
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