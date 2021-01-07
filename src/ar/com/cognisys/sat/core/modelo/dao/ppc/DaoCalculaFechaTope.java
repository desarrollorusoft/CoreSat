package ar.com.cognisys.sat.core.modelo.dao.ppc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ar.com.cognisys.conexiones.recursos.AbstractDao;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.ppc.CodigoActualDTO;
import ar.com.cognisys.sat.core.modelo.ppc.Periodo;
import ar.com.cognisys.sat.core.modelo.ppc.TransaccionMultaDTO;
import ar.com.cognisys.sat.core.modelo.ppc.Vencimientos;

public class DaoCalculaFechaTope extends AbstractDao {

	private static final String RECUPERAR_COD_ACTUAL = 
			"SELECT c_estado_deuda FROM cta_cte WHERE n_transac = ? AND i_capital < 0  AND c_estado_deuda != 'AD'";
	private static final String RECUPERAR_PAGO = 
			"SELECT f_pago FROM cta_cte WHERE n_transac = ? and c_estado_deuda = ?";
	private static final String RECUPERAR_PRORROGA = 
			"SELECT f_prorroga FROM ctrl_operativo";
	private static final String RECUPERAR_VENCIMIENTOS = 
			"SELECT f_prim_vto, f_seg_vto FROM vencimientos_int WHERE n_transac = ?";
	private static final String RECUPERAR_NRO_ESTADO_DEUDA = 
			"SELECT n_estado_deuda FROM cta_cte WHERE n_transac = ? and c_estado_deuda = 'DM' AND i_capital < 0 and n_orden = 1";
	private static final String RECUPERAR_VENCIMIENTO_OPERATIVO = 
			"SELECT f_vto_operativo FROM registro_intimado WHERE n_registro = ?";
	private static final String RECUPERAR_NRO_ORDEN = 
			"SELECT n_orden FROM cta_cte WHERE n_transac = ? AND i_capital < 0 AND c_estado_deuda != 'AD'";
	private static final String RECUPERAR_COD_MOVIMIENTO = 
			 "SELECT c_movimiento FROM cta_cte WHERE n_transac = ? AND n_orden = ?";
	private static final String RECUPERAR_PERIODO = 
			"SELECT c_tasa, n_ano, n_cuota FROM transacciones WHERE n_transac = ?";
	private static final String RECUPERAR_VENCIMIENTOS_PERIODO = 
			"SELECT f_prim_vto, f_seg_vto FROM vencimientos WHERE c_tasa = ? AND n_ano = ? AND n_cuota= ?";

	public DaoCalculaFechaTope(Connection connection) {
		super( connection );
	}

	public Date calcular(TransaccionMultaDTO transaccion, Date ahora) throws ExcepcionControladaError {

		if ( !transaccion.isSistema( 2 ) )
			return ahora;

		CodigoActualDTO codigo = this.codigo( transaccion );

		Date salida = ahora;
		switch ( codigo.getOp() ) {
			case 1:
				Date pago = this.pago( transaccion, codigo );
				Date prorroga = this.prorroga();
				
				if ( prorroga == null ) {
					salida = ahora.before( pago ) || ahora.equals( pago ) ? pago : ahora;
					prorroga = new Date();
				}
				
				if ( pago.after( ahora ) || pago.equals( ahora ) || prorroga.before( ahora ) || prorroga.equals( ahora ) )
					salida = pago;
				
				return salida;
			case 2:
				Vencimientos vencimientos = this.vencimientos( transaccion );
				
				if ( vencimientos.getPrimerVencimiento().after( ahora ) )
					salida = vencimientos.getPrimerVencimiento();
				else if ( vencimientos.getSegundoVencimiento().after( ahora ) )
					salida = vencimientos.getSegundoVencimiento();
				
				return salida;
			case 3:
				Integer nroEstadoDeuda = this.nroEstadoDeuda( transaccion );
				
				if ( nroEstadoDeuda == null )
					return salida;
				
				Date vencimientoOperativo = this.vencimientoOperativo( nroEstadoDeuda );
				
				if ( vencimientoOperativo == null )
					return salida;
				
				Date prorrogaOperativa = this.prorroga();
				
				if ( prorrogaOperativa == null ) {
					salida = ahora.before( vencimientoOperativo ) || ahora.equals( vencimientoOperativo ) ? vencimientoOperativo : ahora;
					prorrogaOperativa = new Date();
				}
				
				if ( vencimientoOperativo.after( ahora ) || vencimientoOperativo.equals( ahora ) || prorrogaOperativa.after( ahora ) || prorrogaOperativa.equals( ahora ) )
					salida = vencimientoOperativo;
				
				return salida;
			default:
				
				int nroOrden = this.nroOrden( transaccion );
				
				int codMovimiento = this.codMovimiento( nroOrden, transaccion );
				switch( codMovimiento ) {
					case 210:
						Date pagoMov = this.pago( transaccion, codigo );
						Date prorrogaMov = this.prorroga();
						
						if ( prorrogaMov == null ) {
							salida = ahora.before( pagoMov ) || ahora.equals( pagoMov ) ? pagoMov : ahora;
							prorrogaMov = new Date();
						}
						
						if ( pagoMov.after( ahora ) || pagoMov.equals( ahora ) || prorrogaMov.after( ahora ) || prorrogaMov.equals( ahora ) )
							salida = pagoMov;
						
						return salida;
					case 209:
						Vencimientos vencimientosMov = this.vencimientos( transaccion );

						if ( vencimientosMov.getPrimerVencimiento().after( ahora ) )
							salida = vencimientosMov.getPrimerVencimiento();
						else if ( vencimientosMov.getSegundoVencimiento().after( ahora ) )
							salida = vencimientosMov.getSegundoVencimiento();
						
						return salida;
					default:
						
						Periodo periodo = this.periodo( transaccion );
						
						Vencimientos vencimientosDef = this.vencimientos( periodo );
						
						if ( vencimientosDef.getPrimerVencimiento().after( ahora ) )
							salida = vencimientosDef.getPrimerVencimiento();
						else if ( vencimientosDef.getSegundoVencimiento().after( ahora ) )
							salida = vencimientosDef.getSegundoVencimiento();
						
						return salida;
				}
		}

	}

