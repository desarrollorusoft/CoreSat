package ar.com.cognisys.sat.core.modelo.dao.deuda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuota;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryTotalCuota;

public abstract class DaoDeuda<D extends DeudaAdapter> extends Dao {

	public DaoDeuda(Connection connection) {
		super(connection);
	}

	public D recuperarCuotas(String cuenta) throws ExcepcionControladaError {
		this.ejecutarSPRecuperoDeuda(cuenta);
		return this.generarDeuda(cuenta);
	}
	
	private void ejecutarSPRecuperoDeuda(String cuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( this.getSPRecuperoDeuda() );
			this.cargarParametrosSPRecuperoDeuda( cuenta, cs );
			
			cs.execute();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(cs);
		}
	}
	
	protected abstract D generarDeuda(String cuenta) throws ExcepcionControladaError;

	protected List<Cuota> recuperarAVencer() throws ExcepcionControladaError {
		return this.recuperarListaCuotas( this.getSQLRecuperarAVencer() );
	}

	protected List<Cuota> recuperarVencidas() throws ExcepcionControladaError {
		return this.recuperarListaCuotas( this.getSQLRecuperarVencidas() );
	}

	protected TotalCuota recuperarTotalAVencer() throws ExcepcionControladaError {
		return this.recuperarTotal( this.getSQLRecuperarTotalAVencer() );
	}

	protected TotalCuota recuperarTotalVencidas() throws ExcepcionControladaError {
		return this.recuperarTotal( this.getSQLRecuperarTotalVencidas() );
	}

	protected TotalCuota recuperarTotalGeneral() throws ExcepcionControladaError {
		return this.recuperarTotal( this.getSQLRecuperarTotalGeneral() );
	}

	protected boolean tieneDeudaEnLegales(String cuenta) throws ExcepcionControladaError {
		this.ejecutarSPRecuperoDeudaLegales(cuenta);
		return this.recuperarDeudaLegales();
	}
	
	private List<Cuota> recuperarListaCuotas(String query) throws ExcepcionControladaError  {
		
		List<Cuota> listaCuotas = new ArrayList<Cuota>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listaCuotas.add( FactoryCuota.generarIntanciaCompleta((rs.getString("d_tasa_abr") != null) ? rs.getString("d_tasa_abr").trim() : "", 
																	  (rs.getString("d_estado") != null) ? rs.getString("d_estado").trim() : "", 
																	  rs.getDate("f_pago"),
																	  rs.getString("n_ano") + "-" + rs.getString("n_cuota"),
																	  rs.getFloat("i_capital"), 
																	  rs.getFloat("i_multa"), 
																	  rs.getFloat("i_recargo"), 
																	  rs.getFloat("i_total"),
																      rs.getInt("transaccion"),
																      (rs.getString("d_estado") != null && rs.getString("d_estado").contains("MORA")),
																	  rs.getInt("n_estado"),
																	  rs.getInt("n_origen"),
																	  this.recperarTasa( rs.getString("d_tasa_abr") )));
			}
			this.agruparPeriodos(listaCuotas);
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return listaCuotas;
	}
	
	protected void agruparPeriodos(List<Cuota> listaCuotas) {
		Collections.sort(listaCuotas);

		for (int i = 0; i < listaCuotas.size()-1; i++) {
			if (listaCuotas.get(i).getPeriodo().equals(listaCuotas.get(i+1).getPeriodo())) {
				// Tienen el mismo periodo, asi que van juntas. Se vinculan las cuentas.
				listaCuotas.get(i).setCuotaAsociada( listaCuotas.get(i+1) );
				listaCuotas.get(i+1).setCuotaAsociada( listaCuotas.get(i) );
			}
		}
	}
	
	protected TotalCuota recuperarTotal(String query) throws ExcepcionControladaError {
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		TotalCuota total = null;
		
		try {
			ps = this.getConnection().prepareStatement(query);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				total = FactoryTotalCuota.generarIntanciaCompleta(rs.getFloat("i_capital"), 
																  rs.getFloat("i_recargo"), 
																  rs.getFloat("i_multa"), 
																  rs.getFloat("i_total"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return total;
	}
	
	private void ejecutarSPRecuperoDeudaLegales(String cuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( this.getSPRecuperarDeudaLegales() );
			this.cargarParametrosSPRecuperoDeudaLegales(cuenta, cs);
			cs.execute();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(cs);
		}
	}

	protected abstract void cargarParametrosSPRecuperoDeudaLegales(String cuenta, CallableStatement cs) throws SQLException;

	private boolean recuperarDeudaLegales() throws ExcepcionControladaError {
		
		boolean tieneDeudaLegales = false;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperarDeudaLegales() );
			rs = ps.executeQuery();
			
			if (rs.next()) {
				tieneDeudaLegales = rs.getInt(1) == 1;
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return tieneDeudaLegales;
	}

	protected abstract Integer recperarTasa(String tasa);

	protected abstract void cargarParametrosSPRecuperoDeuda(String cuenta, CallableStatement cs) throws SQLException;

	public TotalCuota recuperarTotalCancelacionDeuda(String cuenta, Date fecha) throws ExcepcionControladaError {
		this.ejecutarProcedureRecuperacionTotalCancelacion(cuenta, fecha);
		return this.recuperarTotal( this.getSQLRecuperarTotalCancelacion() );
	}

	private void ejecutarProcedureRecuperacionTotalCancelacion(String cuenta, Date fecha) throws ExcepcionControladaError {
		CallableStatement cs = null;
		try {
			cs = this.getConnection().prepareCall( this.getSPRecuperarTotalCancelacion() );
			cs.setDate(4, new java.sql.Date(fecha.getTime()));
			this.cargarParametrosSPRecuperoTotalCancelacion(cs, cuenta);
			cs.execute();
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error SP - " + this.getSPRecuperarTotalCancelacion(), e);
		} finally {
			super.cerrarRecursos(cs);
		}
	}

	protected abstract void cargarParametrosSPRecuperoTotalCancelacion(CallableStatement cs, String cuenta) throws SQLException;

	protected abstract String getSPRecuperoDeuda();
	
	protected abstract String getSQLRecuperarAVencer();
	
	protected abstract String getSQLRecuperarVencidas();
	
	protected abstract String getSQLRecuperarTotalAVencer();
	
	protected abstract String getSQLRecuperarTotalVencidas();
	
	protected abstract String getSQLRecuperarTotalGeneral();
	
	protected abstract String getSPRecuperarDeudaLegales();
	
	protected abstract String getSQLRecuperarDeudaLegales();

	protected abstract String getSPRecuperarTotalCancelacion();
	
	private String getSQLRecuperarTotalCancelacion() {
		return "Select Sum(I_Capital) as i_capital, \n "
			 + "       Sum(I_Recargo) as i_recargo, \n "
			 + "       Sum(I_Multa) as i_multa, \n "
			 + "       Sum(I_Capital + I_Recargo + I_Multa) as i_total  \n "
			 + "From Tmp_Deuda_Pago ";
	}
}