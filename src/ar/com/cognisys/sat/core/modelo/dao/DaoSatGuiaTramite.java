package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryPantalla;

public class DaoSatGuiaTramite extends Dao {

	public DaoSatGuiaTramite(Connection connection) {
		super(connection);
	}

	/*
	 * Se recupera la pantalla
	 * 					 |-> Grupos
	 * 					 |-> Items + Grupos
	 * */
	public Pantalla recuperarDatos() throws ExcepcionControladaError {
		
		Pantalla pantalla = this.recuperarPantalla();
		this.cargarGruposItems(pantalla);
		
		return pantalla;
	}
	
	private Pantalla recuperarPantalla() throws ExcepcionControladaError {
		
		Pantalla pantalla = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_PANTALLA.getSql() );
			ps.setString(1, "Guia Tramites");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				pantalla = FactoryPantalla.generarInstancia(rs.getLong("id"), rs.getString("nombre"));
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
		
		return pantalla;
	}
	
	private void cargarGruposItems(Pantalla pantalla) throws ExcepcionControladaError {
		pantalla.setListaGrupos(new DaoGrupo(this.getConnection()).recuperarGrupos(pantalla));
	}
	
	public void agregarArchivo(Item item) throws ExcepcionControladaError {
		new DaoArchivo(this.getConnection()).agregarArchivoItem(item);
	}

	public void cambiarOrden(Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		DaoGrupo daoGrupo = new DaoGrupo(this.getConnection());
		daoGrupo.actualizarOrden(daoGrupo.recuperarGrupo(grupo, ordenNuevo), grupo.getOrden());
		daoGrupo.actualizarOrden(grupo, ordenNuevo);
	}

	public void cambiarDisponibilidad(Item item) throws ExcepcionControladaError {
		new DaoItem(this.getConnection()).actualizarDisponibilidad(item);
	}
	
	public void altaItem(Grupo grupo, Item item) throws ExcepcionControladaError{
		new DaoSatPreguntasFrecuentes(this.getConnection()).altaItem(grupo, item);
	}	
	
	public void actualizarArchivoItem(Item item, Archivo archivoViejo) throws ExcepcionControladaError {
		(new DaoArchivo(this.getConnection())).eliminarArchivoItem(item, archivoViejo);
		
		if (item.tieneArchivo()) {
			(new DaoArchivo(getConnection())).agregarArchivoItem(item);
		}
	}

	public void eliminarItem(Item item, Grupo grupo) throws ExcepcionControladaError {
		new DaoSatPreguntasFrecuentes(this.getConnection()).eliminarItem(item, grupo);
	}

	public void eliminarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		new DaoArchivo(this.getConnection()).eliminarArchivoGrupo(grupo);
		grupo.setArchivo(null);
	}
	
	public void eliminarArchivoItem(Item item) throws ExcepcionControladaError {
		new DaoArchivo(this.getConnection()).eliminarArchivoItem(item);
		item.setArchivo(null);
	}

	public void agregarArchivoGrupo(Grupo grupo) throws ExcepcionControladaError {
		new DaoArchivo(this.getConnection()).agregarArchivoGrupo(grupo);
	}

	public void modificarItem(Item item) throws ExcepcionControladaError {
		new DaoItem(this.getConnection()).editarDatosItem(item);
	}
}