
package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSDeudaPadron;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSPeriodoDeuda;
import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.EstadoDeclaracion;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.SolicitanteRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryDDJJRS;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryPadronRS;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSDeudaPadron;

public class DaoRS extends DAO {

	private static final String SP_ACTUALIZA_ESTADO = "{call recaudaciones:SP_ACTUALIZA_ESTADO_RS_COGMVL( ? )}";
	
	private static final String SQL_RECUPERAR_DECLARACIONES = "Select d.cuenta, d.ano, d.fecha_actualizacion, e.clave as estado, c.d_razon_social as razon_social \n"
															+ "From recaudaciones:comercios c, web_recau:rs_declaracion d, web_recau:rs_declaracion_estado e \n"
															+ "Where c.n_cuit = ? \n"
															+ "And d.cuenta = c.c_cuenta \n"
															+ "And d.id_rs_declaracion_estado = e.id_rs_declaracion_estado \n"
															+ "Order By ano desc, cuenta asc ";

	private static final String SP_BUSCA_DEUDA_RS_PADRON = "{call sp_rs_calculos_cog( ? , ? , ? )}";

	private static final String SP_CONFIRMA_PADRON = "{call sp_rs_confirma_datos_cog( ? , ? , ? )}";
	
	private static final String UPDATE_REGISTRA_ADHESION = "Update web_recau:rs_declaracion \n"
														 + "Set fecha_actualizacion = current, \n"
														 + "	id_rs_declaracion_estado = (Select id_rs_declaracion_estado \n"
														 + "								From web_recau:rs_declaracion_estado \n"
														 + "								Where clave = ?) \n" 
														 + "Where ano = ? \n"
														 + "And cuenta = ? ";
	
	private static final String UPDATE_ESTADO_DDJJ = "Update web_recau:rs_declaracion \n"
												   + "Set id_rs_declaracion_estado = (Select id_rs_declaracion_estado \n"
												   + "								  From web_recau:rs_declaracion_estado \n"
												   + "								  Where clave = ? )\n"
												   + "Where ano = ? \n"
												   + "And cuenta = ? ";
	
	private static final String UPDATE_RECHAZO_PADRONES = "Update recaudaciones:regimen_simplificado_padrones \n"
													    + "Set c_activado = ? \n"
													    + "Where c_cuenta = ? \n"
													    + "And n_ano = ? ";
	
	private static final String SQL_VENCIMIENTOS_RS = "Select n_cuota as cuota, f_prim_vto as vencimiento \n" 
													+ "From recaudaciones:vencimientos \n" 
													+ "Where n_ano = ? \n" 
													+ "And c_tasa = 221 ";

	private static final String DELETE_DATOS_PADRON_CALCULO = "Delete recaudaciones:tmp_graba_datos_cab \n"
															+ "Where c_cuenta = ? \n"
															+ "And n_orden = ? \n"
															+ "And n_ano = ? ";

	private static final String SQL_INSERT_TMP_GRABA_DATOS = "Insert Into recaudaciones:tmp_graba_datos_cab\n"
														   + "	(c_cuenta,i_imponible,c_pyp1,c_pyp2,c_pyp3,c_pyp4,c_pyp5,c_pyp6,c_pyp7,c_pyp8,c_pyp9,c_pyp10,c_oep1,c_oep2,c_sv1,c_sv2,f_habilitacion,d_contacto,d_mail,n_tel_contacto,n_rubro,n_personas,n_ano,cuenta_abl,celular_actividad,correo_solicitante,nombre_solicitante,apellido_solicitante,id_caracter_solicitante,telefono_solicitante,celular_solicitante,n_orden)\n"
														   + "Values\n" 
														   + "	( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?, ? , ?  , ? , ? , ? , ? , ? , ? , ? )";

	private static final String UPDATE_CONFIRMA_PADRON = "Update web_recau:rs_declaracion_version \n"
													   + "Set fecha_confirmacion = current, fecha_actualizacion = current, flag_completo = 1 \n"
													   + "Where id_rs_declaracion_version = ? ";

