package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryGrupo;
import ar.com.cognisys.sat.core.modelo.factory.configuracion.FactoryPantalla;

public class DaoSatPreguntasFrecuentes extends Dao {

	public DaoSatPreguntasFrecuentes(Connection connection) {
		super(connection);
	}
	
	@Deprecated
	public Grupo recuperarDatos() throws ExcepcionControladaError {
		Grupo grupo = this.recuperarGrupoPadre();
		
		grupo.setListaHijos((new DaoGrupo(this.getConnection()).recuperarSubgrupos(grupo)));
		grupo.setListaItems((new DaoItem(this.getConnection())).recuperarItems(grupo));
		
		return grupo;
	}
	
	@Deprecated
	public Grupo recuperarDatosDisp() throws ExcepcionControladaError {
		Grupo grupo = this.recuperarGrupoPadre();
		
		grupo.setListaHijos((new DaoGrupo(this.getConnection()).recuperarSubgruposDisp(grupo)));
		grupo.setListaItems((new DaoItem(this.getConnection())).recuperarItemsDisp(grupo));
		
		return grupo;
	}
	
	private Grupo recuperarGrupoPadre() throws ExcepcionControladaError {
		
		Grupo grupo = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_GRUPO_PREGUNTAS.getSql());
			rs = ps.executeQuery();
			
			if (rs.next()) {
				grupo = FactoryGrupo.generarInstancia(rs.getLong("id"), 
													  rs.getString("nombre"), 
													  rs.getInt("disponible") == 1, 
													  rs.getInt("orden"));
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
		
		return grupo;
	}
	
	public void eliminarItem(Item item, Grupo grupo) throws ExcepcionControladaError {
		
		this.ordenarItems(item, grupo);
		(new DaoItem(this.getConnection())).eliminarItem(item);
	}

	private void ordenarItems(Item item, Grupo grupo) throws ExcepcionControladaError {
		DaoItem daoItem = new DaoItem(this.getConnection());
		for(int i = item.getOrden(); i < daoItem.recuperarMaximoOrden(grupo); i++){
			daoItem.actualizarOrden(daoItem.recuperarItem(grupo, i+1), i);
		}
	}
	
	public Pantalla recuperarPantalla() throws ExcepcionControladaError {
		
		Pantalla pantalla = this.recuperarMiPantalla();
		this.cargarGruposItems(pantalla);
		
		return pantalla;
	}

	private Pantalla recuperarMiPantalla() throws ExcepcionControladaError {
		
		Pantalla pantalla = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement( Queries.RECUPERAR_PANTALLA.getSql() );
			ps.setString(1, "Preguntas Frecuentes");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				pantalla = FactoryPantalla.generarInstancia(rs.getLong("id"), rs.getString("nombre"));
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Ocurrior un error al recuperar la pantalla", e);
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
		pantalla.setListaGrupos((new DaoGrupo(this.getConnection())).recuperarGrupos(pantalla));
	}
	
	public void cambiarOrden(Item item, Grupo grupo, Integer ordenNuevo) throws ExcepcionControladaError {
		DaoItem daoItem = new DaoItem(this.getConnection());
		daoItem.actualizarOrden(daoItem.recuperarItem(grupo, ordenNuevo), item.getOrden());
		daoItem.actualizarOrden(item, ordenNuevo);
	}
	
	public void altaItem(Grupo grupo, Item item) throws ExcepcionControladaError{
		DaoItem dao = new DaoItem(this.getConnection());
		item.setOrden(dao.recuperarMaximoOrden(grupo)+1);
		
		dao.insertarItem(item);
		item.setId( dao.recuperarIdItem(item) );
		dao.relacionarItemGrupo(grupo, item);
		
		if (item.tieneArchivo()) {
			new DaoArchivo(this.getConnection()).agregarArchivoItem(item);
		}
	}

	public void cambiarDisponibilidad(Item item) throws ExcepcionControladaError {
		new DaoItem(this.getConnection()).actualizarDisponibilidad(item);
	}

	public void modificarItem(Item item) throws ExcepcionControladaError {
		new DaoItem(this.getConnection()).editarDatosItem(item);
	}
}