	private Vencimientos vencimientos(Periodo periodo) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_VENCIMIENTOS_PERIODO );
			st.setInt( 1, periodo.getCodTasa() );
			st.setInt( 2, periodo.getAnio() );
			st.setInt( 3, periodo.getCuota() );
			rs = st.executeQuery();

			return rs.next() ? new Vencimientos( rs.getDate( "f_prim_vto" ), rs.getDate( "f_seg_vto" ) ) : new Vencimientos( new Date(), new Date() );
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar los vencimientos para el periodo " + periodo.getCuota() + " " + periodo.getAnio(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private Periodo periodo(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_PERIODO );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();

			return rs.next() ? new Periodo( rs.getInt( "c_tasa" ), rs.getInt( "n_ano" ), rs.getInt( "n_cuota" ) )  : new Periodo();
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el periodo para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private int nroOrden(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_NRO_ORDEN );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();

			return rs.next() ? rs.getInt( "n_orden" ) : -1000;
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el nro de orden para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

	private int codMovimiento(int nroOrden, TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_COD_MOVIMIENTO );
			st.setLong( 1, transaccion.getNumero() );
			st.setInt( 2, nroOrden );
			rs = st.executeQuery();

			return rs.next() ? rs.getInt( "c_movimiento" ) : 0;
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el movimiento para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

	private Date vencimientoOperativo(int nroEstadoDeuda) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_VENCIMIENTO_OPERATIVO );
			st.setInt( 1, nroEstadoDeuda );
			rs = st.executeQuery();

			return rs.next() ? rs.getDate( "f_vto_operativo" ) : null;
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el vencimiento operativo para el nro de estado deuda " + nroEstadoDeuda, e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

	private Integer nroEstadoDeuda(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_NRO_ESTADO_DEUDA );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();

			return rs.next() ? rs.getInt( "n_estado_deuda" ) : null;
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el nro de estado deuda para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private Vencimientos vencimientos(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_VENCIMIENTOS );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();

			return rs.next() ? new Vencimientos( rs.getDate( "f_prim_vto" ), rs.getDate( "f_seg_vto" ) ) : new Vencimientos( new Date(), new Date() );
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar los vencimientos para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

	private Date prorroga() throws ExcepcionControladaError {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_PRORROGA );
			rs = st.executeQuery();

			return rs.next() ? rs.getDate( "f_prorroga" ) : null;
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar la fecha de prorroga", e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
		
	}

	private Date pago(TransaccionMultaDTO transaccion, CodigoActualDTO codigo) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_PAGO );
			st.setLong( 1, transaccion.getNumero() );
			st.setString( 2, codigo.getCodActual() );
			rs = st.executeQuery();

			return rs.next() ? rs.getDate( "f_pago" ) : new Date();
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el vencimiento para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}

	}

	private CodigoActualDTO codigo(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {
		if ( transaccion.isActual( "PP" ) || transaccion.isActual( "AC" ) || transaccion.isActual( "PV" ) )
			return this.recuperarCodActual( transaccion );

		return new CodigoActualDTO( transaccion.getCodActual() );
	}

	private CodigoActualDTO recuperarCodActual(TransaccionMultaDTO transaccion) throws ExcepcionControladaError {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = this.getConnection().prepareStatement( RECUPERAR_COD_ACTUAL );
			st.setLong( 1, transaccion.getNumero() );
			rs = st.executeQuery();

			if ( rs.next() ) {
				String codActual = rs.getString( "c_estado_deuda" );
				if ( codActual.equals( "DO" ) )
					return new CodigoActualDTO( 1, codActual );
				else if ( codActual.equals( "DI" ) )
					return new CodigoActualDTO( 2, codActual );
				else if ( codActual.equals( "DM" ) || codActual.equals( "DL" ) )
					return new CodigoActualDTO( 3, codActual );
			}

			return new CodigoActualDTO( transaccion.getCodActual() );
		} catch ( SQLException e ) {
			throw new ExcepcionControladaError( "No es posible recuperar el codigo actual para la transaccion " + transaccion.getNumero(), e );
		} finally {
			this.cerrarRecursos( rs, st );
		}
	}

}
