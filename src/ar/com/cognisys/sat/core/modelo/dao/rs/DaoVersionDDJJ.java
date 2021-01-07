package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.contenedor.ContenedorActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividades;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJOEP;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJSV;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryVersionPadronRS;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorCartelerias;

public class DaoVersionDDJJ extends DAO {

	private static final String SQL_RECUPERO_VERSIONES = "Select v.id_rs_declaracion_version, v.version, v.facturacion, v.cuenta_abl, v.fecha_habilitacion, v.cant_personas, v.flag_completo, v.fecha_actualizacion \n"  
													   + "From web_recau:rs_declaracion_version v, web_recau:rs_declaracion d \n"
													   + "Where d.ano = ? \n"
													   + "And d.cuenta = ? \n"
													   + "And v.id_rs_declaracion = d.id_rs_declaracion";
	
	private static final String UPDATE_ESTADO = "Update web_recau:rs_declaracion_version \n"
											  + "Set flag_completo = ? \n"
											  + "Where id_rs_declaracion = (Select id_rs_declaracion \n"
											  + "							From web_recau:rs_declaracion \n"
											  + "							Where cuenta = ? \n"
											  + "							And ano = ? ) \n"
											  + "And version = ? ";

	private static final String SQL_INSERT_VERSION = "Insert Into web_recau:rs_declaracion_version(id_rs_declaracion, id_usuario, version, facturacion, cuenta_abl, fecha_habilitacion, cant_personas, flag_completo) \n"
												   + "Values ( (Select id_rs_declaracion From web_recau:rs_declaracion Where cuenta = ? And ano = ?) , ? , ? , ? , ? , ? , ? , ? )";

	private static final String SQL_UPDATE_VERSION = "Update web_recau:rs_declaracion_version \n"
												   + "Set id_usuario = ?, version = ?, facturacion = ?, cuenta_abl = ?, fecha_habilitacion = ?, cant_personas = ?, flag_completo = ? \n"
												   + "Where id_rs_declaracion_version = ? ";

	private static final String SQL_UPDATE_RECHAZO_VERSION = "Update web_recau:rs_declaracion_version \n"
														   + "Set fecha_actualizacion = current, id_regimen_simplificado_rechazo = (Select id_regimen_simplificado_rechazo \n"
														   + "																		From web_recau:regimen_simplificado_rechazo \n"
														   + "																		Where codigo = ? ) \n"
														   + "Where id_rs_declaracion_version = ? ";

	private static final String SQL_DELETE_VERSION = "Delete From web_recau:rs_declaracion_version \n"
												   + "Where id_rs_declaracion_version = ? ";
	
	private static final String SQL_RECUPERAR_DECLARACION = "SELECT f_habilitacion as fecha_habilitacion, n_rubro as codigo_rubro,i_imponible as importe,n_personas as cantidad_personas, \n"
														  + "	    cuenta_abl as cuenta_abl,c_pyp1 as total_pyp1,c_pyp2 as total_pyp2,c_pyp3 as total_pyp3,c_pyp4 as total_pyp4,c_pyp5 as total_pyp5, \n"
														  + "	    c_pyp6 as total_pyp6, c_pyp7 as total_pyp7, c_pyp8 as total_pyp8, c_pyp9 as total_pyp9, c_pyp10 as total_pyp10, \n"
														  + "	    c_oep1 as total_oep1,c_oep2 as total_oep2,c_sv1 as sv_1,c_sv2 as sv_2, n_ano as ano, n_orden as vez, fecha_alta \n" 
														  + "FROM recaudaciones:tmp_graba_datos_cab \n" 
														  + "WHERE c_cuenta = ? \n"
														  + "AND n_ano = ? \n"
														  + "AND n_orden = ? \n"
														  + "Order by n_orden asc";
	
	public DaoVersionDDJJ(Connection connection) {
		super(connection);
	}

	public List<VersionPadronRS> recuperar(Integer ano, PadronRS p) throws ExcepcionControladaError {
		
		List<VersionPadronRS> lista = new ArrayList<VersionPadronRS>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_VERSIONES );
			ps.setInt(1, ano);
			ps.setString(2, p.getNumero());
			
			rs = ps.executeQuery();
			
