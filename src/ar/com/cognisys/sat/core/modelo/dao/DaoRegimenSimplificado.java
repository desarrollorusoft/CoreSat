package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.generics.assistant.timestamp.TimestampAssistant;
import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSDatos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJ;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.dao.rs.BonificacionRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryBonificacionRS;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSActividad;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSDatos;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRSSolicitante;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryCaracterSAT;

@Deprecated
public class DaoRegimenSimplificado extends DAO {

	private static final String UPDATE_RECHAZO_PADRONES = "Update recaudaciones:regimen_simplificado_padrones\n"
													    + "Set c_activado = ? \n"
													    + "Where c_cuenta = ? \n"
													    + "And n_ano = ? ";
	
	private static final String SQL_RECUPERACION_RS_DATOS = "select a.id_regimen_simplificado_cabecera, a.cuit, a.correo as correo_act, a.telefono as telefono_act, a.celular as celular_act, a.ano, a.flag_confirmado, a.fecha_confirmacion, a.flag_rechazado, \n"
			+ "	  s.nombre, s.apellido, s.correo as correo_sol, s.telefono as telefono_sol, s.celular as celular_sol,\n" + "	  s.id_caracter, c.clave, c.descripcion\n"
			+ "from web_recau:regimen_simplificado_cabecera a, web_recau:regimen_simplificado_solicitante s, web_recau:caracter c\n"
			+ "where a.id_regimen_simplificado_solicitante = s.id_regimen_simplificado_solicitante\n" + "and c.id_caracter = s.id_caracter\n" + "and a.flag_adherido = 0\n"
			+ "and a.cuit = ?";
	
	@Deprecated
	private static final String UPDATE_REGISTRA_RECHAZO = "Update web_recau:regimen_simplificado_cabecera\n" + "Set flag_rechazado = 1, fecha_confirmacion = current \n" + "Where cuit = ? \n"
			;

	/**
	 * regimen_simplificado_padrones ahora tiene n_ano, para el JOIN
	 */
	@Deprecated
	private static final String SQL_VALIDA_CUIT = "SELECT 1\n" + 
												  "FROM recaudaciones:comercios a, recaudaciones:regimen_simplificado_padrones b\n" + 
												  "WHERE a.n_cuit = ?\n"+ 
												  "AND a.c_baja = 0\n" + 
												  "AND a.c_cuenta = b.c_cuenta\n" + 
												  "AND b.c_activado = 1 \n" + 
												  "AND not exists (select 1 \n" + 
												  "			 FROM web_recau:regimen_simplificado_cabecera ca\n" + 
												  "			 WHERE cuit = a.n_cuit\n" + 
												  "			 AND (flag_confirmado = 1 or flag_rechazado = 1))";

	private static final String SQL_VALIDA_CUIT_REGISTRADO = "Select 1 from web_recau:regimen_simplificado_cabecera\n" + "Where cuit = ?\n" + "And flag_rechazado = 0\n" + "And flag_adherido = 0";

	
	

	private static final String SQL_RECUPERO_CUENTAS_ABL = "Select distinct rs.cuenta_abl as cuenta\n"
			+ "From web_recau:regimen_simplificado_cabecera ra, web_recau:regimen_simplificado rs, recaudaciones:comercios c\n" + "where ra.cuit = ?\n" + "and c.n_cuit = ra.cuit\n"
			+ "and rs.cuenta = c.c_cuenta\n" + "and cuenta_abl is not null ";
	/**
	 * Ahora tiene n_ano para el JOIN
	 */
	@Deprecated
	private static final String SQL_RECUPERAR_DECLARACION = "SELECT f_habilitacion as fecha_habilitacion, n_rubro as codigo_rubro,i_imponible as importe,n_personas as cantidad_personas,\n"
														  + "	  cuenta_abl as cuenta_abl,c_pyp1 as total_pyp1,c_pyp2 as total_pyp2,c_pyp3 as total_pyp3,c_pyp4 as total_pyp4,c_pyp5 as total_pyp5,\n"
														  + "	  c_oep1 as total_oep1,c_oep2 as total_oep2,c_sv1 as sv_1,c_sv2 as sv_2, n_ano as ano, n_orden as vez, fecha_alta\n" 
														  + "FROM recaudaciones:tmp_graba_datos_cab\n" 
														  + "WHERE c_cuenta = ?\n"
														  + "AND n_ano = 2018\n"
														  + "Order by n_orden asc";

