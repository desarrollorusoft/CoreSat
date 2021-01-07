package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.adds.CommerceDebtLogger;
import ar.com.cognisys.sat.core.contenedor.ContenedorTasasComercio;
import ar.com.cognisys.sat.core.logger.CoreSATLogger;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.DateUtils;
import ar.com.cognisys.sat.core.modelo.comun.Tasa;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuota;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudaAdapter;

public class DaoRecalcularDeuda extends DAO {

	private static final String UPDATE_TRANSACCIONES_COMERICO = "UPDATE recaudaciones:ddjj_datos_deuda \n" +
																"SET seleccionado = 'S' \n" +
																"WHERE c_cuenta = ? \n" +
																"AND n_transac in ( :LIST )";

	private static final String UPDATE_TRANSACCIONES_COMERICO_REINICIO = "UPDATE recaudaciones:ddjj_datos_deuda \n" +
																		"SET seleccionado = 'N' \n" +
																		"WHERE c_cuenta = ? ";

	private static final String RECALCULAR_DEUDA_COMERCIO = "{call recaudaciones:sp_ddjj_genera_recibo_cog( ? , ? )}";

	private static final String SQL_RECUPERAR_PERIODOS_RECALCULADOS = "SELECT vencido, n_transac, c_tasa, n_ano, n_cuota, i_capital, i_recargo, i_multa, (i_capital+i_recargo+i_multa) as i_total, f_vto, c_actual " +
																	"FROM recaudaciones:ddjj_datos_deuda \n" +
																	"WHERE c_cuenta = ? \n" +
																	"AND n_transac in ( :LIST )";

	public DaoRecalcularDeuda(Connection connection) {
		super( connection );
	}
	
