package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaAVencer;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPPC;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.Parametros;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePago;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.dao.ppc.DaoCalculaFechaTope;
import ar.com.cognisys.sat.core.modelo.dao.ppc.DaoRecargo;
import ar.com.cognisys.sat.core.modelo.enums.EstadoCuotaAVencer;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryCuotaAVencer;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryCuotaPPC;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryCuotaPlanDePago;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryEstadoCuotaAVencer;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryPlanDePago;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryPlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryTotalCuota;
import ar.com.cognisys.sat.core.modelo.ppc.SumadorCuotasPlanPago;
import ar.com.cognisys.sat.core.modelo.ppc.TotalPlanPago;
import ar.com.cognisys.sat.core.modelo.ppc.TransaccionMultaDTO;
import ar.com.cognisys.sat.core.modelo.ppc.codigo.CodigoPlanPagoDTO;
import ar.com.cognisys.sat.core.modelo.ppc.codigo.CodigoPlanPagoMoratoriaDTO;
import ar.com.cognisys.sat.core.modelo.ppc.codigo.CodigoPlanPagoRecuperadoDTO;
import ar.com.cognisys.sat.core.modelo.ppc.cuota.CuotaPlanPagoCeroDTO;
import ar.com.cognisys.sat.core.modelo.ppc.cuota.CuotaPlanPagoDTO;
import ar.com.cognisys.sat.core.modelo.ppc.cuota.CuotaPlanPagoRecuperadoDTO;

public class DaoPlanDePago extends Dao {

	private static final String RS_F_VENCIMIENTO = "f_vencimiento";
	private static final String RECUPERAR_CODIGO_MORATORIA = 
			"select c_moratoria from ppc_cab where n_plan = ?";
	private static final String BUSCAR_VENCIMIENTOS = 
			"execute procedure sp_busca_vencimiento(?)";
	private static final String RECUPERAR_VENCIMIENTO = 
			"select * from tmp_fecha_venc";
	private static final String RECUPERAR_TRANSACCIONES = 
			"select p.n_transac, t.c_sistema, t.c_tasa, t.c_actual \n" +
			"from ppc_transac p, transacciones t \n" +
			"where p.n_plan= ? \n" +
			"and p.c_cuota = 'C' \n" +
			"and p.c_plan = 1 \n" +
			"and t.c_actual <> 'CS' \n" +
			"and p.n_transac = t.n_transac \n" +
			"order by 1 asc";
	private static final String RECUPERAR_RECARGO_EXTRA = 
			"select sum(i_recargo) as total from cuotas_ppc where n_plan = ? and f_pago is null";
	private static final String RECUPERAR_MULTA_EXTRA = 
			"select sum(i_multa) as total from cuotas_ppc where n_plan = ? and f_pago is null";
	private static final String RECUPERAR_ESTADO = 
			"SELECT n_estado \n" + 
			"FROM estados \n" + 
			"WHERE n_orden_estado = ( \n" + 
			"SELECT MAX(n_orden_estado) \n" + 
			"FROM estados \n" + 
			"WHERE c_estado in ('DM', 'DL') \n" + 
			"AND n_transac = ?) \n";
	private static final String RECUPERAR_MORA = 
			"SELECT c_sist_rec, c_recargo FROM mora WHERE n_registro = ?";
	private static final String ES_MOVIMIENTO_140 = 
			"select * from cta_cte where n_transac = ? and c_movimiento = 140";
	private static final String RECUPERAR_SALDO_TRANSACCION = 
			 "select sum(i_capital)*-1 as capital from cta_cte where n_transac= ? and c_estado_deuda not in ('AD','AP') and c_movimiento not in (84,78)";

	public DaoPlanDePago(Connection connection) {
		super(connection);
	}

	public boolean tienePlanPagoABL(Cuenta cuenta) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "SELECT 1\n" 
						 + "FROM solicitudes s , ppc_cab p\n"
						 + "WHERE s.c_plan = 1\n" 
						 + "AND s.c_sistema = 2\n"
						 + "AND s.c_cuenta  = ?\n"
						 + "AND p.c_plan = 1\n" 
						 + "AND p.n_plan = s.n_plan\n"
						 + "AND p.c_estado in(1,7)";

			ps = this.getConnection().prepareStatement(query);
			ps.setInt(1, cuenta.getNumero());
			rs = ps.executeQuery();

			return (rs.next());
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public boolean tienePlanPagoVehiculos(Cuenta cuenta) throws ErrorRecuperacionDatosException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "SELECT 1\n" 
						 + "FROM solicitudes s , ppc_cab p\n"
						 + "WHERE s.c_plan = 1\n" 
						 + "AND (s.c_sistema = "+TiposCuentas.VEHICULOS.getCodigo()+" OR  s.c_sistema = 4 )\n" 
						 + "AND s.c_cuenta = ?\n"
						 + "AND p.c_plan = 1\n" 
						 + "AND p.n_plan = s.n_plan\n"
						 + "AND p.c_estado in(1,7)";

			ps = this.getConnection().prepareStatement(query);
			ps.setInt(1, cuenta.getNumero());
			rs = ps.executeQuery();

			return (rs.next());
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public CuotaPPC cancelacionPlan(PlanDePagoAPagar planDePagoAPagar) throws ExcepcionControladaError {
		
		List<CuotaPlanPagoDTO> cuotas = this.obtenerCuotas( planDePagoAPagar.getNroPlan() );
		
		TotalPlanPago total = new SumadorCuotasPlanPago( cuotas ).sumar();
		
		total.sumarRecargo( this.recuperarRecargoExtra( planDePagoAPagar.getNroPlan() ) );
		
		total.sumarMulta( this.recuperarMultaExtra( planDePagoAPagar.getNroPlan() ) );
		
		return this.generarCuotaPPC( total );
		
	}

	private CuotaPPC generarCuotaPPC(TotalPlanPago total) {
		return FactoryCuotaPPC.generar( null, total.getCapital(), total.getRecargo(), total.getMulta(), 0f, total.total(), 0 );
	}

