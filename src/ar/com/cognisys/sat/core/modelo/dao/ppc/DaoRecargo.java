package ar.com.cognisys.sat.core.modelo.dao.ppc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ar.com.cognisys.conexiones.recursos.AbstractDao;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.Parametros;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.ppc.RecargoDTO;
import ar.com.cognisys.sat.core.modelo.ppc.TransaccionMultaDTO;
import ar.com.cognisys.sat.core.modelo.ppc.codigo.CodigoPlanPagoDTO;

public class DaoRecargo extends AbstractDao {

	private static final String RECUPERAR_MOVIMIENTO = 
			"Select c_movimiento from cta_cte where n_transac = ? and i_capital < 0 and c_estado_deuda != 'AD'";
	private static final String RECUPERAR_RECARGO_MULTA = 
			"Select s_recargo, s_multa from tas_sist where c_tasa = ? and c_sistema = ?";
	private static final String ES_TRANSACCION_139 = 
			"select * from transac_cod_139 where n_transac = ?";
	private static final String RECUPERAR_RECARGO = 
			"select n_recargo from recargos where c_sistema = ? and c_recargo = ? and today between f_vigencia and f_vencimiento";
	private static final String RECUPERAR_FECHAS_VENCIMIENTO = 
			"select f_prim_vto, f_seg_vto from vencimientos_int where n_transac = ?";
	private static final String RECUPERAR_ESTADO_DEUDA = 
			"select n_estado_deuda from cta_cte where n_transac = ? and c_estado_deuda = 'DP'";
	private static final String RECUPERAR_MULTA = 
			"select c_multa from ppc_transac where c_plan = 1 and n_plan = ? and n_transac = ?";
	private static final String RECUPERAR_MULTA_POR_CODIGO = 
			"select n_multa from sist_multa where c_sistema = ?";
	private static final String RECUPERAR_TOTAL_MULTA = null;
	private RecargoDTO recargo;
	private Parametros parametro;
	
	public DaoRecargo(Connection connection) {
		super( connection );
		this.recargo = new RecargoDTO();
		this.parametro = new Parametros();
	}

	public Parametros calcular(TransaccionMultaDTO transaccion, CodigoPlanPagoDTO codigo, Date vencimiento, Date ahora, Integer nroPlan, float capital) throws ExcepcionControladaError {
		
		this.recargo.setFechaCorrespondiente( ahora );
		
		this.agregarMovimiento( transaccion );
		if ( this.recargo.isMovimiento( 130 ) )
			this.recargo.cargarMovimiento130();
		
		this.agregarRecargoMulta( transaccion, codigo );
		
		if ( this.recargo.isMovimiento( 141 ) || this.recargo.isMovimiento( 800 ) || this.recargo.isMovimiento( 700 ) )
			this.recargo.cargarMovimiento141();
		
		if ( this.recargo.hayLsRecargo() && !vencimiento.after( ahora ) ) {
			this.agregarLnRecargo( codigo );
			
			if ( transaccion.isActual( "DI" ) && transaccion.isSistema( 2 ) )
				this.agregarFechasVencimiento( transaccion, ahora );
			
			if ( this.recargo.isCod130() )
				this.recargo.cargarRecargo130( transaccion, vencimiento, capital );
		}
		
		if ( this.recargo.hayLsMulta() ) {
			if ( transaccion.isActual( "DP" ) || transaccion.isActual( "PP" ) )
				this.agregarLnPlan( transaccion );
			
			if ( this.recargo.hayLnPlan() ) {
				this.agregarLnMulta( transaccion );
				
				if ( this.recargo.hayLnMulta() ) {
					this.agregarTotalMulta( transaccion );
					
					if ( this.recargo.isCod130() )
						this.agregarMultaCod130( transaccion, nroPlan );
					
					this.agregarTransaccion139( transaccion );
					
					if ( this.recargo.isCod139() )
						this.agregarMultaCod139( transaccion, nroPlan );
					
					return this.parametro;
				} else {
					this.agregarLnMulta( codigo );
				}
			} else {
				this.agregarLnMulta( codigo );
			}
		}
		
		if ( this.recargo.isCod130() )
			this.agregarMultaCod130( transaccion, nroPlan );
		
		this.agregarTransaccion139( transaccion );
		
		if ( this.recargo.isCod139() )
			this.agregarMultaCod139( transaccion, nroPlan );
		
		this.parametro.setRecargo( this.recargo.getLiRecargo() );
		this.parametro.setMulta( this.recargo.getLiTotMul() );
		
		return this.parametro;
	}