	/**
	 * Metodo para recaulcular Deuda Optimizado - Para deuda con lista de Cuotas
	 * @param cuenta
	 * @param pagoContado
	 * @param fechaPago
	 * @param listaCuotas
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Deuda recalcularDeuda(Cuenta cuenta, boolean pagoContado, Date fechaPago, 
								 List<Cuota> listaCuotas) throws ExcepcionControladaError {
		Deuda deuda = null;
		try {
			this.insertarCuotasAPagar( listaCuotas );
			
			this.recacular( cuenta.getContribuyente().getTipoDocumento(), 
							cuenta.getContribuyente().getNumeroDocumento(), 
							pagoContado, 
							cuenta.getDatoCuenta(), 
							(cuenta.sos(TiposCuentas.CEMENTERIO) ? ((CuentaCementerio)cuenta).getNumeroNomenclador() : 0), 
							fechaPago, 
							cuenta.getTipoCuenta().getCodigo() );

			deuda = FactoryDeudaAdapter.generar();

			this.cargarCuotasAPagar( deuda );
			deuda.caulcularTotales();
			deuda.setIdCarrito( this.recuperarIdCarrito() );
			deuda.setNumeroComprobante( this.recuperarNumeroComprobante() );

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}

		return deuda;
	}

	public Deuda recalcularDeudaComercio(Cuenta cuenta, Date fechaPago, List<Cuota> listaCuotas) throws ExcepcionControladaError {
		Deuda deuda = null;
		try {
			CommerceDebtLogger.getLogger().info("Se comienza el proceso de generacion de comprobante. CUENTA ["+ cuenta.getNumero() +"]");
			CommerceDebtLogger.getLogger().info("Listado de cuotas ["+ listaCuotas.toString() +"]");

			CommerceDebtLogger.getLogger().info("Se reinician los seleccionado. CUENTA ["+ cuenta.getNumero() +"]");
			this.blanquearTransacciones( cuenta.getNumero() );

			CommerceDebtLogger.getLogger().info("Se ponen los periodos seleccionado. CUENTA ["+ cuenta.getNumero() +"]");
			this.marcarTransacciones( listaCuotas, cuenta.getNumero() );

			CommerceDebtLogger.getLogger().info("Se procede a generar el comprobante. CUENTA ["+ cuenta.getNumero() +"] - FECHA ["+ DateUtils.format(fechaPago, "yyyy-MM-dd") +"]");
			String comprobante = this.recacularComercio( cuenta, fechaPago );

			deuda = FactoryDeudaAdapter.generar();

			this.cargarCuotasComercio( deuda, cuenta, listaCuotas );
			deuda.caulcularTotales();
			deuda.setNumeroComprobante( (comprobante != null ? comprobante.trim() : comprobante ) );

			return deuda;
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
	}

	private void blanquearTransacciones(Integer numero) throws ErrorEnBaseException {
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_TRANSACCIONES_COMERICO_REINICIO );
			ps.setInt( 1, numero);

			ps.executeUpdate();

			CommerceDebtLogger.getLogger().info("SQL OK ["+ UPDATE_TRANSACCIONES_COMERICO_REINICIO.replace("?", numero.toString()) +"]");
		} catch ( SQLException e ) {
			CommerceDebtLogger.getLogger().error("Fallo el SQL ["+ UPDATE_TRANSACCIONES_COMERICO_REINICIO.replace("?", numero.toString()) +"]");
			throw new ErrorEnBaseException( "No es pudo actualizar la tabla de transacciones", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void marcarTransacciones(List<Cuota> listaCuotas, Integer numero) throws ErrorEnBaseException {
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( UPDATE_TRANSACCIONES_COMERICO.replace(":LIST", this.generarListadoIdS(listaCuotas) ));
			ps.setInt( 1, numero);

			ps.executeUpdate();

			CommerceDebtLogger.getLogger().info("SQL OK ["+ UPDATE_TRANSACCIONES_COMERICO.replace(":LIST", this.generarListadoIdS(listaCuotas) ).replace("?", numero.toString()) +"]");
		} catch ( SQLException e ) {
			CommerceDebtLogger.getLogger().error("Fallo el SQL ["+ UPDATE_TRANSACCIONES_COMERICO.replace(":LIST", this.generarListadoIdS(listaCuotas) ).replace("?", numero.toString()) +"]");
			throw new ErrorEnBaseException( "No es pudo actualizar la tabla de transacciones", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private String recacularComercio(Cuenta cuenta, Date fechaPago) throws ExcepcionControladaError {
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			cs = this.prepareCall( RECALCULAR_DEUDA_COMERCIO );
			cs.setInt( 1, cuenta.getNumero() );
			cs.setDate( 2, new java.sql.Date( fechaPago.getTime() ) );
			cs.execute();

			rs = cs.getResultSet();

			CommerceDebtLogger.getLogger().info("SP OK: call recaudaciones:sp_ddjj_genera_recibo_cog("+cuenta.getNumero()+","+DateUtils.formatInformix(fechaPago)+")");

			try {
				if (rs == null || !rs.next() || rs.getInt("v_sql_err") != 0) {
					CommerceDebtLogger.getLogger().warn("SP devolvio codigo incorrecto");
					throw new ExcepcionControladaError("El SP fallo internamente para la cuenta [" + cuenta.getNumero().toString() + "]", null);
				} else
					return rs.getString(3);
			} catch (SQLException e) {
				CommerceDebtLogger.getLogger().error("Fallo la recuperacion de datos", e);
				throw new ExcepcionControladaError("No se pudo obtener el ResultSet del SP para la cuenta [" + cuenta.getNumero().toString() + "]", null);
			} finally {
				super.cerrarRecursoRS(rs);
				super.cerrarRecursoST(cs);
			}
		} catch ( Exception ex ) {
			CommerceDebtLogger.getLogger().error("SP FALLO: call recaudaciones:sp_ddjj_genera_recibo_cog("+cuenta.getNumero()+","+DateUtils.formatInformix(fechaPago)+")", ex);
			throw new ExcepcionControladaError( "Ocurrió un error al obtener un comprobante ("+ex.getMessage()+")", ex );
		} finally {
			super.cerrarRecursoST( cs );
		}
	}

	private void cargarCuotasComercio(Deuda deuda, Cuenta cuenta, List<Cuota> listaCuotas) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERAR_PERIODOS_RECALCULADOS.replace(":LIST", this.generarListadoIdS(listaCuotas) ));
			ps.setInt( 1, cuenta.getNumero() );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				boolean esVencida = ( "S".equals(rs.getString( "vencido" )) );
				Tasa tasa = ContenedorTasasComercio.getInstancia().recuperar(rs.getInt("c_tasa"));
				Cuota cuota = FactoryCuota.generarIntanciaCompleta( tasa.getNombre(),
																	rs.getString("c_actual"),
																	rs.getDate("f_vto"),
																	rs.getString("n_ano") + "-" + rs.getString("n_cuota"),
																	rs.getFloat( "i_capital" ),
																	rs.getFloat( "i_multa" ),
																	rs.getFloat( "i_recargo" ),
																	rs.getFloat( "i_total" ),
																	rs.getInt( "n_transac" ),
																	rs.getString("c_actual").contains("DM"),
																	rs.getInt("n_estado"),
																	rs.getInt("n_origen"),
																	tasa.getCodigo() );
				deuda.agregarCouta( cuota, esVencida );
			}

			this.agruparPeriodos( deuda );
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}

	private String generarListadoIdS(List<Cuota> listaCuotas) {
		String ids = "";
		for (Cuota c : listaCuotas)
			ids += c.getNumeroTransaccion() + ",";
		return ids.substring(0, ids.length() - 1);
	}

	/**
	 * Metodo para recaulcular Deuda Optimizado - Para deuda SIN lista de Cuotas
	 * @param cuenta
	 * @param pagoContado
	 * @param fechaPago
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public String recalcularDeuda(Cuenta cuenta, boolean pagoContado, Date fechaPago) throws ExcepcionControladaError {
		try {
			this.recacular( cuenta.getContribuyente().getTipoDocumento(), 
							cuenta.getContribuyente().getNumeroDocumento(), 
							pagoContado, 
							cuenta.getDatoCuenta(), 
							(cuenta.sos(TiposCuentas.CEMENTERIO) ? ((CuentaCementerio)cuenta).getNumeroNomenclador() : 0), 
							fechaPago, 
							cuenta.getTipoCuenta().getCodigo() );

			
			return this.recuperarNumeroComprobante();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
	}

	/**
	 * Metodo para recalcular la deuda de ABL, AUTO y MOTO
	 * 
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param pagoContado
	 * @param fecha
	 * @param listaCuotas
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Deuda recalcularDeuda(Integer tipoDocumento, Integer numeroDocumento, boolean pagoContado, String datoCuenta, Date fecha, List<Cuota> listaCuotas, TiposCuentas tipoCuenta) throws ExcepcionControladaError {
		
		Deuda deuda = null;
		try {
			this.insertarCuotasAPagar( listaCuotas );

			Integer sistema = ( tipoCuenta == TiposCuentas.RODADOS ) ? 4 : tipoCuenta.getCodigo();

			this.recacular( tipoDocumento, numeroDocumento, pagoContado, datoCuenta, 0, fecha, sistema );

			deuda = FactoryDeudaAdapter.generar();

			this.cargarCuotasAPagar( deuda );
			deuda.caulcularTotales();
			deuda.setIdCarrito( this.recuperarIdCarrito() );
			deuda.setNumeroComprobante( this.recuperarNumeroComprobante() );

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}

		return deuda;
	}
	
	/**
	 * Metodo para recalcular deudas de CEMENTERIO
	 * 
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param pagoContado
	 * @param numeroNom
	 * @param fecha
	 * @param listaCuotas
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Deuda recalcularDeuda(Integer tipoDocumento, Integer numeroDocumento, boolean pagoContado, String datoCuenta, Integer numeroNom, Date fecha, List<Cuota> listaCuotas) throws ExcepcionControladaError {
		Deuda deuda = null;

		try {
			this.insertarCuotasAPagar( listaCuotas );

			this.recacular( tipoDocumento, numeroDocumento, pagoContado, datoCuenta, numeroNom, fecha, TiposCuentas.CEMENTERIO.getCodigo() );

			deuda = FactoryDeudaAdapter.generar();

			this.cargarCuotasAPagar( deuda );
			deuda.caulcularTotales();
			deuda.setIdCarrito( this.recuperarIdCarrito() );
			deuda.setNumeroComprobante( this.recuperarNumeroComprobante() );

		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}

		return deuda;
	}
	
	private void insertarCuotasAPagar(List<Cuota> listaCuotas) throws ErrorEnBaseException {

		this.dropearDeudaCheck();
		this.crearDeudaCheck();
		
		for ( Cuota cuota : listaCuotas )
			if ( cuota != null )
				this.insertarCuota( cuota );
	}
	
	private void crearDeudaCheck() throws ErrorEnBaseException {
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( "create temp table deuda_check (n_transac integer) with no log" );
			ps.executeUpdate();

		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( "No es posible crear la tabla 'deuda_check'", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	private void dropearDeudaCheck() {

		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement("drop table deuda_check");
			
			ps.executeUpdate();
		} catch (Exception e) {
			CoreSATLogger.getLogger().warn( "Error al Dropear la Tabla 'deuda_check'" );
		} finally {
			super.cerrarRecursoST( ps );
		}
		
	}

	private void insertarCuota(Cuota cuota) throws ErrorEnBaseException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = this.prepareStatement( "insert into deuda_check values(?)" );
			
			ps.setInt( 1, cuota.getNumeroTransaccion() );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( "No es posible insertar las cuotas a pagar", e );
		} finally {
			super.cerrarRecursoST( ps );
		}
		
	}

	private void recacular(Integer tipoDocumento, Integer numeroDocumento, boolean pagoContado, String datoCuenta, Integer numeroNom, Date fechaSeleccionada, Integer sistema) throws ExcepcionControladaError {

		CallableStatement cs = null;

		try {
			cs = this.prepareCall( QueriesInformix.RECALCULAR_DEUDA_CEMENTERIO.getQuery() );

			cs.setInt( 1, tipoDocumento );
			cs.setInt( 2, numeroDocumento );
			cs.setInt( 3, pagoContado ? 1 : 0 );
			cs.setString( 4, datoCuenta );
			cs.setInt( 5, numeroNom );
			cs.setInt( 6, sistema );
			cs.setDate( 7, new java.sql.Date( fechaSeleccionada.getTime() ) );

			cs.execute();
		} catch ( Exception ex ) {
			throw new ExcepcionControladaError( "Ocurrió un error al obtener un comprobante ("+ex.getMessage()+")", ex );
		} finally {
			super.cerrarRecursoST( cs );
		}

	}
	
	private void cargarCuotasAPagar(Deuda deuda) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( QueriesInformix.BOLETA_PAGO_CUOTAS.getQuery() );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				boolean esVencida = ( rs.getInt( "vencida" ) == 1 );
				String periodo = rs.getString( "d_periodo" );
				String tasa = rs.getString( "d_tasa_abr" );
				Cuota cuota = FactoryCuota.generarIntanciaCompleta( ( tasa == null ) ? "" : tasa.trim(), 
																   "-", 
																   null,
																   ( periodo == null ) ? periodo : periodo.trim(), 
																   rs.getFloat( "i_capital" ), 
																   rs.getFloat( "i_multa" ), 
																   rs.getFloat( "i_recargo" ), 
																   Cuota.calcularTotalPeriodo( rs.getDouble( "i_capital" ), rs.getDouble( "i_multa" ), rs.getDouble( "i_recargo" ) ),
																   rs.getInt( "n_transac" ),
																   ( rs.getInt( "n_registro" ) == 1 ),
																   rs.getInt( "c_tasa" ));
				deuda.agregarCouta( cuota, esVencida );
			}

			this.agruparPeriodos( deuda );
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
	
	private void agruparPeriodos(Deuda deuda) {

		Collections.sort( deuda.getListaCuotasAVencer() );
		for ( int i = 0; i < deuda.getListaCuotasAVencer().size() - 1; i++ ) {
			if ( deuda.getListaCuotasAVencer().get( i ).getPeriodo().equals( deuda.getListaCuotasAVencer().get( i + 1 ).getPeriodo() ) ) {
				// Tienen el mismo periodo, asi que van juntas. Se vinculan las
				// cuentas.
				deuda.getListaCuotasAVencer().get( i ).setCuotaAsociada( deuda.getListaCuotasAVencer().get( i + 1 ) );
				deuda.getListaCuotasAVencer().get( i + 1 ).setCuotaAsociada( deuda.getListaCuotasAVencer().get( i ) );
			}
		}

		Collections.sort( deuda.getListaCuotasVencidas() );
		for ( int i = 0; i < deuda.getListaCuotasVencidas().size() - 1; i++ ) {
			if ( deuda.getListaCuotasVencidas().get( i ).getPeriodo().equals( deuda.getListaCuotasVencidas().get( i + 1 ).getPeriodo() ) ) {
				// Tienen el mismo periodo, asi que van juntas. Se vinculan las
				// cuentas.
				deuda.getListaCuotasVencidas().get( i ).setCuotaAsociada( deuda.getListaCuotasVencidas().get( i + 1 ) );
				deuda.getListaCuotasVencidas().get( i + 1 ).setCuotaAsociada( deuda.getListaCuotasVencidas().get( i ) );
			}
		}
	}
	
	/**
	 * Metodo para recuperar el numero de carrito (puede que este dato no se use mas)
	 * 
	 * @return
	 * @throws ErrorEnBaseException
	 */
	public String recuperarIdCarrito() throws ErrorEnBaseException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( "select * from tmp_carrito" );
			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getString( "n_carrito" );

			throw new ExcepcionControladaError( "tmp_carrito estaba vacia", null );
		} catch ( Exception e ) {
			throw new ErrorEnBaseException( "Error recuperando el identificador de pago", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

	}

	/**
	 * Metodo para recuperar el numero de comprobante
	 * 
	 * @return
	 * @throws ErrorEnBaseException
	 */
	public String recuperarNumeroComprobante() throws ErrorEnBaseException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( "select * from tmp_comprob_web" );

			rs = ps.executeQuery();

			if ( rs.next() )
				return rs.getString( "n_comprob" );

			throw new ExcepcionControladaError( "tmp_comprob_web estaba vacia", null );
		} catch ( Exception e ) {
			throw new ErrorEnBaseException( "Error recuperando el comprobante", e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
}