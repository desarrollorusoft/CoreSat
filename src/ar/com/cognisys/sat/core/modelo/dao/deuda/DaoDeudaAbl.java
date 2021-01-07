package ar.com.cognisys.sat.core.modelo.dao.deuda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudaAdapter;

public class DaoDeudaAbl extends DaoDeuda<DeudaAdapter> {

	private static final String SP_RECUPERO_DEUDA = "{call sp_abl_carga_deuda_cogmvl( 2 , 3 , ? )}";
	
	private static final String SQL_RECUPERO_A_VENCER = "Select *, f_vencimiento as f_pago, n_transac as transaccion From tmp_abl_consulta where d_estado not like '%PLAN%'";

	private static final String SQL_RECUPERO_VENCIDA = "Select *, f_vencimiento as f_pago, n_transac as transaccion From tmp_abl_consultav where d_estado not like '%PLAN%'";

	private static final String SQL_RECUPERO_TOTAL_A_VENCER = "Select * From tmp_abl_totales";

	private static final String SQL_RECUPERO_TOTAL_VENCIDA = "Select * From tmp_abl_totalesv";

	private static final String SQL_RECUPERO_TOTAL_GENERAL = "Select * From tmp_abl_total_gral";

	private static final String SP_RECUPERO_DEUDA_LEGALES = "{call sp_abl_deuda_ppc_cogmvl( ? )}";
	
	private static final String SQL_RECUPERO_DEUDA_LEGALES = "select * from tmp_resultado";

	private static final String SP_RECUPERAR_TOTAL_CANCELACION = "{call sp_abl_deuda_pago_cogmvl(?, ? , ? , ?)}";
	
	public DaoDeudaAbl(Connection connection) {
		super(connection);
	}

	@Override
	protected void cargarParametrosSPRecuperoDeuda(String cuenta, CallableStatement cs) throws SQLException {
		cs.setInt(1, new Integer(cuenta));
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
		return (tasa.contains("PROT.CIUDADANA")) ? 102 : 100;
	}
	
	@Override
	protected void cargarParametrosSPRecuperoDeudaLegales(String cuenta, CallableStatement cs) throws SQLException {
		cs.setInt(1, new Integer(cuenta));
	}
	
	@Override
	protected void cargarParametrosSPRecuperoTotalCancelacion(CallableStatement cs, String cuenta) throws SQLException {
		cs.setInt(1, Integer.parseInt(cuenta));
		cs.setInt(2, 1);
		cs.setInt(3, 1);
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