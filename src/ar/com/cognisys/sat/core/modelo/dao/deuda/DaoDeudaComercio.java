package ar.com.cognisys.sat.core.modelo.dao.deuda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.contenedor.ContenedorTasasComercio;
import ar.com.cognisys.sat.core.modelo.comun.Tasa;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuota;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudaAdapter;

public class DaoDeudaComercio extends DaoDeuda<DeudaAdapter> {

	private static final String SP_RECUPERO_DEUDA = "{call recaudaciones:sp_ddjj_muestra_deuda_cog( ? )}";
	
	private static final String SQL_RECUPERO_A_VENCER = "select n_transac, c_tasa, n_ano, n_cuota, i_capital, i_recargo, i_multa, (i_capital+i_recargo+i_multa) as i_total, f_vto, c_actual\n" +
														"from ddjj_datos_deuda\n" +
														"where c_cuenta = :cuenta \n" +
														"and vencido = 'N'\n" +
														"AND c_actual != 'DL'\n" +
														"ORDER BY c_tasa, n_ano, n_cuota";

	private static final String SQL_RECUPERO_VENCIDA = "select n_transac, c_tasa, n_ano, n_cuota, i_capital, i_recargo, i_multa, (i_capital+i_recargo+i_multa) as i_total, f_vto, c_actual\n" +
														"from ddjj_datos_deuda\n" +
														"where c_cuenta = :cuenta \n" +
														"and vencido = 'S'\n" +
														"AND c_actual != 'DL'\n" +
														"ORDER BY c_tasa, n_ano, n_cuota";

	private static final String SQL_RECUPERO_TOTAL_A_VENCER = "select NVL(sum(i_capital),0) as i_capital, NVL(sum(i_recargo),0) as i_recargo, NVL(sum(i_multa),0) as i_multa, NVL((sum(i_capital)+sum(i_recargo)+sum(i_multa)),0) as i_total\n" +
															"from ddjj_datos_deuda\n" +
															"where c_cuenta = :cuenta \n" +
															"and vencido = 'N'" +
															"AND c_actual != 'DL'";

	private static final String SQL_RECUPERO_TOTAL_VENCIDA = "select NVL(sum(i_capital),0) as i_capital, NVL(sum(i_recargo),0) as i_recargo, NVL(sum(i_multa),0) as i_multa, NVL((sum(i_capital)+sum(i_recargo)+sum(i_multa)),0) as i_total\n" +
															"from ddjj_datos_deuda\n" +
															"where c_cuenta = :cuenta \n" +
															"and vencido = 'S'\n" +
															"AND c_actual != 'DL'";

	private static final String SQL_RECUPERO_TOTAL_GENERAL = "select NVL(sum(i_capital),0) as i_capital, NVL(sum(i_recargo),0) as i_recargo, NVL(sum(i_multa),0) as i_multa, NVL((sum(i_capital)+sum(i_recargo)+sum(i_multa)),0) as i_total\n" +
															"from ddjj_datos_deuda\n" +
															"where c_cuenta = :cuenta \n" +
															"AND c_actual != 'DL'";

	private static final String SP_RECUPERO_DEUDA_LEGALES = "";
	
	private static final String SQL_RECUPERO_DEUDA_LEGALES = "select * from ddjj_datos_deuda where c_cuenta = :cuenta and c_actual = 'DL'";

	private static final String SP_RECUPERAR_TOTAL_CANCELACION = "{call recaudaciones:sp_ddjj_muestra_deuda_cog( ? )}";

	private static final String SQL_RECUPERO_CANCELACION = "select NVL(sum(i_capital),0) as i_capital, NVL(sum(i_recargo_desc),0) as i_recargo, NVL(sum(i_multa_desc),0) as i_multa, NVL((sum(i_capital)+sum(i_recargo_desc)+sum(i_multa_desc)),0) as i_total\n" +
															"from ddjj_datos_deuda\n" +
															"where c_cuenta = :cuenta \n" +
															"and vencido = 'S'\n" +
															"AND c_actual != 'DL'";

	public DaoDeudaComercio(Connection connection) {
		super(connection);
	}

	@Override
	protected void cargarParametrosSPRecuperoDeuda(String padron, CallableStatement cs) throws SQLException {
		cs.setString(1, padron);
	}