			while (rs.next())
				lista.add( FactoryVersionPadronRS.generar(ano, 
														  rs.getInt("id_rs_declaracion_version"), 
														  rs.getInt("version"), 
														  rs.getInt("cant_personas"), 
														  rs.getString("cuenta_abl"), 
														  rs.getFloat("facturacion"), 
														  rs.getDate("fecha_habilitacion"), 
														  rs.getDate("fecha_actualizacion"), 
														  rs.getInt("flag_completo") == 1) );
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return lista;
	}

	public void actualizarEstado(PadronRS padron) throws ExcepcionControladaError {
		PreparedStatement ps = null;

		try {
			VersionPadronRS version = padron.obtenerUltimaVersion();
			
			ps = this.prepareStatement( UPDATE_ESTADO );
			ps.setInt( 1, version.isCompleto() ? 1 : 0 );
			ps.setString( 2, padron.getNumero() );
			ps.setInt( 3, version.getAno() );
			ps.setInt( 4, version.getVersion() );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void guardar(String cuenta, VersionPadronRS version, Usuario usuario) throws ExcepcionControladaError {
		if ( version.tieneID() )
			this.actualizarDeclaracion( version, usuario.getIdUsuario() );
		else
			this.registrar( cuenta, version, usuario.getIdUsuario() );
	}

	private void registrar(String cuenta, VersionPadronRS version, Integer idUsuario) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_INSERT_VERSION, Statement.RETURN_GENERATED_KEYS );
			ps.setString( 1, cuenta );
			ps.setInt( 2, version.getAno() );
			ps.setInt( 3, idUsuario );
			ps.setInt( 4, version.getVersion() );
			ps.setFloat( 5, version.getFacturacion() );
			ps.setString( 6, version.getCuentaABL() );
			ps.setDate( 7, new java.sql.Date( version.getFechaHabilitacion().getTime() ) );
			ps.setInt( 8, version.getCantidadPersonas() );
			ps.setInt( 9, version.isCompleto() ? 1 : 0 );

			ps.executeUpdate();
			
			ResultSet gk = ps.getGeneratedKeys();
					
			if (gk.next())
				version.setId( gk.getInt(1) );
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void actualizarDeclaracion(VersionPadronRS version, Integer idUsuario) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_UPDATE_VERSION );
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, version.getVersion() );
			ps.setFloat( 3, version.getFacturacion() );
			ps.setString( 4, version.getCuentaABL() );
			ps.setDate( 5, new java.sql.Date( version.getFechaHabilitacion().getTime() ) );
			ps.setInt( 6, version.getCantidadPersonas() );
			ps.setInt( 7, version.isCompleto() ? 1 : 0 );
			ps.setInt( 8, version.getId() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void registrarRechazo(List<PadronRS> listaPadrones, Integer codigoRechazo) throws ExcepcionControladaError {
		
		for (PadronRS padron : listaPadrones)
			this.registrarRechazo(padron.obtenerUltimaVersion(), codigoRechazo);
	}

	private void registrarRechazo(VersionPadronRS version, Integer codigoRechazo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_UPDATE_RECHAZO_VERSION );
			ps.setInt( 1, codigoRechazo );
			ps.setInt( 2, version.getId() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void borrar(VersionPadronRS version) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_DELETE_VERSION );
			ps.setInt( 1, version.getId() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public VersionPadronRS buscarDeclaracionConfirmada(String padron, Integer ano, Integer version) throws ExcepcionControladaError {

		VersionPadronRS v = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERAR_DECLARACION );
			ps.setInt( 1, Integer.parseInt( padron ) );
			ps.setInt( 2, ano );
			ps.setInt( 3, version );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				v = FactoryVersionPadronRS.generar(ano, 
												   null, 
												   version, 
												   rs.getInt("cantidad_personas"), 
												   rs.getString("cuenta_abl"), 
												   rs.getFloat("importe"), 
												   rs.getDate("fecha_habilitacion"), 
												   rs.getDate("fecha_alta"), 
												   true);
				
				if (ano == 2018)
					this.cargar2018((VersionPadronRS2018) v, rs);
				else if (ano == 2019)
					this.cargar2019((VersionPadronRS2019) v, rs);
				else if (ano == 2020)
					this.cargar2020((VersionPadronRS2020) v, rs);
			}
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return v;
	}

	private void cargar2018(VersionPadronRS2018 v, ResultSet rs) throws SQLException {
		// Actividades
		ActividadComercial a = ContenedorActividadComercial.getInstancia().recuperarActividad( rs.getInt( "codigo_rubro" ), v.getAno() );
		v.setActividades( FactoryActividades.generar( a ) );
		
		// PYP
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 1), rs.getFloat( "total_pyp1" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 2), rs.getFloat( "total_pyp2" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 3), rs.getFloat( "total_pyp3" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 4), rs.getFloat( "total_pyp4" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 5), rs.getFloat( "total_pyp5" ) ));
		
		v.setListaCarteleria( this.filtrarCartelerias(v.getListaCarteleria()) );
		
		// OEP
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.TOLDO, rs.getFloat( "total_oep1" ) ) );
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.POSTE, rs.getFloat( "total_oep2" ) ) );
		
		// Servicios Varios
		v.setServiciosVarios( FactoryDDJJSV.generar( rs.getInt( "sv_1" ), rs.getInt( "sv_2" ) ) );
	}

	private void cargar2019(VersionPadronRS2019 v, ResultSet rs) throws SQLException {
		// Actividades
		ActividadComercial a = ContenedorActividadComercial.getInstancia().recuperarActividad( rs.getInt( "codigo_rubro" ), v.getAno() );
		v.setActividades( FactoryActividades.generar( a ) );
		
		// PYP
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 1), rs.getFloat( "total_pyp1" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 2), rs.getFloat( "total_pyp2" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 3), rs.getFloat( "total_pyp3" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 4), rs.getFloat( "total_pyp4" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 5), rs.getFloat( "total_pyp5" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 6), rs.getFloat( "total_pyp6" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 7), rs.getFloat( "total_pyp7" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 8), rs.getFloat( "total_pyp8" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 9), rs.getFloat( "total_pyp9" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 10), rs.getFloat( "total_pyp10" ) ));
		
		v.setListaCarteleria( this.filtrarCartelerias(v.getListaCarteleria()) );
		
		// OEP
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.TOLDO, rs.getFloat( "total_oep1" ) ) );
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.POSTE, rs.getFloat( "total_oep2" ) ) );
		
		// Servicios Varios
		v.setServiciosVarios( FactoryDDJJSV.generar( rs.getInt( "sv_1" ), rs.getInt( "sv_2" ) ) );
	}
	

	private void cargar2020(VersionPadronRS2020 v, ResultSet rs) throws SQLException {
		// Actividades
		ActividadComercial a = ContenedorActividadComercial.getInstancia().recuperarActividad( rs.getInt( "codigo_rubro" ), v.getAno() );
		v.setActividades( FactoryActividades.generar( a ) );
		
		// PYP
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 1), rs.getFloat( "total_pyp1" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 2), rs.getFloat( "total_pyp2" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 3), rs.getFloat( "total_pyp3" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 4), rs.getFloat( "total_pyp4" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 5), rs.getFloat( "total_pyp5" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 6), rs.getFloat( "total_pyp6" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 7), rs.getFloat( "total_pyp7" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 8), rs.getFloat( "total_pyp8" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 9), rs.getFloat( "total_pyp9" ) ));
		v.getListaCarteleria().add( FactoryDDJJCarteleria.generar( ContenedorCartelerias.getInstancia().get(v.getAno(), 10), rs.getFloat( "total_pyp10" ) ));
		
		v.setListaCarteleria( this.filtrarCartelerias(v.getListaCarteleria()) );
		
		// OEP
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.TOLDO, rs.getFloat( "total_oep1" ) ) );
		v.getListaOEP().add( FactoryDDJJOEP.generar( TipoOEP.POSTE, rs.getFloat( "total_oep2" ) ) );
		
		// Servicios Varios
		v.setServiciosVarios( FactoryDDJJSV.generar( rs.getInt( "sv_1" ), rs.getInt( "sv_2" ) ) );
	}

	private List<DDJJCarteleria> filtrarCartelerias(List<DDJJCarteleria> listaCarteleria) {
		List<DDJJCarteleria> lista = new ArrayList<DDJJCarteleria>();
		
		for (DDJJCarteleria carteleria : listaCarteleria)
			if (carteleria.getMetros() > 0.0f)
				lista.add( carteleria );
		
		return lista;
	}
}