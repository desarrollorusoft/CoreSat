package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.UsuarioInexistenteException;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryUsuario;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryDomicilio;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPermisoUsuario;

public class DaoUsuario extends DAO {

	public DaoUsuario(Connection connection) {
		super( connection );
	}

	/**
	 * Este metodo permite recuperar el Usuario, a partir de los datos de Ingreso
	 * @param cuit
	 * @param clave
	 * @return Usuario
	 * @throws ExcepcionControladaError
	 */
	public Usuario recuperar(String cuit, String clave) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( Queries.RECUPERAR_USUARIO.getSql() );
			ps.setString( 1, cuit );
			ps.setString( 2, clave );

			rs = ps.executeQuery();

			if (rs.next())
				return FactoryUsuario.generar(rs.getInt("Id_Usuario"), 
												 rs.getString("cuit"), 
												 rs.getString("correo"), 
												 rs.getString("telefono"), 
												 rs.getString("fax"), 
												 rs.getInt("flag_newsletter") == 1, 
												 rs.getInt("flag_activacion") == 1, 
												 rs.getDate("fecha"));
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return null;
	}
	
	public Usuario buscarYRecuperarDatosUsuario(Integer idUsuario, CuentasUsuario cuentas) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "Select Id_Usuario, cuit, correo, clave, telefono, fax, flag_activacion, flag_newsletter \n "
					 + "From web_recau:Usuario_Cogmvl \n "
					 + "Where Id_Usuario = ? \n ";

		try {
			ps = this.prepareStatement(query);
			ps.setInt(1, idUsuario);
			
			rs = ps.executeQuery();

			if (rs.next())
				return FactoryUsuario.generarInstanciaCompleta(idUsuario,
																  rs.getString( "cuit" ),
																  rs.getString("clave"),
																  cuentas,
																  rs.getString("correo"),
																  rs.getString("telefono"),
																  rs.getString("fax"),
																  rs.getInt("flag_newsletter") == 1);
			
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}
	
	@Deprecated
	public void cargarDatosCuenta(CuentaVehiculos cuenta) throws ErrorEnBaseException {

		this.cargarSPDatosAutoMoto( cuenta );

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = this.prepareStatement( "Select * from tmp_pad_vehiculos" );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				cuenta.setCatVehiculo( rs.getString( "c_cat_veh" ) );
				cuenta.setCodigoMarca( rs.getString( "c_cod_marca" ) );
				cuenta.setMarca( rs.getString( "d_marca" ) );
				cuenta.setModelo( rs.getString( "n_modelo" ) );
				cuenta.setModeloMarca( rs.getString( "d_modelo" ) );
				cuenta.setTipoVehiculo( rs.getString( "c_cod_marca" ) );
				cuenta.setValuacion( rs.getString( "n_valuacion" ) );
			}
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( "Error al recuperar los datos del vehículo", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	private void cargarSPDatosAutoMoto(CuentaAutomotor cuenta) throws ErrorEnBaseException {
		CallableStatement cs = null;

		try {
			cs = this.prepareCall( QueriesInformix.SPP_RECUPERAR_DATOS_AUTOMOTOR.getQuery() );
			cs.setInt( 1, cuenta.getSistema() );
			cs.setString( 2, cuenta.getDominio() );
			cs.execute();
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( "Error al recuperar los datos del vehículo", e );
		} finally {
			super.cerrarRecursoST( cs );
		}
	}

	@Deprecated
	public void cargarDatosCuenta(CuentaRodados cuenta) throws ErrorEnBaseException {
		

		this.cargarSPDatosAutoMoto( cuenta );

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = this.prepareStatement( "Select * from tmp_pad_rodados" );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				cuenta.setCilindrada( rs.getString( "n_cilindrada" ) );
				cuenta.setMarca( rs.getString( "d_marca" ) );
				cuenta.setModelo( rs.getString( "n_modelo" ) );
			}
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( "Error al recuperar los datos del rodado", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	@Deprecated
	public void cargarDatosContribuyenteAbl(CuentaABL cuenta) throws ExcepcionControladaError {
		
		this.cargarSPDatosABL( cuenta );
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			ps = this.prepareStatement( QueriesInformix.RECUPERAR_DATOS_ABL.getQuery() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				Contribuyente contribuyente = FactoryContribuyente.generarInstanciaCompleta(rs.getString("d_nombre"), 
																			  rs.getString("d_apellido"), 
																			  rs.getInt("n_documento"), 
																			  "", 
																			  FactoryDomicilio.generarInstanciaCompleta(rs.getString("d_calle_pro"), 
																					  									rs.getInt("n_nro_pro"), 
																					  									(rs.getString("c_piso_pro") == null ? "" : rs.getString("c_piso_pro")),
																					  									rs.getString("c_dpto_pro"), 
																					  									rs.getInt("c_postal_pro")), 
										  									  rs.getString("d_telefono"), 
																			  "", 
																			  0);
				cuenta.setDescripcion( contribuyente.getDomicilio().toString() );
				cuenta.setContribuyente( contribuyente );
				cuenta.setBaja( "1".equals( rs.getString( "c_baja" ) ) );
				cuenta.setCategoria( rs.getString( "n_cate" ) );
				cuenta.setEximido( "1".equals( rs.getString( "c_eximido" ) ) );
				cuenta.setFrente( rs.getString( "n_frente" ) );
				cuenta.setNumeroCirculacion( rs.getString( "n_circ" ) );
				cuenta.setOficio( rs.getString( "n_oficio" ) );
				cuenta.setPartida( rs.getString( "n_partida" ) );
				cuenta.setSeccion( rs.getString( "c_seccion" ) );
				cuenta.setSuperficie( rs.getString( "n_superf" ) );
				cuenta.setValuacion( rs.getString( "n_valua" ) );
				cuenta.setVerificador( rs.getString( "n_verificador" ) );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	private void cargarSPDatosABL(CuentaABL cuenta) throws ErrorRecuperacionDatosException {

		CallableStatement cs = null;

		try {

			cs = this.prepareCall( QueriesInformix.GENERAR_DATOS_ABL.getQuery() );
			cs.setInt( 1, cuenta.getNumero() );
			cs.execute();
		} catch ( SQLException e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoST( cs );
		}

	}

	public Integer registarUsuario(String cuit, String correo, String clave, CuentasUsuario cuentas) throws ExcepcionControladaError {

		this.registrarDatosUsuario( cuit, clave, correo, "", "" );

		Integer idUsuario = this.recuperarIdUsuario( cuit, clave );

		for ( Cuenta abl : cuentas.obtenerCuentas(TiposCuentas.ABL) )
			this.registrarCuentasAsociadas( idUsuario, abl.getTipoCuenta().getCodigo_usuario(), abl.getNumero().toString(), abl.getAlias() );

		for ( Cuenta vehiculo : cuentas.obtenerCuentas(TiposCuentas.VEHICULOS) )
			this.registrarCuentasAsociadas( idUsuario, vehiculo.getTipoCuenta().getCodigo_usuario(), ((CuentaVehiculos) vehiculo).getDominio(), vehiculo.getAlias() );

		for ( Cuenta rodado : cuentas.obtenerCuentas(TiposCuentas.RODADOS) )
			this.registrarCuentasAsociadas( idUsuario, rodado.getTipoCuenta().getCodigo_usuario(), ((CuentaRodados) rodado).getDominio(), rodado.getAlias() );

		for ( Cuenta cementerio : cuentas.obtenerCuentas(TiposCuentas.CEMENTERIO) )
			this.registrarCuentasAsociadas( idUsuario, cementerio.getTipoCuenta().getCodigo_usuario(), cementerio.getNumero().toString(), cementerio.getAlias() );

		for ( Cuenta piletas : cuentas.obtenerCuentas(TiposCuentas.PILETAS) )
			this.registrarCuentaAsociadaPileta( idUsuario, ((CuentaPileta) piletas).getTipoDocumento().getNombrePiletas(), ((CuentaPileta) piletas).getNumeroDocumento().toString(), piletas.getAlias());

		return idUsuario;
	}
	
	private void registrarDatosUsuario(String cuit, String clave, String correo, String telefono1, String telefono2) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;

		String insert = "Insert Into web_recau:usuario_cogmvl \n "
					  + "	(nombre_usuario, clave, correo, telefono, fax, cuit, flag_tyc, flag_newsletter) \n "
					  + "Values \n "
					  + "	( 'registro_'||current , ? , ? , ? , ? , ? , 1 , 1) ";

		try {
			ps = this.prepareStatement( insert );
			ps.setString( 1, clave );
			ps.setString( 2, correo == null ? "" : correo );
			ps.setString( 3, telefono1 == null ? "" : telefono1 );
			ps.setString( 4, telefono2 == null ? "" : telefono2 );
			ps.setString( 5, cuit );

			ps.executeUpdate();
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private Integer recuperarIdUsuario(String cuit, String clave) throws ExcepcionControladaError {
		
		Integer idUsuario = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select Id_Usuario  \n "
					 + "From web_recau:usuario_cogmvl \n "
					 + "Where clave = ? \n"
					 + "And cuit = ? ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, clave );
			ps.setString( 2, cuit );

			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt( "Id_Usuario" );

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return idUsuario;
	}
	
	public Integer recuperarIdUsuarioPorCorreo(String correo, String clave) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select Id_Usuario  \n "
					 + "From web_recau:usuario_cogmvl \n "
					 + "Where correo = ? \n"
					 + "And cuit = ? ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, clave );
			ps.setString( 2, correo );

			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt( "Id_Usuario" );
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return null;
	}
	
	public Integer recuperarIdUsuario(String cuit) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select Id_Usuario  \n "
					 + "From web_recau:usuario_cogmvl \n "
					 + "Where cuit = ? ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt( "Id_Usuario" );
			
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return null;
	}

	public void registrarCuentaAsociadaPileta(Integer idUsuario, String tipoDoc, String nroDoc, String alias) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String insert = "INSERT INTO web_recau:USUARIO_CUENTAS_PILETAS_COGMVL (id_usuario, tipo_documento, nro_documento, descripcion) VALUES (?, ?, ?, ?)";

		try {
			ps = this.prepareStatement( insert );
			ps.setInt( 1, idUsuario );
			ps.setString( 2, tipoDoc );
			ps.setString( 3, nroDoc );
			ps.setString( 4, alias );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void registrarCuentasAsociadas(Integer idUsuario, Integer tipoCuenta, String cuenta, String alias) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;

		String insert = "Insert Into web_recau:usuario_cuentas_rel_cogmvl \n "
					  + "	(id_usuario, tipo_cuenta, cuenta, descripcion) \n "
					  + "Values \n "
					  + "	( ? , ? , ? , ? ) ";

		try {
			ps = this.prepareStatement( insert );
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, tipoCuenta );
			ps.setString( 3, cuenta );
			ps.setString( 4, alias );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}	
	}
	

	public boolean existeCuentaAsociada(Integer idUsuario, String cuenta) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select distinct 1 from web_recau:usuario_cuentas_rel_cogmvl \n "
					  + "where id_usuario = ? \n"
					  + "and cuenta = ? \n";

		try {
			ps = this.prepareStatement( query );
			ps.setInt(1, idUsuario);
			ps.setString(2, cuenta);

			rs = ps.executeQuery();

			return rs.next();

		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}


	public void actualizarClave(String cuit, String claveNueva) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String update = "Update web_recau:usuario_cogmvl \n " 
					  + "Set clave = ? \n "
					  + "Where cuit = ? ";

		try {
			ps = this.prepareStatement( update );
			ps.setString( 1, claveNueva );
			ps.setString( 2, cuit );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void actualizarClave(Integer idUsuario, String claveNueva) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;

		String update = "Update web_recau:usuario_cogmvl \n " 
					  + "Set clave = ? \n "
					  + "Where id_usuario = ? \n";

		try {
			ps = this.prepareStatement( update );
			ps.setString( 1, claveNueva );
			ps.setInt( 2, idUsuario );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void actualizarDatosUsuario(Integer idUsuario, String correo, String telefono1, String telefono2, String nombre, 
									  String apellido, Integer nivel) throws ExcepcionControladaError {
		PreparedStatement ps = null;

		String update = "Update Web_recau:usuario_cogmvl \n "
					  + "Set nombre_usuario = 'actualiza_perfil_'||current, correo = ?, telefono = ?, fax = ?, nombre = ?, apellido = ?, nivel = ? \n "
					  + "Where id_usuario = ? ";

		try {
			ps = this.prepareStatement( update );
			ps.setString( 1, correo );
			ps.setString( 2, telefono1.trim() );
			ps.setString( 3, telefono2.trim() );
			ps.setString( 4, nombre );
			ps.setString( 5, apellido );
			ps.setInt( 6, nivel );
			ps.setInt( 7, idUsuario );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void actualizarDatosUsuario(Integer idUsuario, String correo, String telefono1, String telefono2, String nombre, String apellido) throws ExcepcionControladaError {
		PreparedStatement ps = null;

		String update = "Update Web_recau:usuario_cogmvl \n "
					  + "Set nombre_usuario = 'actualiza_perfil_'||current, correo = ?, telefono = ?, fax = ?, nombre = ?, apellido = ? \n "
					  + "Where id_usuario = ? ";

		try {
			ps = this.prepareStatement( update );
			ps.setString( 1, correo );
			ps.setString( 2, telefono1.trim() );
			ps.setString( 3, telefono2.trim() );
			ps.setString( 4, nombre );
			ps.setString( 5, apellido );
			ps.setInt( 6, idUsuario );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void eliminarCuentaRelacionada(Integer idUsuario, Integer tipoCuenta, String cuenta) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String update = "Delete Web_recau:usuario_cuentas_rel_cogmvl \n"
				+ "Where id_usuario = ? \n"
				+ "And cuenta = ? \n"
				+ "And tipo_cuenta = ? ";

		try {
			ps = this.prepareStatement( update );
			ps.setInt( 1, idUsuario );
			ps.setString( 2, cuenta );
			ps.setInt( 3, tipoCuenta );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void eliminarCuentaRelacionadaPileta(Integer idUsuario, String tipoDoc, String nroDoc) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		String update = "DELETE FROM web_recau:USUARIO_CUENTAS_PILETAS_COGMVL WHERE id_usuario = ? and tipo_documento = ? and nro_documento = ?";

		try {
			ps = this.prepareStatement( update );
			ps.setInt( 1, idUsuario );
			ps.setString( 2, tipoDoc );
			ps.setString( 3, nroDoc );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public boolean existeUsuarioCuit(String cuit) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select 1 \n " 
					 + "From web_recau:usuario_cogmvl \n "
					 + "Where cuit = ? \n"
					 + "And flag_baja = 0 ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, cuit );
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	public boolean existeUsuarioCorreo(String correo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select 1 \n " 
					 + "From web_recau:usuario_cogmvl \n "
					 + "Where correo = ? \n"
					 + "And flag_baja = 0 ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, correo );
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public void activarUsuario(Integer idUsuario) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		
		String query = "Update web_recau:usuario_cogmvl \n " 
					 + "Set flag_activacion = 1 \n "
					 + "Where id_usuario = ? ";

		try {
			ps = this.prepareStatement(query);
			ps.setInt( 1, idUsuario );

			ps.executeUpdate();

		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public boolean estaActivo(String correo) throws ExcepcionControladaAlerta, ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select a.flag_activacion \n"
					 + "From Web_Recau:Usuario_Cogmvl u, Web_Recau:Usuario_Activacion_Cogmvl a \n"
					 + "Where u.correo = ? \n"
					 + "And a.id_usuario = u.id_usuario ";

		try {
			ps = this.prepareStatement(query);
			ps.setString(1, correo);
			
			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt("flag_activacion") != 0;
			
			throw new UsuarioInexistenteException();
			
		} catch (UsuarioInexistenteException e) {
			throw e;
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public boolean validarDatosOlvidoDatos(String correo, String tipoCuenta, String cuentaDominio) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			if ( tipoCuenta.equals( TiposCuentas.ABL.getNombre() ) ) {
				
				ps = this.prepareStatement(QueriesInformix.RECUPERACION_CUENTA_ABL.getQuery());
				ps.setString(1, correo);
				ps.setInt(2, TiposCuentas.recuperarPosicionCodigoUsuario(tipoCuenta));
				ps.setString(3, cuentaDominio);
				
			} else if ( tipoCuenta.equals( TiposCuentas.VEHICULOS.getNombre() ) ) {
				
				ps = this.prepareStatement(QueriesInformix.RECUPERACION_TIPO_VEHICULO.getQuery());
				ps.setString(1, correo);
				ps.setString(2, cuentaDominio);
				ps.setString(3, cuentaDominio);
				ps.setString(4, cuentaDominio);
				ps.setString(5, correo);
				ps.setString(6, cuentaDominio);
				ps.setString(7, cuentaDominio);
			} else {
				return false;
			}
			
			rs = ps.executeQuery();

			return rs.next();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
	}

	public boolean esCorreoValido(String correo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select 1 \n "
					 + "From Web_Recau:Usuario_Cogmvl U \n "
					 + "Where U.Correo = ? ";

		try {
			ps = this.prepareStatement( query );
			ps.setString( 1, correo );

			rs = ps.executeQuery();

			return !rs.next();
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	public List<Usuario> recuperarListaUsuarios(String datoCuenta, TiposCuentas tipoCuenta) throws ExcepcionControladaError {
		
		List<Usuario> lista = new ArrayList<Usuario>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( Queries.GET_USUARIOS_X_CUENTA.getSql() );
			ps.setString( 1, datoCuenta );
			ps.setInt( 2, TiposCuentas.recuperarPosicionCodigoUsuario( tipoCuenta.getNombre() ) );
			rs = ps.executeQuery();
			
			while (rs.next()) {
				lista.add( this.generarUsuario(rs) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return lista;
	}
	
	public Usuario recuperarUsuario(Integer idUsuario) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = this.prepareStatement( Queries.BUSCAR_USUARIO.getSql() );
			ps.setInt( 1, idUsuario );

			rs = ps.executeQuery();

			if ( rs.next() )
				return this.generarUsuario(rs);
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudo recuperar los datos de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}
	
	/**
	 * No usar este metodo, usar el metodo "recuperar(cuit)"
	 * @param correo
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Usuario recuperarUsuario(String correo) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = this.prepareStatement( Queries.BUSCAR_USUARIO_CORREO.getSql() );
			ps.setString( 1, correo );

			rs = ps.executeQuery();

			if ( rs.next() )
				return this.generarUsuario(rs);
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudo recuperar los datos de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}
	
	public Usuario recuperar(String cuit) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			ps = this.prepareStatement( Queries.BUSCAR_USUARIO_CUIT.getSql() );
			ps.setString( 1, cuit );

			rs = ps.executeQuery();

			if ( rs.next() )
				return this.generarUsuario(rs);
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudieron recuperar los datos de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}
	
	public void cargarPermisos(Usuario u) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( Queries.BUSCAR_PERMISOS_USUARIO.getSql() );
			ps.setInt( 1, u.getIdUsuario() );
			
			rs = ps.executeQuery();

			while ( rs.next() )
				u.getListaPermisos().add( FactoryPermisoUsuario.generarInstancia( rs.getString("clave"), 
																				  rs.getString("cuenta"), 
																				  rs.getInt("tipo_cuenta")) );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se puedieron recuperar los permisos", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public List<Usuario> recuperarTodosUsuarios(Queries query, Integer comienzo, Integer cantidad, String filtro, Integer activo) throws ExcepcionControladaError {
		
		List<Usuario> lista = new ArrayList<Usuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			if (filtro == null) {
				String aux = Queries.BUSCAR_LISTA_USUARIOS_COMPLETO.getSql();
				if(activo != -1)
					aux += " AND U.Flag_Activacion = ? \n";
				ps = this.prepareStatement( aux );
			} else {
				String aux = query.getSql();
				if(activo != -1)
					aux += " AND U.Flag_Activacion = ? \n";

				ps = this.prepareStatement( aux );

				ps.setString(3, "%" + filtro + "%");
			}

			ps.setInt(1, comienzo);
			ps.setInt(2, cantidad);

			if(activo != -1)
				ps.setInt(4, activo);

			rs = ps.executeQuery();

			while ( rs.next() )
				lista.add( this.generarUsuario(rs) );
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudo recuperar los datos de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return lista;
	}

	public Integer buscarCantidadUsuarios(Queries query, String filtro, Integer activo) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if ( filtro == null ) {
				String aux =  Queries.USUARIOS_CANTIDAD.getSql();
				if(activo!=-1)
					aux += " And U.Flag_Activacion = ? ";
				ps = this.prepareStatement( aux );
			} else {
				String aux =  query.getSql();
				if(activo!=-1)
					aux += " AND U.Flag_Activacion = ? ";
				ps = this.prepareStatement( aux );
				if(activo!=-1)
					ps.setInt(2, activo );
				ps.setString( 1, "%" + filtro + "%" );
			}
			
			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getInt( 1 );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudo recuperar los datos de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;
	}
	
	public void borrarUsuario(Integer idUsuario) throws ExcepcionControladaError {

		this.borrarDatosUsuario( idUsuario );
//		this.borrarCuentasUsuario( idUsuario );
	}
	
	public void borrarDatosUsuario(Integer idUsuario) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( Queries.ELIMINAR_USUARIO.getSql() );
			ps.setInt( 1, idUsuario );
			ps.executeUpdate();

		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No se pudo eliminar el usuario", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void borrarCuentasUsuario(Integer idUsuario) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( Queries.ELIMINAR_CUENTAS_USUARIO.getSql() );
			ps.setInt( 1, idUsuario );
			ps.executeUpdate();

		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No se pudo eliminar el usuario", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public Map<TiposCuentas, Integer> recuperarCantidadesCuentas(Integer idUsuario) throws ExcepcionControladaError {
		
		Map<TiposCuentas, Integer> tabla = new EnumMap<TiposCuentas, Integer>( TiposCuentas.class );
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( QueriesInformix.RECUPERAR_CANTIDADES_CUENTAS.getQuery() );
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			
			rs = ps.executeQuery();

			while ( rs.next() ) {
				Integer cantidad = rs.getInt( "Cantidad" );
				switch ( rs.getInt( "Tipo_Cuenta" ) ) {
					case 1:	tabla.put( TiposCuentas.ABL, cantidad ); break;
					case 2:	tabla.put( TiposCuentas.COMERCIOS, cantidad ); break;
					case 3:	tabla.put( TiposCuentas.VEHICULOS, cantidad ); break;
					case 4:	tabla.put( TiposCuentas.RODADOS, cantidad ); break;
					case 5:	tabla.put( TiposCuentas.CEMENTERIO, cantidad ); break;
					case 6:	tabla.put( TiposCuentas.PILETAS, cantidad ); break;
					default:break;
				}
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudieron recuperarar las cuentas de usuario", e);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return tabla;
	}

	public void generarCodigoActivacion(Integer idUsuario, String codigo) throws ExcepcionControladaError {

		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( QueriesInformix.GUARDAR_CODIGO_ACTIVACION.getQuery() );
			ps.setInt( 1, idUsuario );
			ps.setString( 2, codigo );

			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo generar el código de activación", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public String recuperarCodigoActivacion(Integer idUsuario) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( QueriesInformix.BUSCAR_CODIGO_ACTIVACION.getQuery() );
			ps.setInt( 1, idUsuario );

			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getString( "codigo" );

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo recuperar el código de activación", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return null;

	}

	public Integer registrarUsuarioRS(String cuit, String clave, String correo, String telefono, String celular, String alias) throws ExcepcionControladaError {

		this.registrarDatosUsuario( cuit, clave, correo, telefono, celular );
		Integer idUsuario = this.recuperarIdUsuario( cuit, clave );
		this.registrarCuentasAsociadas( idUsuario, TiposCuentas.COMERCIOS.getCodigo_usuario(), cuit, alias );
		this.activarUsuario( idUsuario );

		return idUsuario;
	}

	public boolean tieneCuenta(Integer idUsuario, TiposCuentas tipoCuenta, String cuentaDominio) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select * \n"
					+ "from Web_Recau:Usuario_Cuentas_Rel_Cogmvl C \n"
					+ "where id_usuario = ? \n"
					+ "and tipo_cuenta = ? \n"
					+ "and cuenta = ? ";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, tipoCuenta.getCodigo_usuario() );
			ps.setString( 3, cuentaDominio );

			rs = ps.executeQuery();

			return rs.next();

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo validar la cuenta", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public boolean tieneCuenta(Integer idUsuario, String tipoDoc, String nroDoc) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT id_usuario FROM web_recau:USUARIO_CUENTAS_PILETAS_COGMVL WHERE id_usuario = (?) and tipo_documento = (?) and nro_documento = (?)";

		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, idUsuario );
			ps.setString( 2, tipoDoc );
			ps.setString( 3, nroDoc );

			rs = ps.executeQuery();

			return rs.next();

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo validar la cuenta", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	public Integer registrarCF(UsuarioCF usuario) throws ExcepcionControladaError {
		return this.registrarUsuarioRS(usuario.getCuit(), 
									   usuario.getClave(), 
									   usuario.getCorreo(), 
									   usuario.getTelefono(), 
									   "", 
									   usuario.getRazonSocial());
	}

	public void aceptaTyC(Usuario usuario) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		String sql = "Update web_recau:usuario_cogmvl \n"
				   + "Set flag_tyc = 1\n"
				   + "where id_usuario = ? ";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, usuario.getIdUsuario() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo validar la cuenta", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private Usuario generarUsuario(ResultSet rs) throws SQLException {
		return FactoryUsuario.generarInstanciaCompleta(rs.getInt("Id_Usuario"),
													   rs.getString("Nombre_Usuario"), 
													   rs.getString("Clave"),
													   rs.getString("Cuit"), 
													   rs.getString("nombre"), 
													   rs.getString("apellido"), 
													   rs.getString("Correo"), 
													   rs.getString("Telefono1"), 
													   rs.getString("Telefono2"), 
													   (rs.getInt("Flag_Newsletter") == 1),
													   (rs.getInt("Flag_Activacion") == 1),
													   rs.getDate("Fecha_Alta"),
													   (rs.getInt("flag_tyc") == 1),
													   rs.getInt("nivel"));
	}

	public void validarRSAsociado(Integer idUsuario, String cuit) throws ExcepcionControladaError {
		if (!this.tieneRSAsociado(cuit))
			this.registrarCuentasAsociadas(idUsuario, TiposCuentas.COMERCIOS.getCodigo_usuario(), cuit, "");
	}

	private boolean tieneRSAsociado(String cuit) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select 1 \n"
					+ "from web_recau:usuario_cogmvl u, web_recau:usuario_cuentas_rel_cogmvl r \n"
					+ "where cuit = ? \n"
					+ "and u.id_usuario = r.id_usuario \n"
					+ "and r.cuenta = ? ";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setString( 1, cuit );
			ps.setString( 2, cuit );
			
			rs = ps.executeQuery();

			return (rs.next());
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudo recuperar la cuenta de RS", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void actualizarCorreo(Integer idUsuario, String correo) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		String sql = "Update web_recau:usuario_cogmvl \n"
				   + "Set correo = ? \n"
				   + "where id_usuario = ? ";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setString( 1, correo );
			ps.setInt( 2, idUsuario );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se cargar su correo", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public void registroCargaNivel3(String nombre, String apellido, String cuit, String tipoDocumento,
									String nroDocumento, String correo, String telefono, String celular) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		String sql = "Insert Into dfe (n_cuit, c_documento, n_documento, d_nombre, d_apellido, d_telefono, d_celular, d_dfe) \n"
				   + "Values ( ? , ? , ? , ? , ? , ? , ? , ? )";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setString( 1, cuit );
			ps.setInt( 2, TiposDocumento.getTipoDocumentoPorNombrePiletas(tipoDocumento).getCodigo());
			ps.setInt( 3, Integer.parseInt(nroDocumento) );
			ps.setString( 4, nombre );
			ps.setString( 5, apellido );
			ps.setString( 6, telefono );
			ps.setString( 7, celular );
			ps.setString( 8, correo );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se registrar el ALTA", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public boolean existeDfe(String cuit) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "Select 1 From dfe Where n_cuit = ?";
		try {
			ps = this.prepareStatement( sql );
			ps.setString( 1, cuit );
			
			rs = ps.executeQuery();
			
			return ( rs.next() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se pudieron recuperar los datos", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	public void registroActualizacionCargaNivel3(String nombre, String apellido, String cuit, String tipoDocumento,
												 String nroDocumento, String correo, String telefono, String celular) throws ExcepcionControladaError {
	
		PreparedStatement ps = null;
		
		String sql = "Update dfe \n"
					+ "Set c_documento = ?, n_documento = ?, d_nombre = ?, d_apellido = ?, d_telefono = ?, d_celular = ?, d_dfe = ? \n"
					+ "Where n_cuit = ? ";
		
		try {
			ps = this.prepareStatement( sql );
			ps.setInt( 1, TiposDocumento.getTipoDocumentoPorNombrePiletas(tipoDocumento).getCodigo());
			ps.setInt( 2, Integer.parseInt(nroDocumento) );
			ps.setString( 3, nombre );
			ps.setString( 4, apellido );
			ps.setString( 5, telefono );
			ps.setString( 6, celular );
			ps.setString( 7, correo );
			ps.setString( 8, cuit );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No se registrar la Actualización", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}