	private void agregarMultaCod139(TransaccionMultaDTO transaccion, Integer nroPlan) throws ExcepcionControladaError {
		try {

			DaoCalculaCod dao = new DaoCalculaCod139( this.getConnection() );
			this.parametro = dao.calcular( transaccion.getNumero(), this.recargo.getLiRecargo(), this.recargo.getLiTotMul(), nroPlan );
			
			this.recargo.setLiTotMul( this.parametro.getSistMul() );
			this.recargo.setLiRecargo( this.parametro.getLcRecargo() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible calcular el codigo 139", e );
		}
	}

	private void agregarMultaCod130(TransaccionMultaDTO transaccion, Integer nroPlan) throws ExcepcionControladaError {
		try {
			DaoCalculaCod dao = new DaoCalculaCod130( this.getConnection() );
			this.parametro = dao.calcular( transaccion.getNumero(), this.recargo.getLiRecargo(), this.recargo.getLiTotMul(), nroPlan );
			
			this.recargo.setLiTotMul( this.parametro.getSistMul() );
			this.recargo.setLiRecargo( this.parametro.getLcRecargo() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "No es posible calcular el codigo 130", e );
		}
		
	}

	private void agregarTotalMulta(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_TOTAL_MULTA );
			st.setInt( 1, this.recargo.getLnPlan() );
			st.setLong( 2, transaccion.getNumero() );
			rs = st.executeQuery();
			
			if ( rs.next() ) {
				this.recargo.setLiTotMul( rs.getFloat( "tot_multa" ) );
				this.recargo.setLnMulta( rs.getFloat( "c_multa" ) );
			}
		
			if ( !this.recargo.hayLiTotMul() )
				this.recargo.setLnMulta( 0 );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el total la multa para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private void agregarLnMulta(CodigoPlanPagoDTO codigo) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_MULTA_POR_CODIGO );
			st.setInt( 1, codigo.getSistMul() );
			rs = st.executeQuery();
			
			if ( rs.next() )
				this.recargo.setLnMulta( rs.getInt( "n_multa" ) );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar la multa para el codigo " + codigo.getSistMul(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}	
		
	}

	private void agregarLnMulta(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_MULTA );
			st.setInt( 1, this.recargo.getLnPlan() );
			st.setLong( 2, transaccion.getNumero() );
			rs = st.executeQuery();
			
			if ( rs.next() )
				this.recargo.setLnMulta( rs.getInt( "c_multa" ) );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar la multa para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}	
		
	}

	private void agregarLnPlan(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_ESTADO_DEUDA );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();
			
			if ( rs.next() )
				this.recargo.setLnPlan( rs.getInt( "n_estado_deuda" ) );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el estado de deuda para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}	
		
	}

	private void agregarFechasVencimiento(TransaccionMultaDTO transaccion, Date ahora) throws ExcepcionControladaError {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_FECHAS_VENCIMIENTO );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();
			
			if ( rs.next() ) {
				this.recargo.setPrimerVencimiento( rs.getDate( "f_prim_vto" ) );
				this.recargo.setSegundoVencimiento( rs.getDate( "f_seg_vto" ) );

				this.recargo.actualizarFechaCorrespondiente();
			}
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar los vencimientos para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private void agregarLnRecargo(CodigoPlanPagoDTO codigo) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_RECARGO );
			st.setInt( 1, codigo.getSistRec() );
			st.setInt( 2, codigo.getLcRecargo() );
			rs = st.executeQuery();
			
			this.recargo.setLnRecargo( rs.next() ? rs.getFloat( "n_recargo" ) : 0 );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el recargo", e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

	private void agregarTransaccion139(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( ES_TRANSACCION_139 );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();
			
			this.recargo.setLsCod139( rs.next() ? 1 : 0 );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible averiguar si la transaccion " + transaccion.getNumero() + " es codigo 139", e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private void agregarRecargoMulta(TransaccionMultaDTO transaccion, CodigoPlanPagoDTO codigo) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_RECARGO_MULTA );
			st.setInt( 1, transaccion.getCodTasa() );
			st.setInt( 2, codigo.getSistRec() );
			rs = st.executeQuery();
			
			if ( rs.next() ) {
				this.recargo.setLsRecargo( rs.getInt( "s_recargo" ) );
				this.recargo.setLsMulta( rs.getInt( "s_multa" ) );
			}
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el recargo para la tasa " + transaccion.getCodTasa(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private void agregarMovimiento(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = this.getConnection().prepareStatement( RECUPERAR_MOVIMIENTO );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();
			
			if ( rs.next() )
				this.recargo.setCodMovimiento( rs.getInt( "c_movimiento" ) );
			
		} catch (SQLException e) {
			throw new ExcepcionControladaError( "No es posible recuperar el movimiento para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

}