	private float recuperarMultaExtra(Integer nroPlan) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_MULTA_EXTRA );
			st.setInt( 1, nroPlan );
			rs = st.executeQuery();

			return rs.next() ? rs.getFloat( 1 ) : 0f;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar la Multa Extra", e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
		
	}

	private float recuperarRecargoExtra(Integer nroPlan) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_RECARGO_EXTRA );
			st.setInt( 1, nroPlan );
			rs = st.executeQuery();

			return rs.next() ? rs.getFloat( 1 ) : 0f;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el Recargo Extra para el plan " + nroPlan, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
		
	}

	private List<CuotaPlanPagoDTO> obtenerCuotas(Integer nroPlan) throws ExcepcionControladaError {

		List<CuotaPlanPagoDTO> lista = new ArrayList<CuotaPlanPagoDTO>();
		
		int codMoratoria = this.recuperarMoratoria( nroPlan );
		Date fechaVencimiento = this.obtenerFechaVencimiento( nroPlan );
		
		List<TransaccionMultaDTO> transacciones = this.obtenerTransacciones( nroPlan );
		for ( TransaccionMultaDTO transaccion: transacciones )
			lista.add( this.obtenerCuota( transaccion, nroPlan, codMoratoria, fechaVencimiento ) );
		
		return lista;
	}

	private CuotaPlanPagoDTO obtenerCuota(TransaccionMultaDTO transaccion, Integer nroPlan, int codMoratoria, Date vencimiento) throws ExcepcionControladaError {
		
		CodigoPlanPagoDTO codigo = this.obtenerCodigo( codMoratoria, transaccion );
		
		Date ahora = new Date();
		
		return this.obtenerCuota( transaccion, codMoratoria == 1 ? ahora : vencimiento, ahora, codigo, nroPlan );
	}

	private CuotaPlanPagoDTO obtenerCuota(TransaccionMultaDTO transaccion, Date vencimiento, Date ahora, CodigoPlanPagoDTO codigo, Integer nroPlan) throws ExcepcionControladaError {
		
		try {
			float capital = this.saldoTransaccion( transaccion.getNumero() );

			Date tope = new DaoCalculaFechaTope( this.getConnection() ).calcular( transaccion, ahora );
			
			return capital == 0 ? new CuotaPlanPagoCeroDTO( tope ) : this.recuperarCuota( transaccion, vencimiento, ahora, tope, codigo, nroPlan, capital );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible obtener la cuota para la transaccion " + transaccion, e );
		}
	}

	private float saldoTransaccion(long transaccion) throws ExcepcionControladaError {
		return esMovimiento140( transaccion ) ? 0 : this.recuperarSaldoTransaccion( transaccion );
	}

	private float recuperarSaldoTransaccion(long transaccion) throws ExcepcionControladaError {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_SALDO_TRANSACCION );
			st.setLong( 1, transaccion );
			rs = st.executeQuery();

			return rs.next() ? rs.getFloat( "capital" ) : 0;

		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No se pudo averiguar el saldo de la transaccion " + transaccion, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
	}

	private boolean esMovimiento140(long transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( ES_MOVIMIENTO_140 );
			st.setLong( 1, transaccion );
			rs = st.executeQuery();

			return rs.next();
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No se pudo averiguar si la transaccion " + transaccion + " es movimiento 140", e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
	}

	private CuotaPlanPagoDTO recuperarCuota(TransaccionMultaDTO transaccion, Date vencimiento, Date ahora, Date tope, CodigoPlanPagoDTO codigo, Integer nroPlan, float capital) throws ExcepcionControladaError {
		Parametros pa = new DaoRecargo( this.getConnection() ).calcular( transaccion, codigo, vencimiento, ahora, nroPlan, capital );
		
		return new CuotaPlanPagoRecuperadoDTO( capital, pa.getRecargo(), pa.getMulta(), tope );
	}

	private CodigoPlanPagoDTO obtenerCodigo(int codMoratoria, TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		return codMoratoria != 0 && codMoratoria != 1 ? new CodigoPlanPagoMoratoriaDTO() : this.obtenerCodigo( transaccion );
	}

	private CodigoPlanPagoDTO obtenerCodigo(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		int numEstado = this.recuperarEstado( transaccion.getNumero() );

		if ( numEstado <= 0 )
			return ( transaccion.isTasa( 405 ) || transaccion.isTasa( 406 ) ) ? new CodigoPlanPagoRecuperadoDTO( 3, 17 ) : new CodigoPlanPagoRecuperadoDTO( 3, transaccion.getCodSistema() );

		CodigoPlanPagoDTO codigo = this.recuperarPorMora( numEstado );
		if ( codigo == null )
			codigo = new CodigoPlanPagoRecuperadoDTO( transaccion.getCodSistema(), transaccion.getCodSistema() );

		return codigo;
	}

	private CodigoPlanPagoDTO recuperarPorMora(int numEstado) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_MORA );
			st.setInt( 1, numEstado );
			rs = st.executeQuery();

			return rs.next() ? new CodigoPlanPagoRecuperadoDTO( rs.getInt( "c_sist_rec" ), rs.getInt( "c_sist_rec" ) ) : null;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar la mora para el estado " + numEstado, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
		
	}

	private int recuperarEstado(long transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_ESTADO );
			st.setLong( 1, transaccion );
			rs = st.executeQuery();

			return rs.next() ? rs.getInt( 1 ) : 0;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el estado de la transaccion " + transaccion, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
	}

	private List<TransaccionMultaDTO> obtenerTransacciones(Integer nroPlan) throws ExcepcionControladaError {

		List<TransaccionMultaDTO> lista = new ArrayList<TransaccionMultaDTO>();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_TRANSACCIONES );
			st.setInt( 1, nroPlan );
			rs = st.executeQuery();

			while ( rs.next() )
				lista.add( new TransaccionMultaDTO( rs.getLong( "n_transac" ), rs.getInt( "c_sistema" ), rs.getInt( "c_tasa" ), rs.getString( "c_actual" ) ) );
			
			return lista;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible obtener las Transacciones para el plan " + nroPlan, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
		
	}

	private Date obtenerFechaVencimiento(Integer nroPlan) throws ExcepcionControladaError {
		
		this.cargarVencimientos( nroPlan );
		
		Date vencimiento = this.recuperarVencimiento();
		
		Date ahora = new Date();
		return vencimiento.after( ahora ) ? ahora : vencimiento;
	}

	private Date recuperarVencimiento() throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_VENCIMIENTO );
			rs = st.executeQuery();

			return rs.next() ? rs.getDate( 1 ) : new Date();
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible obtener el Vencimiento", e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
		
	}

	private void cargarVencimientos(Integer nroPlan) throws ExcepcionControladaError {
		
		CallableStatement st = null;
		try {
			
			st = this.getConnection().prepareCall( BUSCAR_VENCIMIENTOS );
			st.setInt( 1, nroPlan );
			st.execute();
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible buscar los Vencimientos para el plan " + nroPlan, e );
		} finally {
			this.cerrarRecursos( st );
		}
	}

	private int recuperarMoratoria(Integer nroPlan) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_CODIGO_MORATORIA );
			st.setInt( 1, nroPlan );
			rs = st.executeQuery();

			return rs.next() ? rs.getInt( "c_moratoria" ) : 0;
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible obtener la Moratoria para el plan " + nroPlan, e );
		} finally {
			this.cerrarRecursos( st, rs );
		}
	}

	@Deprecated
	public Parametros recargosMultas(long transaccion, Date fecha_vto_plan, Date fecha_hoy, float lc_sist_rec, float lc_recargo,
									 float lc_sist_mul, int n_plan) throws ExcepcionControladaError {
		
		Parametros pa = new Parametros();
		Parametros p = new Parametros();
		@SuppressWarnings("unused")
		String fecha_vto = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			float li_capital = 0;

			fecha_hoy = calculaFechaTope(transaccion, fecha_hoy);

			li_capital = saldoTransac(transaccion);

			fecha_vto = sdf.format(fecha_hoy);
			pa.setFechaVencimiento(fecha_hoy);

			if (li_capital == 0) {

				pa.setCapital(0);
				pa.setRecargo(0);
				pa.setMulta(0);

			} else {
				p = calculaRecMul(transaccion, fecha_vto_plan, fecha_hoy, lc_sist_rec, lc_recargo, lc_sist_mul, li_capital, n_plan);

				pa.setMulta(p.getMulta());
				pa.setRecargo(p.getRecargo());
				pa.setCapital(li_capital);
			}
			
			return pa;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar el plan de pago", e);
		}
	}

	@Deprecated
	public Parametros busca_rec_ppc_original(long transaccion, int n_plan) throws ExcepcionControladaError {
		
		Parametros pa = new Parametros();
		String query;
		int n_orden_estado = 0;
		int ln_registro = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			query = "SELECT MAX(n_orden_estado) as n_orden_estado FROM estados WHERE c_estado in ('DM', 'DL') AND n_transac =" + transaccion;
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				n_orden_estado = rs.getInt("n_orden_estado");
			}

			query = " SELECT n_estado FROM estados WHERE c_estado in ('DM', 'DL') AND n_transac = " + transaccion + "  AND n_orden_estado = " + n_orden_estado;
			
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				ln_registro = rs.getInt("n_estado");
			}
			
			if (!(new Integer(ln_registro).equals(null)) && ln_registro > 0) {
				stmt = getConnection().createStatement();
				query = "SELECT c_sist_rec, c_recargo FROM mora WHERE n_registro = "
						+ ln_registro;
				rs = stmt.executeQuery(query);
				if (!rs.next()) {
					stmt = getConnection().createStatement();
					query = " SELECT c_sistema FROM transacciones WHERE n_transac = " + transaccion;
					
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						pa.setSistema(rs.getInt("c_sistema"));
					}

					pa.setSistMul(pa.getSistema());
					pa.setSistRec(pa.getSistema());
					pa.setLcRecargo(1);

					return pa;
				} else {

					pa.setSistMul(rs.getInt("c_sist_rec"));
					pa.setSistRec(rs.getInt("c_sist_rec"));

					pa.setLcRecargo(1);
					return pa;
				}
			}

			int c_tasa = 0;
			query = " select c_sistema, c_tasa from transacciones WHERE n_transac = " + transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				pa.setSistema(rs.getInt(1));
				c_tasa = rs.getInt(2);
			}
			pa.setSistMul(pa.getSistema());
			if ((c_tasa == 405) || (c_tasa == 406)) {
				pa.setSistMul(17);
			}

			pa.setSistRec(3);
			pa.setLcRecargo(1);
			return pa;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar el plan de pago", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
		}
	}

	@Deprecated
	public List<CuotaPlanDePago> getCuotas(PlanDePagoAPagar plan) throws ErrorEnBaseException {

		List<CuotaPlanDePago> cuotas = new ArrayList<CuotaPlanDePago>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "SELECT c.n_cuota_plan, c.f_vencimiento, c.i_capital, c.i_recargo, c.i_multa, c.i_interes_fin,\n"
						 + "	   (Nvl(c.i_capital, 0) + Nvl(c.i_recargo, 0) + Nvl(c.i_multa, 0) + Nvl(c.i_interes_fin, 0)) as total\n"
						 + "FROM cuotas_ppc c\n"
						 + "WHERE c.n_plan = ?\n"
						 + "AND c.f_pago is null\n"
						 + "AND c.f_vencimiento >= TODAY";

			ps = this.getConnection().prepareStatement(query);
			ps.setInt(1, plan.getNroPlan());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				float recargo = rs.getFloat("i_recargo");
				Date vencimiento = rs.getDate( RS_F_VENCIMIENTO );
				
				if ( vencio(vencimiento) )
					recargo = calculaRecargo(vencimiento, rs.getFloat("i_capital"), recargo, rs.getFloat("i_multa"));
				
				cuotas.add(FactoryCuotaPlanDePago.getInstance(rs.getInt("n_cuota_plan"), 
															  vencimiento,
															  rs.getFloat("i_capital"), 
															  recargo,
															  rs.getFloat("i_multa"), 
															  rs.getFloat("i_interes_fin"),
															  new Date()));
			}
		} catch (Exception e) {
			throw new ErrorEnBaseException("Error recuperando las cuotas", e.getCause());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		
		return cuotas;
	}
	
	@Deprecated
	public Parametros calculaRecMul(long transaccion, Date f_vto_plan, Date fecha_hoy, float lc_sist_rec, float lc_recargo,
									float lc_sist_mul, float li_capital, int n_plan) throws ExcepcionControladaError  {
		
		Parametros pa = new Parametros();
		Parametros p = new Parametros();
		String query = new String();
		ResultSet rs;
		int lc_tasa = 0;
		String lc_actual = "";
		int lc_sistema = 0;
		int lc_movimiento = 0;
		int ls_cod_130 = 0;
		int ls_recargo = 0;
		int ls_multa = 0;
		int ls_cod_139 = 0;
		int ln_plan = 0;
		@SuppressWarnings("unused")
		float li_multa = 0;
		float ln_multa = 0;
		float li_tot_mul = 0;
		@SuppressWarnings("unused")
		float li_recargo = 0;
		float ln_recargo = 0;
		
		try {
			Statement stmt = getConnection().createStatement();
			
			// estas eran las variables que pasaban por paremetro
			Float O_li_recargo = new Float(0);
			
			Date f_seg_vto = new Date();
			Date f_prim_vto = new Date();
			
			if (li_capital == 0) {
				pa.setRecargo(0);
				
				pa.setMulta(0);
				return pa;
			}

			query = "Select c_tasa, c_actual, c_sistema from transacciones where n_transac = "
					+ transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				lc_tasa = rs.getInt("c_tasa");
				lc_actual = rs.getString("c_actual");
				lc_sistema = rs.getInt("c_sistema");
			}

			query = "Select c_movimiento from cta_cte where n_transac = "
					+ transaccion + " and i_capital < 0 and c_estado_deuda != 'AD'";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				lc_movimiento = rs.getInt("c_movimiento");
			}

			if (lc_movimiento == 130) {
				ls_cod_130 = 1;
				ls_recargo = 1;
				ls_multa = 1;
			}

			query = "Select s_recargo, s_multa from tas_sist where c_tasa = "
					+ lc_tasa + " and c_sistema = " + lc_sist_rec;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				ls_recargo = rs.getInt("s_recargo");
				ls_multa = rs.getInt("s_multa");
			}

			ls_cod_139 = buscaTransacCod139(transaccion);
			if (lc_movimiento == 141 || lc_movimiento == 800
					|| lc_movimiento == 700) {
				ls_recargo = 0;
				ls_multa = 0;
			}

			if (ls_recargo != 0) {
				if (f_vto_plan.after(fecha_hoy)) {
					li_recargo = 0;
				} else {
					query = "select n_recargo from recargos where c_sistema = "
							+ lc_sist_rec + " and c_recargo = " + lc_recargo
							+ " and today between f_vigencia and f_vencimiento";
					rs = stmt.executeQuery(query);
					if (!rs.next()) {
						ln_recargo = 0;
					} else {
						ln_recargo = rs.getFloat("n_recargo");
					}

					if (lc_actual.equals(new String("DI")) && lc_sistema == 2) {
						query = "select f_prim_vto, f_seg_vto from vencimientos_int where n_transac= "
								+ transaccion;
						rs = stmt.executeQuery(query);
						if (rs.next()) {
							f_prim_vto = rs.getDate("f_prim_vto");
							f_seg_vto = rs.getDate("f_seg_vto");

							if (fecha_hoy.before(f_prim_vto)
									|| fecha_hoy.equals(f_prim_vto)) {
								fecha_hoy = f_prim_vto;
							} else {
								if (fecha_hoy.before(f_seg_vto)
										|| fecha_hoy.equals(f_seg_vto)) {
									fecha_hoy = f_seg_vto;
								}
							}
						}
					}

					if (ls_cod_130 == 1) {
						O_li_recargo = (new Float(
								li_capital
										* ((fecha_hoy.getTime() - f_vto_plan.getTime()) / 86400000)
										* ln_recargo));
					} else {
						if (!(lc_actual.equals("PV"))) {
							O_li_recargo = (new Float(
									li_capital
											* ((fecha_hoy.getTime() - f_vto_plan.getTime()) / 86400000)
											* ln_recargo));
						} else {
							O_li_recargo = (new Float(
									(li_capital
											* ((fecha_hoy.getTime() - f_vto_plan.getTime()) / 86400000) * ln_recargo) / 2));
						}
					}
				}
			} else {
				O_li_recargo = new Float(0);
			}

			if (ls_multa == 1) {
				if (lc_actual.equals("DP") || lc_actual.equals("PP")) {
					query = "select n_estado_deuda from cta_cte where n_transac = "
							+ transaccion + " and c_estado_deuda = 'DP'";
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						ln_plan = rs.getInt("n_estado_deuda");
					}
				}

				if (ln_plan != 0) {
					ln_multa = 0;
					query = "select c_multa from ppc_transac where c_plan = 1 and n_plan = "
							+ ln_plan + " and n_transac = " + transaccion;
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						ln_multa = rs.getInt("c_multa");
					}

					if (ln_multa == 0) {
						query = "select n_multa from sist_multa where c_sistema = "
								+ lc_sist_mul;
						rs = stmt.executeQuery(query);
						if (rs.next()) {
							ln_multa = rs.getInt("n_multa");
						} else {
							ln_multa = 0;
						}
					} else {
						query = "select c_multa, sum(p.i_multa) as tot_multa from per_cuotas_ppc p, cuotas_ppc c, ppc_transac t ";
						query = query + " where t.c_plan = 1 and t.n_plan = "
								+ ln_plan + " and t.n_transac = " + transaccion
								+ " and p.n_plan = t.n_plan";
						query = query
								+ " and p.n_transac = t.n_transac and p.n_plan = c.n_plan and p.n_cuota_plan = c.n_cuota_plan and c.f_pago is null group by 1";
						rs = stmt.executeQuery(query);
						if (rs.next()) {
							ln_multa = rs.getFloat("c_multa");
							li_tot_mul = rs.getFloat("tot_multa");
						}

						if (li_tot_mul == 0) {
							ln_multa = 0;
						}

						if (ls_cod_130 == 1) {
							p = calculaCod130(transaccion,
									O_li_recargo.floatValue(), li_tot_mul, n_plan);

							li_tot_mul = p.getSistMul();
							O_li_recargo = new Float(p.getLcRecargo());
						}

						if (lc_movimiento != 141 || lc_movimiento != 130) {
							ls_cod_139 = buscaTransacCod139(transaccion);
						}

						if (ls_cod_139 == 1) {
							p = calculaCod139(transaccion,
									O_li_recargo.floatValue(), li_tot_mul, n_plan);

							li_tot_mul = p.getSistMul();
							O_li_recargo = new Float(p.getLcRecargo());
						}

						return p;
						// por aca salen la variables O_*****
					}
				} else {
					query = "select n_multa from sist_multa where c_sistema = "
							+ lc_sist_mul;
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						ln_multa = rs.getFloat("n_multa");
					} else {
						ln_multa = 0;
					}
				}
				li_multa = li_capital * ln_multa;
			} else {
				li_multa = 0;
			}

			if (ls_cod_130 == 1) {
				p = calculaCod130(transaccion, O_li_recargo.floatValue(),
						li_tot_mul, n_plan);

				li_tot_mul = p.getMulta();
				O_li_recargo = new Float(p.getRecargo());
			}

			if (lc_movimiento != 141 || lc_movimiento != 130) {
				ls_cod_139 = buscaTransacCod139(transaccion);
			}

			if (ls_cod_139 == 1) {
				p = calculaCod139(transaccion, O_li_recargo.floatValue(),
						li_tot_mul, n_plan);

				li_tot_mul = p.getMulta();
				O_li_recargo = new Float(p.getRecargo());
			}

			pa.setRecargo(O_li_recargo.floatValue());
			pa.setMulta(li_tot_mul);
			return pa;
		} catch (Exception e) {
			throw new ErrorEnBaseException("Error recuperando las cuotas", e);
		}
	}

	public Parametros calculaCod139(long transaccion, float li_recargo,
			float li_multa, int n_plan) throws Exception {

		Parametros pa = new Parametros();
		String query = new String();
		int c_plan = 0;
		float li_porrec = 0;
		float li_por_mul = 0;
		float xi_recargo = 0;
		float xi_multa = 0;

		xi_recargo = li_recargo;
		xi_multa = li_multa;

		Statement stmt = getConnection().createStatement();
		ResultSet rs;
		// saco el c_plan para no pasarlo por referencia dentro de la funcion
		query = "select c_plan from ppc_cab where n_plan= " + n_plan;
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			c_plan = rs.getInt("c_plan");
		}

		if (c_plan == 0) {
			query = "select i_porrec, i_pormul from transac_cod_139 where n_transac = "
					+ transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				li_porrec = rs.getFloat("i_porrec");
				li_por_mul = rs.getFloat("i_pormul");
			}
		} else {
			query = "select i_porrec, i_pormul from transac_cod_139 where n_transac = "
					+ transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				li_porrec = rs.getFloat("i_porrec");
				li_por_mul = rs.getFloat("i_pormul");
			} else {

				pa.setRecargo(li_recargo);
				pa.setMulta(li_multa);
				return pa;
			}
		}

		if (li_porrec == 100) {
			li_recargo = 0;
		} else {
			if (li_porrec != 0) {
				li_recargo = (li_recargo * li_porrec) / 100;
				li_recargo = xi_recargo - li_recargo;
			}
		}

		if (li_por_mul == 100) {
			li_multa = 0;
		} else {
			if (li_por_mul != 0) {
				li_multa = (li_multa * li_por_mul) / 100;
				li_multa = xi_multa - li_multa;
			}
		}

		pa.setRecargo(li_recargo);
		pa.setMulta(li_multa);
		return pa;

	}

	public Parametros calculaCod130(long transaccion, float li_recargo,
			float li_multa, int n_plan) throws Exception {

		String query = new String();
		Parametros pa = new Parametros();
		int c_plan = 0;
		float li_porrec = 0;
		float li_por_mul = 0;
		float xi_recargo = 0;
		float xi_multa = 0;

		xi_recargo = li_recargo;
		xi_multa = li_multa;

		Statement stmt = null;
		ResultSet rs = null; // saco el c_plan para no pasarlo por referencia dentro de la funcion
		
		try {
			stmt = getConnection().createStatement();
			query = "select c_plan from ppc_cab where n_plan= " + n_plan;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				c_plan = rs.getInt("c_plan");
			}

			if (c_plan == 0) {
				query = "select i_porrec, i_pormul from transac_cod_130 where n_transac = "
						+ transaccion
						+ " and n_cod = 130 and c_plan = 0 and n_plan = "
						+ n_plan
						+ " and n_acta = 0";
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					li_porrec = rs.getFloat("i_porrec");
					li_por_mul = rs.getFloat("i_pormul");
				}
			} else {
				query = "select i_porrec, i_pormul from transac_cod_130 where n_transac = "
						+ transaccion
						+ " and n_cod = 130 and c_plan = 0 and n_plan = 0 and n_acta = 0";
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					li_porrec = rs.getInt("i_porrec");
					li_por_mul = rs.getInt("i_pormul");
				} else {

					pa.setRecargo(li_recargo);
					pa.setMulta(li_multa);
					return pa;
				}
			}
			if (li_porrec == 100) {
				li_recargo = 0;
			} else {
				if (li_porrec != 0) {
					li_recargo = (li_recargo * li_porrec) / 100;
					li_recargo = xi_recargo - li_recargo;
				}
			}

			if (li_por_mul == 100) {
				li_multa = 0;
			} else {
				if (li_por_mul != 0) {
					li_multa = (li_multa * li_por_mul) / 100;
					li_multa = xi_multa - li_multa;
				}
			}

			pa.setRecargo(li_recargo);
			pa.setMulta(li_multa);
			return pa;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}

	public int buscaTransacCod139(long transaccion) throws Exception {

		String query = new String();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = getConnection().createStatement();
			query = "select * from transac_cod_139 where n_transac = "
					+ transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}

	@Deprecated
	public float saldoTransac(long transaccion) throws Exception {

		boolean enc = false;
		float capital = 0;
		String query = new String();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = getConnection().createStatement();

			query = "select * from cta_cte where n_transac = " + transaccion
					+ " and c_movimiento = 140";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				enc = true;
			} else {
				enc = false;
			}

			if (enc) {
				return 0;
			} else {
				query = "select sum(i_capital)*-1 as capital from cta_cte where n_transac= "
						+ transaccion
						+ " and c_estado_deuda not in ('AD','AP') and c_movimiento not in (84,78)";
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					capital = rs.getFloat("capital");
				} else {
					capital = 0;
				}
			}
			return capital;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}

	@Deprecated
	public Date calculaFechaTope(long transaccion, Date xf_tope) throws ExcepcionControladaError {

		String query;
		int xc_sistema = 0;
		@SuppressWarnings("unused")
		int xc_tasa = 0;
		Date salida = new Date();
		// Date xf_tope = new Date();
		Date xf_prorroga = new Date();
		Date xf_vencimiento = new Date();
		Date f_prim_vto = new Date();
		Date f_seg_vto = new Date();
		int xn_registro = 0;
		int ln_orden = 0;
		int lc_movim = 0;
		int lc_tasa = 0;
		int ln_ano = 0;
		int ln_cuota = 0;
		String xc_actual = new String("");
		int op = 0;

		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = getConnection().createStatement();
			query = "SELECT c_sistema, c_tasa, c_actual FROM transacciones WHERE n_transac = "
					+ transaccion;
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				xc_sistema = rs.getInt("c_sistema");
				xc_tasa = rs.getInt("c_tasa");
				xc_actual = rs.getString("c_actual");
			}

			if (xc_sistema != 2) {
				salida = xf_tope;
			} else {

				if (xc_actual.equals(new String("PP"))
						|| xc_actual.equals(new String("AC"))
						|| xc_actual.equals(new String("PV"))) {
					stmt = getConnection().createStatement();
					query = "SELECT c_estado_deuda FROM cta_cte WHERE n_transac = "
							+ transaccion
							+ " AND i_capital < 0  AND c_estado_deuda != 'AD'";
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						xc_actual = rs.getString("c_estado_deuda");
					}
				}

				if (xc_actual.equals(new String("DO"))) {
					op = 1;
				}
				if (xc_actual.equals(new String("DI"))) {
					op = 2;
				}
				if (xc_actual.equals(new String("DM"))
						|| xc_actual.equals(new String("DL"))) {
					op = 3;
				}

				switch (op) {
				case 1:
					salida = xf_tope;
					stmt = getConnection().createStatement();
					query = "SELECT f_pago FROM cta_cte WHERE n_transac = "
							+ transaccion + " and c_estado_deuda = '" + xc_actual
							+ "'";
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						xf_vencimiento = rs.getDate("f_pago");
					}
					stmt = getConnection().createStatement();
					query = "SELECT f_prorroga FROM ctrl_operativo";
					rs = stmt.executeQuery(query);

					if (!rs.next()) {
						if (xf_tope.before(xf_vencimiento)
								|| xf_tope.equals(xf_vencimiento)) {
							salida = xf_vencimiento;
						} else {
							salida = xf_tope;
						}
					} else {
						xf_prorroga = rs.getDate("f_prorroga");
					}

					if (xf_vencimiento.after(xf_tope)
							|| xf_vencimiento.equals(xf_tope)) {
						salida = xf_vencimiento;
					} else {
						if (xf_prorroga.before(xf_tope)
								|| xf_prorroga.equals(xf_tope)) {
							salida = xf_vencimiento;
						}
					}
					break;

				case 2:
					stmt = getConnection().createStatement();
					query = "SELECT f_prim_vto, f_seg_vto FROM vencimientos_int WHERE n_transac = "
							+ transaccion;
					rs = stmt.executeQuery(query);
					salida = xf_tope;
					if (!rs.next()) {
						salida = xf_tope;
					} else {
						f_prim_vto = rs.getDate("f_prim_vto");
						f_seg_vto = rs.getDate("f_seg_vto");
					}

					if (f_prim_vto.after(xf_tope)) {
						salida = f_prim_vto;
					} else {
						if (f_seg_vto.after(xf_tope)) {
							salida = f_seg_vto;
						}
					}

					break;

				case 3:
					salida = xf_tope;
					stmt = getConnection().createStatement();
					query = "SELECT n_estado_deuda FROM cta_cte WHERE n_transac = "
							+ transaccion
							+ " and c_estado_deuda = 'DM' AND i_capital < 0 and n_orden = 1";
					rs = stmt.executeQuery(query);
					if (!rs.next()) {
						salida = xf_tope;
					} else {
						xn_registro = rs.getInt("n_estado_deuda");

						stmt = getConnection().createStatement();
						query = "SELECT f_vto_operativo FROM registro_intimado WHERE n_registro = "
								+ xn_registro;
						rs = stmt.executeQuery(query);
						if (!rs.next()) {
							salida = xf_tope;
						} else {
							xf_vencimiento = rs.getDate("f_vto_operativo");
							stmt = getConnection().createStatement();
							query = "SELECT f_prorroga FROM ctrl_operativo";
							rs = stmt.executeQuery(query);
							if (!rs.next()) {
								if (xf_tope.before(xf_vencimiento)
										|| xf_tope.equals(xf_vencimiento)) {
									salida = xf_vencimiento;
								} else {
									salida = xf_tope;
								}
							} else {
								xf_prorroga = rs.getDate("f_prorroga");
								if (xf_vencimiento.after(xf_tope)
										|| xf_vencimiento.equals(xf_tope)) {
									salida = xf_vencimiento;
								} else {
									if (xf_prorroga.after(xf_tope)
											|| xf_prorroga.equals(xf_tope)) {
										salida = xf_vencimiento;
									}
								}
							}
						}
					}
					break;

				default:
					ln_orden = -1000;
					stmt = getConnection().createStatement();
					query = "SELECT n_orden FROM cta_cte WHERE n_transac = "
							+ transaccion
							+ " AND i_capital < 0 AND c_estado_deuda != 'AD'";
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						ln_orden = rs.getInt("n_orden");
					}

					stmt = getConnection().createStatement();
					query = "SELECT c_movimiento FROM cta_cte WHERE n_transac = "
							+ transaccion + " AND n_orden = " + ln_orden;
					rs = stmt.executeQuery(query);
					if (rs.next()) {
						lc_movim = rs.getInt("c_movimiento");
					}

					switch (lc_movim) {
					case 210:
						salida = xf_tope;
						stmt = getConnection().createStatement();
						query = "SELECT f_pago FROM cta_cte WHERE n_transac = "
								+ transaccion + " and c_estado_deuda = '"
								+ xc_actual + "'";
						rs = stmt.executeQuery(query);
						if (rs.next()) {
							xf_vencimiento = rs.getDate("f_pago");
						}
						stmt = getConnection().createStatement();
						query = "SELECT f_prorroga FROM ctrl_operativo";
						rs = stmt.executeQuery(query);

						if (!rs.next()) {
							if (xf_tope.before(xf_vencimiento)
									|| xf_tope.equals(xf_vencimiento)) {
								salida = xf_vencimiento;
							} else {
								salida = xf_tope;
							}
						} else {
							xf_prorroga = rs.getDate("f_prorroga");
							if (xf_vencimiento.after(xf_tope)
									|| xf_vencimiento.equals(xf_tope)) {
								salida = xf_vencimiento;
							} else {
								if (xf_prorroga.after(xf_tope)
										|| xf_prorroga.equals(xf_tope)) {
									salida = xf_vencimiento;
								}
							}
						}
						break;

					case 209:
						stmt = getConnection().createStatement();
						query = "SELECT f_prim_vto, f_seg_vto FROM vencimientos_int WHERE n_transac = "
								+ transaccion;
						rs = stmt.executeQuery(query);
						salida = xf_tope;
						if (!rs.next()) {
							salida = xf_tope;
						} else {
							f_prim_vto = rs.getDate("f_prim_vto");
							f_seg_vto = rs.getDate("f_seg_vto");

							if (f_prim_vto.after(xf_tope)) {
								salida = f_prim_vto;
							} else {
								if (f_seg_vto.after(xf_tope)) {
									salida = f_seg_vto;
								}
							}
						}

						break;

					default:
						stmt = getConnection().createStatement();
						query = "SELECT c_tasa, n_ano, n_cuota FROM transacciones WHERE n_transac = "
								+ transaccion;
						rs = stmt.executeQuery(query);

						if (rs.next()) {
							lc_tasa = rs.getInt("c_tasa");
							ln_ano = rs.getInt("n_ano");
							ln_cuota = rs.getInt("n_cuota");
						}

						stmt = getConnection().createStatement();
						query = "SELECT f_prim_vto, f_seg_vto FROM vencimientos WHERE c_tasa = "
								+ lc_tasa
								+ " AND n_ano= "
								+ ln_ano
								+ " AND n_cuota= " + ln_cuota;
						rs = stmt.executeQuery(query);
						if (rs.next()) {
							f_prim_vto = rs.getDate("f_prim_vto");
							f_seg_vto = rs.getDate("f_seg_vto");
						}

						if (f_prim_vto.after(xf_tope) || f_prim_vto.equals(xf_tope)) {
							salida = f_prim_vto;
						} else {
							if (f_seg_vto.after(xf_tope)
									|| f_seg_vto.equals(xf_tope)) {
								salida = f_seg_vto;
							} else {
								salida = xf_tope;
							}
						}
						break;
					}
				}
			}
			
			return salida;
		} catch (Exception e) {
			throw new ErrorEnBaseException("Error al recuperar las cuotas", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	private boolean vencio(Date fecha) {
		return new Date().after(fecha);
	}

	public float calculaRecargo(Date f, float c, float r, float m) throws ErrorEnBaseException {

		Date fecha1 = new Date();
		long hoy = 0;
		long otra = 0;
		float resultado = 0;
		hoy = fecha1.getTime();
		otra = hoy - f.getTime();
		otra = otra / 86400000;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query;

		try {
			query = "SELECT n_recargo FROM recargos WHERE c_sistema = 3 AND c_recargo = 1";

			stmt = this.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			float recargo = 0;

			while (rs.next()) {
				recargo = rs.getFloat(1);
			}

			resultado = r + (recargo * ((r + m + c) * otra));

			return resultado;
		} catch (Exception e) {
			throw new ErrorEnBaseException("Error al realizar el cálculo", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public PlanDePago recuperarDatosConsultaAbl(CuentaABL cuenta) throws ExcepcionControladaError {

		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String procedure = "{call sp_abl_deuda_ppc_cogmvl(?)}";
		String query = null;

		try {
			cs = this.getConnection().prepareCall(procedure);
			cs.setInt(1, cuenta.getNumero());
			cs.execute();

			PlanDePago plan = FactoryPlanDePago.generarInstanciaVacia();
			plan.setCuenta(cuenta);
			TotalCuota total = FactoryTotalCuota.generarIntanciaVacia();
			List<CuotaAVencer> cuotas = new ArrayList<CuotaAVencer>();
			EstadoCuotaAVencer estado = null;
			query = " select * from tmp_resultado";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				estado = FactoryEstadoCuotaAVencer.generarInstancia(rs
						.getInt(1));
			}

			query = " select * from tmp_deuda_ppc";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				cuotas.add(FactoryCuotaAVencer.generarIntanciaCompleta(
						rs.getString(2), rs.getInt(3) + "-" + rs.getInt(4),
						rs.getFloat(6), rs.getFloat(8), rs.getFloat(7),
						rs.getFloat(9)));
			}

			query = "select sum(i_capital),sum(i_recargo),sum(i_multa),sum(i_total) from tmp_deuda_ppc";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				total.setCapital(rs.getFloat(1));
				total.setMulta(rs.getFloat(3));
				total.setRecargo(rs.getFloat(2));
				total.setTotal(rs.getFloat(4));
			}

			plan.setListaCuotasAVencer(cuotas);
			plan.setTotalCuotasAVencer(total);
			plan.setEstadoCuotaAVencer(estado);

			return plan;
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public PlanDePago recuperarDatosConsultaVehiculos(CuentaVehiculos cuenta)
			throws ExcepcionControladaError {

		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String procedure = "{call sp_veh_det_reaj_cogmvl(?)}";
		String query = null;

		try {
			cs = this.getConnection().prepareCall(procedure);
			cs.setString(1, cuenta.getDominio());
			cs.execute();

			PlanDePago plan = FactoryPlanDePago.generarInstanciaVacia();
			plan.setCuenta(cuenta);
			TotalCuota total = FactoryTotalCuota.generarIntanciaVacia();
			List<CuotaAVencer> cuotas = new ArrayList<CuotaAVencer>();
			EstadoCuotaAVencer estado = null;
			query = " select * from tmp_resultado";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				estado = FactoryEstadoCuotaAVencer.generarInstancia(rs
						.getInt(1));
			}

			query = " select * from tmp_pat_det_reaj";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				cuotas.add(FactoryCuotaAVencer.generarIntanciaCompleta(
						rs.getString(1), rs.getInt(2) + "-" + rs.getInt(3),
						rs.getFloat(5), rs.getFloat(7), rs.getFloat(6),
						rs.getFloat(8)));
			}

			query = "select sum(i_capital),sum(i_recargo),sum(i_multa),sum(i_total) from tmp_pat_det_reaj";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				total.setCapital(rs.getFloat(1));
				total.setMulta(rs.getFloat(3));
				total.setRecargo(rs.getFloat(2));
				total.setTotal(rs.getFloat(4));
			}

			plan.setListaCuotasAVencer(cuotas);
			plan.setTotalCuotasAVencer(total);
			plan.setEstadoCuotaAVencer(estado);

			return plan;
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public PlanDePago recuperarDatosConsultaRodados(CuentaRodados cuenta)
			throws ExcepcionControladaError {

		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String procedure = "{call sp_pat_det_reaj_cogmvl(?)}";
		String query = null;

		try {
			cs = this.getConnection().prepareCall(procedure);
			cs.setString(1, cuenta.getDominio());
			cs.execute();

			PlanDePago plan = FactoryPlanDePago.generarInstanciaVacia();
			plan.setCuenta(cuenta);
			TotalCuota total = FactoryTotalCuota.generarIntanciaVacia();
			List<CuotaAVencer> cuotas = new ArrayList<CuotaAVencer>();
			EstadoCuotaAVencer estado = null;
			query = " select * from tmp_resultado";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				estado = FactoryEstadoCuotaAVencer.generarInstancia(rs
						.getInt(1));
			}

			query = " select * from tmp_pat_det_reaj";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				cuotas.add(FactoryCuotaAVencer.generarIntanciaCompleta(
						rs.getString(1), rs.getInt(2) + "-" + rs.getInt(3),
						rs.getFloat(5), rs.getFloat(7), rs.getFloat(6),
						rs.getFloat(8)));
			}

			query = "select sum(i_capital),sum(i_recargo),sum(i_multa),sum(i_total) from tmp_pat_det_reaj";
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				total.setCapital(rs.getFloat(1));
				total.setMulta(rs.getFloat(3));
				total.setRecargo(rs.getFloat(2));
				total.setTotal(rs.getFloat(4));
			}

			plan.setListaCuotasAVencer(cuotas);
			plan.setTotalCuotasAVencer(total);
			plan.setEstadoCuotaAVencer(estado);

			return plan;
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
	}	
	
	public Integer generarCarrito(Integer numeroDeDocumento, Integer tipoDocumento, List<CuotaPlanDePago> cuotaAPagars,
								  PlanDePagoAPagar planDePagoAPagar) throws ExcepcionControladaError {

		CallableStatement cs = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int n_carrito = 0;
		int ln_orden = 1;
		String query = "execute procedure sp_genera_carrito_ppc(?,?)";
		
		try {
			cs = this.getConnection().prepareCall(query);
			cs.setInt(1, tipoDocumento);
			cs.setInt(2, numeroDeDocumento);

			cs.execute();

			query = "select * from tmp_carrito";

			stmt = this.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			if (rs.next()) {
				n_carrito = rs.getInt(1);
			} else {
				throw new ErrorEnBaseException("Error generando el pago", null);
			}

			for (CuotaPlanDePago cuotaPlanDePago : cuotaAPagars) {

				query = " INSERT INTO web_recau:carrito_det_ppc VALUES("
						+ n_carrito + ",'" + planDePagoAPagar.getNroPlan() + "-"
						+ cuotaPlanDePago.getCuotaPlan() + "','"
						+ planDePagoAPagar.getNroPlan().toString() + "','"
						+ cuotaPlanDePago.getCuotaPlan() + "'," + ln_orden + ",'"
						+ cuotaPlanDePago.getCapital() + "','"
						+ cuotaPlanDePago.getRecargo() + "','"
						+ cuotaPlanDePago.getMulta() + "','"
						+ cuotaPlanDePago.getInteres() + "', NULL)";
				stmt = this.getConnection().prepareStatement(query);
				stmt.executeUpdate();
				ln_orden++;
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al recuperar las cuotas", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return n_carrito;
	}
}