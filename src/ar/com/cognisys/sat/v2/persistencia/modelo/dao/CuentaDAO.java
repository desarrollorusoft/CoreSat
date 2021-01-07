package ar.com.cognisys.sat.v2.persistencia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.factory.FactoryCuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoCuentaPorCodigo;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoCuentasPorUsuario;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.factory.CuentaQueryFactory;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaDAO extends DAO {

	/**
	 * Query que recupera las cuentas asociadas al usuario.
	 * Cuentas No Recuperadas: CEMENTERIOS 
	 */
	private static final String RECUPERAR_CUENTAS_POR_USUARIO = 
			"select distinct \n" +
			"'ABL' as tipo_cuenta, \n" +
			"rel.cuenta as codigo_cuenta, \n" +
			"abl.c_cuenta as numero_cuenta, \n" +
			"rel.descripcion as alias, \n" +
			"trim( nvl( cal.d_calle, '' ) ) || ' ' || \n" +
			"decode ( trim ( nvl( abl.n_nro_pro, '' ) ), '', '', trim ( nvl( abl.n_nro_pro, '' ) ) || ' ' ) || \n" +
			"decode ( trim ( nvl( abl.c_piso_pro, '' ) ), '', '', trim ( nvl( abl.c_piso_pro, '' ) ) || ' ' ) || \n" +
			"decode ( trim ( nvl( abl.c_dpto_pro, '' ) ) , '', '', trim ( nvl( abl.c_dpto_pro, '' ) ) ) as descripcion, \n" +
			"nvl((select unique 1 from recaudaciones:boleta_electronica be where abl.c_cuenta = be.c_cuenta and be.c_sistema = 2),0) as be \n" +
			"from web_recau:usuario_cuentas_rel_cogmvl rel, abl abl, calles cal \n" +
			"where rel.id_usuario = ? \n" +
			"and rel.tipo_cuenta = 1 \n" +
			"and abl.c_baja = 0 \n" +
			"and abl.c_cuenta = rel.cuenta \n" +
			"and cal.c_calle = abl.c_calle_pro \n" +
			"union \n" +
			"select distinct\n" +
			"'COMERCIOS' as tipo_cuenta,\n" +
			"rel.cuenta as codigo_cuenta,\n" +
			"com.c_cuenta as numero_cuenta,\n" +
			"rel.descripcion as alias,\n" +
			"trim( nvl( com.d_razon_social, '' ) ) as descripcion, \n" +
			"nvl((select unique 1 from recaudaciones:boleta_electronica be where com.c_cuenta = be.c_cuenta and be.c_sistema = 1),0) as be \n" +
			"from web_recau:usuario_cuentas_rel_cogmvl rel, recaudaciones:comercios com\n" +
			"where rel.id_usuario = ? \n" +
			"and rel.tipo_cuenta = 2\n" +
			"and com.c_baja = 0\n" +
			"and com.n_cuit = rel.cuenta\n" +
			"union \n" +
			"select distinct \n" +
			"'VEHICULOS' as tipo_cuenta, \n" +
			"rel.cuenta as codigo_cuenta, \n" +
			"veh.c_cuenta as numero_cuenta, \n" +
			"rel.descripcion as alias, \n" +
			"'' as descripcion, \n" +
			"nvl((select unique 1 from recaudaciones:boleta_electronica be where veh.c_cuenta = be.c_cuenta and be.c_sistema = 31),0) as be \n" +
			"from web_recau:usuario_cuentas_rel_cogmvl rel, recaudaciones:vehiculos veh \n" +
			"where rel.id_usuario = ? \n" +
			"and rel.tipo_cuenta = 3 \n" +
			"and veh.c_baja = 0 \n" +
			"and ( trim( nvl( veh.c_dominio_mercosur, '' ) ) = rel.cuenta \n" +
			"or trim( nvl( veh.c_dominio_actual, '' ) ) = rel.cuenta \n" +
			"or trim( nvl( veh.c_dominio_original, '' ) ) = rel.cuenta ) \n" +
			"union \n" +
			"select distinct \n" +
			"'RODADOS' as tipo_cuenta, \n" +
			"rel.cuenta as codigo_cuenta, \n" +
			"rod.c_cuenta as numero_cuenta, \n" +
			"rel.descripcion as alias, \n" +
			"trim( nvl( rod.d_marca, '' ) ) || ' ' || decode( trim( nvl( rod.d_modelo, '' ) ), '', '', trim( nvl( rod.d_modelo, '' ) ) || ' ' ) || trim( nvl( rod.n_cilindrada, '' ) ) as descripcion, \n" +
			"nvl((select unique 1 from recaudaciones:boleta_electronica be where rod.c_cuenta = be.c_cuenta and be.c_sistema = 4),0) as be \n" +
			"from web_recau:usuario_cuentas_rel_cogmvl rel, recaudaciones:rodados rod \n" +
			"where rel.id_usuario = ? \n" +
			"and rel.tipo_cuenta = 4 \n" +
			"and rod.c_baja = 0 \n" +
			"and ( trim( nvl( rod.c_mercosur, '' ) ) = rel.cuenta \n" +
			"or trim( nvl( rod.c_dominio, '' ) ) = rel.cuenta ) \n" +
			"union \n" +
			"select distinct \n" +
			"'CEMENTERIO' as tipo_cuenta, \n" +
			"rel.cuenta as codigo_cuenta, \n" +
			"to_number(rel.cuenta) as numero_cuenta, \n" +
			"rel.descripcion as alias, \n" +
			"'' as descripcion, \n" +
			"0 as be \n" +
			"from web_recau:usuario_cuentas_rel_cogmvl rel, cementerio:arrendatarios a \n" +
			"where rel.id_usuario = ? \n" +
			"and rel.tipo_cuenta = 5\n" +
			"and a.c_cuenta||'' = rel.cuenta\n" +
			"and a.c_padron = 1\n" +
			"union \n" +
			"select distinct \n" +
			"'PILETAS' as tipo_cuenta, \n" +
			"c.tipo_documento as codigo_cuenta, \n" +
			"to_number(c.nro_documento) as numero_cuenta, \n" +
			"c.descripcion as alias, \n" +
			"'' as descripcion, \n" +
			"0 as be \n" +
			"from web_recau:usuario_cuentas_piletas_cogmvl c \n" +
			"where c.id_usuario = ? \n";
	
	public CuentaDAO(Connection connection) {
		super( connection );
	}
	
	public CuentaDTO recuperarCuenta(TipoCuentaDTO tipoCuentaDTO, String nombreUsuario, String codigoCuenta) throws ErrorRecuperandoCuentasPorUsuario, ErrorRecuperandoCuentaPorCodigo {
		
		CuentaDTO cuentaDTO = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( this.recuperarCuentaQuery( tipoCuentaDTO ) );

			ps.setString( 1, nombreUsuario );
			ps.setString( 2, codigoCuenta );
			
			rs = ps.executeQuery();
			
			if ( rs.next() )
				cuentaDTO = this.crearCuentaDTO( tipoCuentaDTO, rs );
			
		} catch ( SQLException e ) {
			throw new ErrorRecuperandoCuentaPorCodigo( codigoCuenta, e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return cuentaDTO;
	}

	private String recuperarCuentaQuery(TipoCuentaDTO tipoCuentaDTO) {
		CuentaQueryFactory factory = new CuentaQueryFactory( tipoCuentaDTO );
		ICuentaQuery cuentaQuery = factory.generar();
		return cuentaQuery.getRecuperarCuenta();
	}

	public Map<TipoCuentaDTO, List<CuentaDTO>> recuperarCuentasPorUsuario(Integer idUsuario) throws ErrorRecuperandoCuentasPorUsuario {
		
		Map<TipoCuentaDTO, List<CuentaDTO>> mapa = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( RECUPERAR_CUENTAS_POR_USUARIO );
			
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, idUsuario );
			ps.setInt( 3, idUsuario );
			ps.setInt( 4, idUsuario );
			ps.setInt( 5, idUsuario );
			ps.setInt( 6, idUsuario );
			
			rs = ps.executeQuery();
			
			mapa = new HashMap<TipoCuentaDTO, List<CuentaDTO>>();
			while ( rs.next() )
				this.rellenarMapa( mapa, rs );
			
		} catch ( SQLException e ) {
			throw new ErrorRecuperandoCuentasPorUsuario( idUsuario, e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return mapa;
	}

	private void rellenarMapa(Map<TipoCuentaDTO, List<CuentaDTO>> mapa, ResultSet rs) throws SQLException {
		
		String tipoCuenta = rs.getString( "tipo_cuenta" );
		if ( tipoCuenta != null )
			tipoCuenta = tipoCuenta.trim();
		
		TipoCuentaDTO tipoCuentaDTO = TipoCuentaDTO.obtener( tipoCuenta );
		
		if( !mapa.containsKey( tipoCuentaDTO ) )
			mapa.put( tipoCuentaDTO, new ArrayList<CuentaDTO>() );
		
		CuentaDTO cuentaDTO = this.crearCuentaDTO( tipoCuentaDTO, rs );
		
		if ( cuentaDTO != null )
			mapa.get( tipoCuentaDTO ).add( cuentaDTO );
	}

	private CuentaDTO crearCuentaDTO(TipoCuentaDTO tipoCuentaDTO, ResultSet rs) throws SQLException {
		
		String codigoCuenta = rs.getString( "codigo_cuenta" );
		if ( codigoCuenta != null )
			codigoCuenta = codigoCuenta.trim();
		
		Integer numeroCuenta = rs.getInt( "numero_cuenta" );

		String descripcion = rs.getString( "descripcion" );
		if ( descripcion != null )
			descripcion = descripcion.trim();
		
		String alias = rs.getString( "alias" );
		if ( alias != null )
			alias = alias.trim();
			
		boolean aceptaBe = rs.getInt( "be" ) == 1;
		
		FactoryCuentaDTO factory = new FactoryCuentaDTO( tipoCuentaDTO );
		return factory.generar( tipoCuentaDTO.name(), codigoCuenta, numeroCuenta, descripcion, alias, aceptaBe );
	}
}
