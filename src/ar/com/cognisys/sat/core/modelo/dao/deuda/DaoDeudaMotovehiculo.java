package ar.com.cognisys.sat.core.modelo.dao.deuda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudaAdapter;

public class DaoDeudaMotovehiculo extends DaoDeuda<DeudaAdapter> {

	private static final String SP_RECUPERO_DEUDA = "{call sp_rod_carga_mov_cogmvl(?)}";
	
	private static final String SQL_RECUPERO_A_VENCER = "Select *, cc_n_transac as transaccion, (NVL(i_capital, 0.0) + NVL(i_recargo, 0.0) + NVL(i_multa, 0.0)) as i_total \n"
													  + "From tmp_rod_cons_deuda \n"
													  + "Where d_estado not like '%PLAN%' ";

	private static final String SQL_RECUPERO_VENCIDA = "Select *, cc_n_transac as transaccion, (NVL(i_capital, 0.0) + NVL(i_recargo, 0.0) + NVL(i_multa, 0.0)) as i_total \n"
													 + "From tmp_rod_cons_deudv \n"
													 + "Where d_estado not like '%PLAN%' ";

	private static final String SQL_RECUPERO_TOTAL_A_VENCER = "Select *, (NVL(i_capital, 0.0) + NVL(i_recargo, 0.0) + NVL(i_multa, 0.0)) as i_total  From tmp_rod_totales";

	private static final String SQL_RECUPERO_TOTAL_VENCIDA = "Select *, (NVL(i_capital, 0.0) + NVL(i_recargo, 0.0) + NVL(i_multa, 0.0)) as i_total  From tmp_rod_totalesv";

	private static final String SQL_RECUPERO_TOTAL_GENERAL = "Select *, (NVL(i_capital, 0.0) + NVL(i_recargo, 0.0) + NVL(i_multa, 0.0)) as i_total  From tmp_rod_total_gral";

	private static final String SP_RECUPERO_DEUDA_LEGALES = "{call sp_rod_carga_mov_cogmvl( ? )}";
	
	private static final String SQL_RECUPERO_DEUDA_LEGALES = "select * from tmp_resultado";

	private static final String SP_RECUPERAR_TOTAL_CANCELACION = "{call sp_pat_deuda_pago_cogmvl(?, ? , ? , ?)}";
	
	public DaoDeudaMotovehiculo(Connection connection) {
		super(connection);
	}

	@Override
	protected void cargarParametrosSPRecuperoDeuda(String dominio, CallableStatement cs) throws SQLException {
		cs.setString(1, dominio);
	}

	@Override
	protected DeudaAdapter generarDeuda(String cuenta) throws ExcepcionControladaError {
		return FactoryDeudaAdapter.generar(super.recuperarAVencer(), 
										   super.recuperarVencidas(), 
										   super.recuperarTotalAVencer(), 
										   super.recuperarTotalVencidas(), 
										   super.recuperarTotalGeneral(),
										   super.tieneDeudaEnLegales(cuenta));
	}
	
	@Override
	protected Integer recperarTasa(String tasa) {
		return 300;
	}
	
	@Override
	protected void cargarParametrosSPRecuperoDeudaLegales(String cuenta, CallableStatement cs) throws SQLException {
		cs.setString(1, cuenta);
	}
	
	@Override
	protected void cargarParametrosSPRecuperoTotalCancelacion(CallableStatement cs, String cuenta) throws SQLException {
		cs.setString(1, cuenta);
		cs.setInt(2, 1);
		cs.setInt(3, 0);
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