	/**
	 * regimen_simplificado_padrones ahora tiene n_ano, para el JOIN
	 */
	@Deprecated
	private static final String UPDATE_RECTIFICA_PADRON = 
			"update recaudaciones:regimen_simplificado_padrones\n" + 
			"set c_activado = 1\n" + 
			"where c_cuenta in ( select c_cuenta\n" + 
			"from recaudaciones:comercios\n" + 
			"where n_cuit = ?\n" + 
			"and c_baja = 0 )\n";
	
	private static final String UPDATE_RECTIFICA_ADHESION = "Update web_recau:regimen_simplificado_cabecera\n" + "Set flag_confirmado = 0, flag_adherido = 0 \n" + "Where cuit = ? \n";

	private static final String SQL_RECUPERACION_RS_DATOS_ACTUAL = "select a.id_regimen_simplificado_cabecera, a.cuit, a.correo as correo_act, a.telefono as telefono_act, a.celular as celular_act, a.ano, a.flag_confirmado, a.fecha_confirmacion, a.flag_rechazado, \n"
																 + "	  s.nombre, s.apellido, s.correo as correo_sol, s.telefono as telefono_sol, s.celular as celular_sol,\n" 
																 + "	  s.id_caracter, c.clave, c.descripcion\n"
																 + "from web_recau:regimen_simplificado_cabecera a, web_recau:regimen_simplificado_solicitante s, web_recau:caracter c\n"
																 + "where a.id_regimen_simplificado_solicitante = s.id_regimen_simplificado_solicitante\n" 
																 + "and c.id_caracter = s.id_caracter\n" 
																 + "and a.cuit = ?";

	private static final String SP_RECUPERAR_BONIFICACION = "{call recaudaciones:sp_rs_bonificacion_cog( ? )}";
	
	public DaoRegimenSimplificado(Connection connection) {
		super( connection );
	}

	public void guardarDeclaracion(CuentaComercios cuenta) throws ExcepcionControladaError {

		Integer id = this.buscarDeclaracion( cuenta.getNumero() );

		if ( id == null )
			this.registrarDeclaracion( cuenta.getNumero(), cuenta.getDDJJEnCurso() );
		else
			this.actualizarDeclaracion( id, cuenta.getDDJJEnCurso() );
	}

