package ar.com.cognisys.sat.v2.persistencia.modelo.queries;

import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaRodadoQuery implements ICuentaQuery {

	private static final String RECUPERAR_CUENTA = 
			"select distinct \n" + 
			"'RODADOS' as tipo_cuenta, \n" + 
			"rel.cuenta as codigo_cuenta, \n" + 
			"rod.c_cuenta as numero_cuenta, \n" + 
			"rel.descripcion as alias, \n" + 
			"trim( nvl( rod.d_marca, '' ) ) || ' ' || decode( trim( nvl( rod.d_modelo, '' ) ), '', '', trim( nvl( rod.d_modelo, '' ) ) || ' ' ) || trim( nvl( rod.n_cilindrada, '' ) ) as descripcion \n" + 
			"from web_recau:usuario_cuentas_rel_cogmvl rel, web_recau:usuario_cogmvl u, rodados rod \n" + 
			"where u.cuit = ? \n" + 
			"and rel.cuenta = ? \n" + 
			"and rel.tipo_cuenta = 4 \n" + 
			"and rod.c_baja = 0 \n" +
			"and rel.id_usuario = u.id_usuario \n" + 
			"and ( trim( nvl( rod.c_mercosur, '' ) ) = rel.cuenta \n" + 
			"or trim( nvl( rod.c_dominio, '' ) ) = rel.cuenta ) \n";
	
	@Override
	public String getRecuperarCuenta() {
		return RECUPERAR_CUENTA;
	}
}