	private static final String UPDATE_RECHAZA_PADRON = "Update web_recau:rs_declaracion_version \n"
													  + "Set fecha_confirmacion = current, fecha_actualizacion = current, flag_completo = 1, \n"
													  + "	 id_regimen_simplificado_rechazo = (Select id_regimen_simplificado_rechazo \n"
													  + "										From web_recau:regimen_simplificado_rechazo \n"
													  + "										Where clave = 'MONTO_ACUMULADO_EXCESO' )"
													  + "Where id_rs_declaracion_version = ? ";

	private static final String SQL_VALIDA_CUIT = "SELECT 1 \n"
												+ "FROM recaudaciones:comercios a, recaudaciones:regimen_simplificado_padrones b \n"
												+ "WHERE a.n_cuit = ? \n"
												+ "AND a.c_baja = 0 \n"
												+ "AND a.c_cuenta = b.c_cuenta \n"
												+ "AND not exists (select 1  \n"
												+ "			 FROM web_recau:usuario_cogmvl u \n"
												+ "			 WHERE u.cuit = a.n_cuit)";

	private static final String SP_PUEDE_RECTIFICAR = "{call recaudaciones:sp_rs_rectifica_cog( ? , ? )}";
	
	public DaoRS(Connection connection) {
		super(connection);
	}

	public List<DDJJRS> recuperarDeclaraciones(String cuit) throws ExcepcionControladaError {
		
		this.actualizarEstado( cuit );
		
		return this.buscarDeclaraciones( cuit );
	}
	
