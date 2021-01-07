package ar.com.cognisys.sat.v2.persistencia.modelo.queries;

import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaAutomotorQuery implements ICuentaQuery {

	private static final String RECUPERAR_CUENTA = 
			"select distinct \n" + 
			"'VEHICULOS' as tipo_cuenta, \n" + 
			"rel.cuenta as codigo_cuenta, \n" + 
			"veh.c_cuenta as numero_cuenta, \n" + 
			"rel.descripcion as alias, \n" + 
			"'' as descripcion \n" + 
			"from web_recau:usuario_cuentas_rel_cogmvl rel, web_recau:usuario_cogmvl u, vehiculos veh \n" + 
			"where u.cuit = ? \n" + 
			"and rel.cuenta = ? \n" + 
			"and rel.tipo_cuenta = 3 \n" + 
			"and veh.c_baja = 0 \n" + 
			"and rel.id_usuario = u.id_usuario \n" + 
			"and ( trim( nvl( veh.c_dominio_mercosur, '' ) ) = rel.cuenta \n" + 
			"or trim( nvl( veh.c_dominio_actual, '' ) ) = rel.cuenta \n" + 
			"or trim( nvl( veh.c_dominio_original, '' ) ) = rel.cuenta ) \n";
	
	@Override
	public String getRecuperarCuenta() {
		return RECUPERAR_CUENTA;
	}

}