	@Override
	protected DeudaAdapter generarDeuda(String cuenta) throws ExcepcionControladaError {
		return FactoryDeudaAdapter.generar(this.recuperarListaCuotas( this.getSQLRecuperarAVencer().replace(":cuenta", cuenta) ),
										   this.recuperarListaCuotas( this.getSQLRecuperarVencidas().replace(":cuenta", cuenta) ),
										   super.recuperarTotal( this.getSQLRecuperarTotalAVencer().replace(":cuenta", cuenta) ),
										   super.recuperarTotal( this.getSQLRecuperarTotalVencidas().replace(":cuenta", cuenta) ),
										   super.recuperarTotal( this.getSQLRecuperarTotalGeneral().replace(":cuenta", cuenta) ),
										   this.tieneDeudaEnLegales(cuenta));
	}

	private List<Cuota> recuperarListaCuotas(String query) throws ExcepcionControladaError  {

		List<Cuota> listaCuotas = new ArrayList<Cuota>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Tasa tasa = ContenedorTasasComercio.getInstancia().recuperar( rs.getInt("c_tasa") );

				listaCuotas.add( FactoryCuota.generarIntanciaCompleta(tasa.getNombre(),
																	rs.getString("c_actual"),
																	rs.getDate("f_vto"),
																	rs.getString("n_ano") + "-" + rs.getString("n_cuota"),
																	rs.getFloat("i_capital"),
																	rs.getFloat("i_multa"),
																	rs.getFloat("i_recargo"),
																	rs.getFloat("i_total"),
																	rs.getInt("n_transac"),
																	rs.getString("c_actual").contains("DM"),
																	rs.getInt("n_estado"),
																	rs.getInt("n_origen"),
																	tasa.getCodigo()));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return listaCuotas;
	}

	@Override
	protected Integer recperarTasa(String tasa) {
		return 221;
	}
	
	@Override
	protected void cargarParametrosSPRecuperoDeudaLegales(String cuenta, CallableStatement cs) throws SQLException {
		cs.setString(1, cuenta);
	}
	
	@Override
	protected boolean tieneDeudaEnLegales(String cuenta) throws ExcepcionControladaError {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperarDeudaLegales().replace(":cuenta", cuenta) );
			rs = ps.executeQuery();

			return ( rs.next() );
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	public TotalCuota recuperarTotalCancelacionDeuda(String cuenta, java.util.Date fecha) throws ExcepcionControladaError {
		this.ejecutarProcedureRecuperacionTotalCancelacion(cuenta);
		return super.recuperarTotal( SQL_RECUPERO_CANCELACION.replace(":cuenta", cuenta) );
	}

	private void ejecutarProcedureRecuperacionTotalCancelacion(String cuenta) throws ExcepcionControladaError {
		CallableStatement cs = null;
		try {
			cs = this.getConnection().prepareCall( this.getSPRecuperarTotalCancelacion() );
			this.cargarParametrosSPRecuperoTotalCancelacion(cs, cuenta);
			cs.execute();
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error SP - " + this.getSPRecuperarTotalCancelacion(), e);
		} finally {
			super.cerrarRecursos(cs);
		}
	}

	@Override
	protected void cargarParametrosSPRecuperoTotalCancelacion(CallableStatement cs, String cuenta) throws SQLException {
		cs.setInt(1, Integer.parseInt(cuenta));
	}

	@Override
	protected String getSPRecuperoDeuda() {
		return SP_RECUPERO_DEUDA;
	}

	@Override
	protected String getSQLRecuperarAVencer() {
		return SQL_RECUPERO_A_VENCER;
	}

	@Override
	protected String getSQLRecuperarVencidas() {
		return SQL_RECUPERO_VENCIDA;
	}

	@Override
	protected String getSQLRecuperarTotalAVencer() {
		return SQL_RECUPERO_TOTAL_A_VENCER;
	}

	@Override
	protected String getSQLRecuperarTotalVencidas() {
		return SQL_RECUPERO_TOTAL_VENCIDA;
	}

	@Override
	protected String getSQLRecuperarTotalGeneral() {
		return SQL_RECUPERO_TOTAL_GENERAL;
	}

	@Override
	protected String getSPRecuperarDeudaLegales() {
		return SP_RECUPERO_DEUDA_LEGALES;
	}
	
	@Override
	protected String getSQLRecuperarDeudaLegales() {
		return SQL_RECUPERO_DEUDA_LEGALES;
	}
	
	@Override
	protected String getSPRecuperarTotalCancelacion() {
		return SP_RECUPERAR_TOTAL_CANCELACION;
	}
}