	public boolean validoCuit(String cuit) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_VALIDA_CUIT );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			return rs.next();
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	private void actualizarEstado(String cuit) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.prepareCall( SP_ACTUALIZA_ESTADO );
			cs.setString( 1, cuit );
			cs.execute();
			
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoST( cs );
		}
	}

	private List<DDJJRS> buscarDeclaraciones(String cuit) throws ErrorRecuperacionDatosException {
		
		List<DDJJRS> lista = new ArrayList<DDJJRS>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERAR_DECLARACIONES );
			ps.setString( 1, cuit );
			
			rs = ps.executeQuery();

			DDJJRS ddjj = null;
			
			while (rs.next()) {
				int ano = rs.getInt("ano");
				EstadoDeclaracion estado = EstadoDeclaracion.valueOf( rs.getString("estado") );
				
				if (ddjj == null || !ddjj.sos( ano )) {
					ddjj = FactoryDDJJRS.generar(ano, rs.getDate("fecha_actualizacion"));
					lista.add( ddjj );
				}
				
				ddjj.agregar( FactoryPadronRS.generar( rs.getString("cuenta"), rs.getString("razon_social"), estado ) );
			}
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return lista;
	}	
	
	/* SPs */
	public void actualizarEstado(DDJJRS declaracion, PadronRS padron) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_ESTADO_DDJJ );
			ps.setString( 1, padron.getEstado().name() );
			ps.setInt( 2, declaracion.getAno() );
			ps.setString( 3, padron.getNumero() );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void registrarRechazo(DDJJRS ddjj, Integer codigoRechazo) throws ExcepcionControladaError {
		
		for (PadronRS padron : ddjj.getListaPadrones()) {
			this.registrarRechazoRecaudaciones(ddjj.getAno(), padron.getNumero(), codigoRechazo);
			this.actualizarEstado( ddjj, padron );
		}
	}
	
	public void registrarRechazoRecaudaciones(Integer ano, String cuenta, Integer codigoRechazo) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_RECHAZO_PADRONES );
			ps.setInt( 1, codigoRechazo );
			ps.setInt( 2, Integer.parseInt(cuenta) );
			ps.setInt( 3, ano );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public List<RSDeudaPadron> registrarConfirmaciones(DDJJRS ddjj, Usuario usaurio) throws ExcepcionControladaError {
		
		List<RSDeudaPadron> lista = new ArrayList<RSDeudaPadron>();
		
		for ( PadronRS p : ddjj.getListaPadrones() ) {
			VersionPadronRS version = p.obtenerUltimaVersion();
			
			this.limpiarRegistroRecaudaciones( p.getNumero(), version.getVersion(), ddjj.getAno() );
			this.registrarPadronRecaudaciones( p.getNumero(), version, usaurio );
			this.registrarActividadesRecaudaciones( p.getNumero(), version );	

			lista.add( this.generarDeudaPadron( version.getAno(), version.getVersion(), p, this.buscarVencimientos( ddjj.getAno() ) ) );
		}
		
		return lista;
	}

	private List<RSPeriodoDeuda> buscarVencimientos(Integer ano) throws ExcepcionControladaError {

		List<RSPeriodoDeuda> lista = new ArrayList<RSPeriodoDeuda>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_VENCIMIENTOS_RS );
			ps.setInt( 1, ano );
			rs = ps.executeQuery();

			while (rs.next())
				lista.add( new RSPeriodoDeuda( rs.getInt( "cuota" ), rs.getDate( "vencimiento" ), ano ) );

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return lista;
	}
	
	private void limpiarRegistroRecaudaciones(String cuenta, Integer version, Integer ano) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( DELETE_DATOS_PADRON_CALCULO );
			ps.setInt( 1, Integer.parseInt(cuenta) );
			ps.setInt( 2, version );
			ps.setInt( 3, ano );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void registrarPadronRecaudaciones(String cuenta, VersionPadronRS version, Usuario usuario) throws ExcepcionControladaError {
		SolicitanteRS solicitante = usuario.getComercio().getSolicitanteRS(); 
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( SQL_INSERT_TMP_GRABA_DATOS );
			ps.setInt( 1, Integer.parseInt(cuenta) );
			ps.setFloat( 2, version.getFacturacion() );
			this.cargarCartelerias( version, ps );
			this.cargarOEP( version, ps );
			this.cargarServiciosVarios( version, ps );
			ps.setDate( 17, new java.sql.Date( version.getFechaHabilitacion().getTime() ) );
			ps.setString( 18, solicitante.getNombreCompleto() );
			ps.setString( 19, usuario.getCorreo() );
			ps.setString( 20, usuario.getTelefono1() );
			this.cargarActividades( version, ps );
			ps.setInt( 22, version.getCantidadPersonas() );
			ps.setInt( 23, version.getAno() );
			ps.setInt( 24, Integer.parseInt(version.getCuentaABL()) );
			ps.setString( 25, usuario.getTelefono2() );
			ps.setString( 26, solicitante.getCorreo() );
			ps.setString( 27, solicitante.getNombre() );
			ps.setString( 28, solicitante.getApellido() );
			ps.setInt( 29, solicitante.getCaracter().getId() );
			ps.setString( 30, solicitante.getTelefono() );
			ps.setString( 31, solicitante.getCelular() );
			ps.setInt( 32, version.getVersion());
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void cargarCartelerias(VersionPadronRS version, PreparedStatement ps) throws SQLException {
		switch (version.getAno()) {
			case 2018: 
				VersionPadronRS2018 v2018 = (VersionPadronRS2018) version;
				ps.setFloat( 3, v2018.getCarteleria( 1 ) );
				ps.setFloat( 4, v2018.getCarteleria( 2 ) );
				ps.setFloat( 5, v2018.getCarteleria( 3 ) );
				ps.setFloat( 6, v2018.getCarteleria( 4 ) );
				ps.setFloat( 7, v2018.getCarteleria( 5 ) );
				ps.setFloat( 8, v2018.getCarteleria( 6 ) );
				ps.setFloat( 9, v2018.getCarteleria( 7 ) );
				ps.setFloat( 10, v2018.getCarteleria( 8 ) );
				ps.setFloat( 11, v2018.getCarteleria( 9 ) );
				ps.setFloat( 12, v2018.getCarteleria( 10 ) );
				break;
			case 2019: 
				VersionPadronRS2019 v2019 = (VersionPadronRS2019) version;
				ps.setFloat( 3, v2019.getCarteleria( 1 ) );
				ps.setFloat( 4, v2019.getCarteleria( 2 ) );
				ps.setFloat( 5, v2019.getCarteleria( 3 ) );
				ps.setFloat( 6, v2019.getCarteleria( 4 ) );
				ps.setFloat( 7, v2019.getCarteleria( 5 ) );
				ps.setFloat( 8, v2019.getCarteleria( 6 ) );
				ps.setFloat( 9, v2019.getCarteleria( 7 ) );
				ps.setFloat( 10, v2019.getCarteleria( 8 ) );
				ps.setFloat( 11, v2019.getCarteleria( 9 ) );
				ps.setFloat( 12, v2019.getCarteleria( 10 ) );
				break;
			case 2020: 
				VersionPadronRS2020 v2020 = (VersionPadronRS2020) version;
				ps.setFloat( 3, v2020.getCarteleria( 1 ) );
				ps.setFloat( 4, v2020.getCarteleria( 2 ) );
				ps.setFloat( 5, v2020.getCarteleria( 3 ) );
				ps.setFloat( 6, v2020.getCarteleria( 4 ) );
				ps.setFloat( 7, v2020.getCarteleria( 5 ) );
				ps.setFloat( 8, v2020.getCarteleria( 6 ) );
				ps.setFloat( 9, v2020.getCarteleria( 7 ) );
				ps.setFloat( 10, v2020.getCarteleria( 8 ) );
				ps.setFloat( 11, v2020.getCarteleria( 9 ) );
				ps.setFloat( 12, v2020.getCarteleria( 10 ) );
				break;
			default:
				break;
		}
	}

	private void cargarOEP(VersionPadronRS version, PreparedStatement ps) throws SQLException {
		switch (version.getAno()) {
			case 2018: 
				VersionPadronRS2018 v2018 = (VersionPadronRS2018) version;
				ps.setFloat( 13, v2018.getOEPMetrosToldos() );
				ps.setFloat( 14, v2018.getOEPUnidadesPostes() );
				break;
			case 2019: 
				VersionPadronRS2019 v2019 = (VersionPadronRS2019) version;
				ps.setFloat( 13, v2019.getOEPMetrosToldos() );
				ps.setFloat( 14, v2019.getOEPUnidadesPostes() );
				break;
			case 2020: 
				VersionPadronRS2020 v2020 = (VersionPadronRS2020) version;
				ps.setFloat( 13, v2020.getOEPMetrosToldos() );
				ps.setFloat( 14, v2020.getOEPUnidadesPostes() );
				break;
			default:
				break;
		}
	}

	private void cargarServiciosVarios(VersionPadronRS version, PreparedStatement ps) throws SQLException {
		switch (version.getAno()) {
			case 2018: 
				VersionPadronRS2018 v2018 = (VersionPadronRS2018) version;
				ps.setFloat( 15, v2018.getServiciosVarios().getMotores() );
				ps.setFloat( 16, v2018.getServiciosVarios().getCalderas() );
				break;
			case 2019: 
				VersionPadronRS2019 v2019 = (VersionPadronRS2019) version;
				ps.setFloat( 15, v2019.getServiciosVarios().getMotores() );
				ps.setFloat( 16, v2019.getServiciosVarios().getCalderas() );
				break;
			case 2020: 
				VersionPadronRS2020 v2020 = (VersionPadronRS2020) version;
				ps.setFloat( 15, v2020.getServiciosVarios().getMotores() );
				ps.setFloat( 16, v2020.getServiciosVarios().getCalderas() );
				break;
			default:
				break;
		}
	}
	
	private void cargarActividades(VersionPadronRS version, PreparedStatement ps) throws SQLException {
		Actividades a = this.recuperarActividades(version);
		ps.setInt( 21, a.getActividadPrincipal().getCodigo() );
	}
	
	private void registrarActividadesRecaudaciones(String numero, VersionPadronRS version) throws ExcepcionControladaError {
		
		if (version.getAno() <= 2020) {
			Actividades a = this.recuperarActividades( version );
			
			if (a.hayOtrasActividades()) {
				for ( ActividadComercial ac : a.getOtrasActividades() ) {
					this.eliminarActividadesSecundariasPadron( numero, version.getVersion(), version.getAno() );
					this.registrarActividadesSecundariasPadron( numero, version.getVersion(), version.getAno(), ac );
				}
			}
		}
	}

	private Actividades recuperarActividades(VersionPadronRS version) {
		switch (version.getAno()) {
			case 2018:	return ((VersionPadronRS2018) version).getActividades();
			case 2019:	return ((VersionPadronRS2019) version).getActividades();
			case 2020:	return ((VersionPadronRS2020) version).getActividades();
			default: 	return null;
		}
	}
	
	private void eliminarActividadesSecundariasPadron(String numero, Integer vez, Integer ano) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		String insert = "Delete recaudaciones:ddjj_sh_sub_rubros\n" 
					  + "Where c_cuenta = ?\n"
					  + "And n_orden = ? \n"
					  + "And n_ano = ? ";

		try {
			ps = this.prepareStatement( insert );
			ps.setInt( 1, Integer.parseInt(numero) );
			ps.setInt( 2, vez );
			ps.setInt( 3, ano );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void registrarActividadesSecundariasPadron(String numero, Integer vez, Integer ano, ActividadComercial ac) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		String insert = "insert into recaudaciones:ddjj_sh_sub_rubros\n" 
					  + "	(c_id_ddjj, n_ano, c_cuenta, sub_rubro, n_orden)\n" 
					  + "Values\n" 
					  + "	( 0 , ? , ? , ? , ? )";

		try {
			ps = this.prepareStatement( insert );
			ps.setInt( 1, ano );
			ps.setInt( 2, Integer.parseInt(numero) );
			ps.setInt( 3, ac.getCodigo() );
			ps.setInt( 4, vez );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public RSDeudaPadron generarDeudaPadron(Integer ano, Integer version, PadronRS padron, List<RSPeriodoDeuda> vencimientos) throws ExcepcionControladaError {

		RSDeudaPadron deuda = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			cs = this.prepareCall( SP_BUSCA_DEUDA_RS_PADRON );
			cs.setInt( 1, Integer.parseInt( padron.getNumero() ) );
			cs.setInt( 2, version );
			cs.setInt( 3, ano );
			cs.execute();

			rs = cs.getResultSet();

			if ( rs.next() ) {
				deuda = FactoryRSDeudaPadron.generar(rs.getFloat( 1 ), 
													 rs.getFloat( 2 ), 
													 rs.getFloat( 3 ), 
													 rs.getFloat( 4 ), 
													 rs.getFloat( 5 ), 
													 vencimientos,
													 padron.getNumero());
			} else
				throw new ExcepcionControladaError( "No se pudo obtener el detalle para el padrón", null );
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
		
		return deuda;
	}
	
	public boolean registrarAceptacion(String cuit, DDJJRS declaracion) throws ExcepcionControladaError {
		boolean adherido = this.enviarAceptacion( cuit, declaracion.getVersion(), declaracion.getAno() );
		
		if (adherido)
			declaracion.confirmada();
		else
			declaracion.rechazada();
		
		for (PadronRS p : declaracion.getListaPadrones()) {
			VersionPadronRS v = p.obtenerUltimaVersion();
			
			this.registrarResultadoAdhesion(declaracion.getAno(), p.getNumero(), declaracion.getEstado());
			
			if (adherido)
				this.registrarConfirmacionPadrones( v.getId() );
			else
				this.registrarRechazoPadrones( v.getId() );
		}
		
		return adherido;
	}

	private boolean enviarAceptacion(String cuit, Integer version, Integer ano) throws ExcepcionControladaError {

		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			cs = this.prepareCall( SP_CONFIRMA_PADRON );
			cs.setString( 1, cuit );
			cs.setInt( 2, version );
			cs.setInt( 3, ano );
			cs.execute();

			rs = cs.getResultSet();

			return rs.next() && rs.getInt( 1 ) == 1;
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
	}
	
	private void registrarResultadoAdhesion(Integer ano, String cuenta, EstadoDeclaracion estado) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_REGISTRA_ADHESION );
			ps.setString( 1, estado.name() );
			ps.setInt( 2, ano );
			ps.setString( 3, cuenta );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void registrarConfirmacionPadrones(Integer idVersion) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_CONFIRMA_PADRON );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void registrarRechazoPadrones(Integer idVersion) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_RECHAZA_PADRON );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public boolean puedeRectificar(String cuenta, Integer ano) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SP_PUEDE_RECTIFICAR );
			ps.setString( 1, cuenta );
			ps.setInt( 2, ano );
			rs = ps.executeQuery();
			
			return (rs.next() && rs.getInt( 1 ) == 1);
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
}