	@Deprecated
	private Integer buscarDeclaracion(Integer cuenta) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select id_regimen_simplificado from web_recau:regimen_simplificado where cuenta = ?";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, cuenta );
			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt( "id_regimen_simplificado" );

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return null;
	}

	/**
	 * Este metodo puede fallar a causa de que no se le carge debidamente todos
	 * los datos, como pueden ser la ganancia(monto_origen) Esto ocurrira cuando
	 * aparezca una nueva cuenta asociada al CUIT
	 * 
	 * @param cuenta
	 * @param ddjj
	 * @throws ExcepcionControladaError
	 */
	@Deprecated
	private void registrarDeclaracion(Integer cuenta, DDJJ ddjj) throws ExcepcionControladaError {
		
		Integer id = this.buscarDeclaracion( cuenta );
		
		if (id == null) {
			if ( ddjj == null )
				this.registrarVacio( cuenta );
			else
				this.registrarDeclaracionCompleta( cuenta, ddjj );
		}
	}

	@Deprecated
	private void registrarDeclaracionCompleta(Integer cuenta, DDJJ ddjj) throws ExcepcionControladaError {
		this.registrarCompleto( cuenta, ddjj );

		Integer idRegimenSimplificado = this.buscarDeclaracion( cuenta );

		for ( DDJJCarteleria c : ddjj.getListaCarteleria() )
			this.registrarCarteleria( idRegimenSimplificado, c );

		for ( DDJJOEP c : ddjj.getOcupacion() )
			this.registrarOcupacion( idRegimenSimplificado, c );

		this.registrarActividades( idRegimenSimplificado, ddjj.getActividades() );
	}

	private void registrarCompleto(Integer cuenta, DDJJ ddjj) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;

		String sql = "Insert Into web_recau:regimen_simplificado(cuenta, monto_declarado, sv_motores, sv_calderas, cantidad, fecha_habilitacion, flag_confirmado, cuenta_abl, ano) \n"
				   + "Values ( ? , ? , ? , ? , ? , ? , ? )";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, cuenta );
			ps.setFloat( 2, ddjj.getGanancia() == null ? 0F : ddjj.getGanancia() );
			ps.setInt( 3, ddjj.getServiciosValios().getMotores() );
			ps.setInt( 4, ddjj.getServiciosValios().getCalderas() );
			ps.setInt( 5, ddjj.getCantidadPersonas() == null ? 0 : ddjj.getCantidadPersonas() );
			ps.setTimestamp( 6, TimestampAssistant.transform( ddjj.getFechaHabilitacion() ) );
			ps.setInt( 7, ddjj.isEnCurso() ? 0 : 1 );
			ps.setInt( 8, ddjj.getCuentaABL() );
			ps.setInt( 9, ddjj.getAno() );

			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void registrarVacio(Integer cuenta) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;

		String sql = "Insert Into web_recau:regimen_simplificado(cuenta, monto_declarado) \n" 
				   + "Values ( ? , ? )";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, cuenta );
			ps.setFloat( 2, 0f );

			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void actualizarDeclaracion(Integer id, DDJJ ddjj) throws ExcepcionControladaError {

		this.actualizarDatosRS( id, ddjj );
		this.limpiarCateleria( id );
		this.limpiarOcupacion( id );
		this.limpiarActividades( id );

		for ( DDJJCarteleria c : ddjj.getListaCarteleria() )
			this.registrarCarteleria( id, c );

		for ( DDJJOEP c : ddjj.getOcupacion() )
			this.registrarOcupacion( id, c );

		this.registrarActividades( id, ddjj.getActividades() );
	}

	private void actualizarDatosRS(Integer id, DDJJ ddjj) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Update web_recau:regimen_simplificado\n"
				   + "Set monto_declarado = ?, sv_motores = ?, sv_calderas = ?, cantidad = ?, fecha_habilitacion = ?, flag_confirmado = ?, cuenta_abl = ?, ano = 2018 \n"
				   + "Where id_regimen_simplificado = ?\n";
		try {
			ps = this.prepareStatement( sql );
			ps.setFloat( 1, ddjj.getGanancia() == null ? 0F : ddjj.getGanancia() );
			ps.setInt( 2, ddjj.getServiciosValios().getMotores() );
			ps.setInt( 3, ddjj.getServiciosValios().getCalderas() );
			ps.setInt( 4, ddjj.getCantidadPersonas() == null ? 0 : ddjj.getCantidadPersonas() );
			ps.setTimestamp( 5, TimestampAssistant.transform( ddjj.getFechaHabilitacion() == null ? new Date() : ddjj.getFechaHabilitacion() ) );
			ps.setInt( 6, ddjj.isEnCurso() ? 0 : 1 );
			ps.setInt( 7, ddjj.getCuentaABL() );
			ps.setInt( 8, id );
			ps.executeUpdate();

			if ( !ddjj.isEnCurso() )
				this.guardarFechaConfirmacionPadron( id );
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void guardarFechaConfirmacionPadron(Integer id) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Update web_recau:regimen_simplificado\n" 
				   + "Set fecha_confirmacion = ? \n" 
				   + "Where id_regimen_simplificado = ?\n";

		try {
			ps = this.prepareStatement( sql );
			ps.setTimestamp( 1, TimestampAssistant.transform( new Date() ) );
			ps.setInt( 2, id );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void limpiarCateleria(Integer id) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Delete From web_recau:regimen_simplificado_carteleria where id_regimen_simplificado = ? ";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, id );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void limpiarOcupacion(Integer id) throws ErrorActualizandoRegistracionException {

		PreparedStatement ps = null;

		String sql = "Delete From web_recau:regimen_simplificado_oep where id_regimen_simplificado = ? ";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, id );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void limpiarActividades(Integer id) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Delete From web_recau:regimen_simplificado_actividades where id_regimen_simplificado = ? ";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, id );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void registrarCarteleria(Integer id, DDJJCarteleria c) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Insert Into web_recau:regimen_simplificado_carteleria(id_regimen_simplificado, codigo_pyp, metros, descripcion) " 
				   + "Values ( ? , ? , ? , ? )";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, id );
			ps.setInt( 2, c.getTipo().getCodigo() );
			ps.setFloat( 3, c.getMetros() );
			ps.setString( 4, c.getDescripcion() );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void registrarOcupacion(Integer idRegimenSimplificado, DDJJOEP c) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;

		String sql = "Insert Into web_recau:regimen_simplificado_oep(id_regimen_simplificado, tipo, valor) " 
				   + "Values ( ? , ? , ? )";

		try {
			ps = this.prepareStatement( sql );

			ps.setInt( 1, idRegimenSimplificado );
			ps.setString( 2, c.getTipo().name() );
			ps.setFloat( 3, c.getValor() );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	private void registrarActividades(Integer id, Actividades actividades) throws ExcepcionControladaError {

		this.registrarActividad( id, actividades.getActividadPrincipal(), true );

		for ( ActividadComercial ac : actividades.getOtrasActividades() )
			this.registrarActividad( id, ac, false );
	}

	@Deprecated
	private void registrarActividad(Integer idRegimenSimplificado, ActividadComercial actividad, boolean principal) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String sql = "Insert Into web_recau:regimen_simplificado_actividades(id_regimen_simplificado, codigo_recaudaciones, flag_principal) " 
				   + "Values ( ? , ? , ? )";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, idRegimenSimplificado );
			ps.setInt( 2, actividad == null || actividad.getCodigo() == null ? 0 : actividad.getCodigo() );
			ps.setInt( 3, principal ? 1 : 0 );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	public void registrarRechazo(String cuit) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_REGISTRA_RECHAZO );
			ps.setString( 1, cuit );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	@Deprecated
	public RSDatos recuperarRsDatos(String cuit) throws ExcepcionControladaError {

		RSDatos rsDatos = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERACION_RS_DATOS );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			if ( rs.next() )
				rsDatos = FactoryRSDatos.generar( rs.getLong( "id_regimen_simplificado_cabecera" ),
						FactoryRSActividad.generar( cuit, rs.getString( "correo_act" ), rs.getString( "clave" ), rs.getString( "telefono_act" ), rs.getString( "celular_act" ) ),
						FactoryRSSolicitante.generar( rs.getString( "nombre" ), rs.getString( "apellido" ),
								FactoryCaracterSAT.generar( rs.getInt( "id_caracter" ), rs.getString( "clave" ), rs.getString( "descripcion" ) ), rs.getString( "correo_sol" ),
								rs.getString( "telefono_sol" ), rs.getString( "celular_sol" ) ),
						rs.getInt( "flag_rechazado" ) == 1 );
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return rsDatos;
	}
	
	@Deprecated
	public RSDatos recuperarRsDatosActual(String cuit) throws ExcepcionControladaError {

		RSDatos rsDatos = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERACION_RS_DATOS_ACTUAL );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			if ( rs.next() )
				rsDatos = FactoryRSDatos.generar( rs.getLong( "id_regimen_simplificado_cabecera" ),
						FactoryRSActividad.generar( cuit, rs.getString( "correo_act" ), rs.getString( "clave" ), rs.getString( "telefono_act" ), rs.getString( "celular_act" ) ),
						FactoryRSSolicitante.generar( rs.getString( "nombre" ), rs.getString( "apellido" ),
								FactoryCaracterSAT.generar( rs.getInt( "id_caracter" ), rs.getString( "clave" ), rs.getString( "descripcion" ) ), rs.getString( "correo_sol" ),
								rs.getString( "telefono_sol" ), rs.getString( "celular_sol" ) ),
						rs.getInt( "flag_rechazado" ) == 1 );
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return rsDatos;
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

	public boolean existeCuitRegistrado(String cuit) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_VALIDA_CUIT_REGISTRADO );
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

	public List<String> recueprarCuentasABL(String cuit) throws ExcepcionControladaError {

		List<String> lista = new ArrayList<String>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERO_CUENTAS_ABL );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			while (rs.next())
				lista.add( rs.getString( "cuenta" ) );

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return lista;
	}

	@Deprecated
	public void rectificarRegimen(String cuit) throws ErrorRecuperacionDatosException {

		rectificarCabecera( cuit );
//		rectificarRegimenSimplificado( cuit );
		rectificarPadron( cuit );
	}

	private void rectificarPadron(String cuit) throws ErrorRecuperacionDatosException {

	
		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( UPDATE_RECTIFICA_PADRON );
			ps.setString( 1, cuit );
			ps.executeUpdate();
		} catch ( SQLException e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
		
	}

	private void rectificarCabecera(String cuit) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_RECTIFICA_ADHESION );
			ps.setString( 1, cuit );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public BonificacionRS recuperarBonificacion(Integer padron) throws ExcepcionControladaError {
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.prepareCall( SP_RECUPERAR_BONIFICACION );
			cs.setInt( 1, padron );
			cs.execute();

			rs = cs.getResultSet();

			if (rs.next()) {
				if (rs.getString(1).equals("S"))
					return FactoryBonificacionRS.generar(rs.getFloat(3),
														 rs.getFloat(5));
				else
					return FactoryBonificacionRS.generar();
			} else
				return null;
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
	}
}