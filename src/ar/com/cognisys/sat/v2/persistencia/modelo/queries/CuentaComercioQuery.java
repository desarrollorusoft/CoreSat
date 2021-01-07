package ar.com.cognisys.sat.v2.persistencia.modelo.queries;

import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaComercioQuery implements ICuentaQuery {

	private static final String RECUPERAR_CUENTA = 
			"select distinct \n" + 
			"'COMERCIOS' as tipo_cuenta, \n" + 
			"rel.cuenta as codigo_cuenta, \n" + 
			"com.c_cuenta as numero_cuenta, \n" + 
			"rel.descripcion as alias, \n" + 
			"trim( nvl( com.d_razon_social, '' ) ) as descripcion \n" + 
			"from web_recau:usuario_cuentas_rel_cogmvl rel, web_recau:usuario_cogmvl u, comercios com \n" + 
			"where u.cuit = ? \n" + 
			"and rel.cuenta = ? \n" + 
			"and rel.tipo_cuenta = 2 \n" + 
			"and com.c_baja = 0 \n" + 
			"and rel.id_usuario = u.id_usuario \n" + 
			"and com.n_cuit = rel.cuenta \n";
	
	@Override
	public String getRecuperarCuenta() {
		return RECUPERAR_CUENTA;
	}

}
