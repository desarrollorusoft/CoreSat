package ar.com.cognisys.sat.v2.persistencia.modelo.queries;

import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaCementerioQuery implements ICuentaQuery {

	private static final String RECUPERAR_CUENTA = 
			"select distinct \n" + 
			"'CEMENTERIO' as tipo_cuenta, \n" + 
			"rel.cuenta as codigo_cuenta, \n" + 
			"to_number(rel.cuenta) as numero_cuenta, \n" + 
			"rel.descripcion as alias, \n" + 
			"'' as descripcion \n" + 
			"from web_recau:usuario_cuentas_rel_cogmvl rel, web_recau:usuario_cogmvl u \n" + 
			"where u.cuit = ? \n" + 
			"and rel.cuenta = ? \n" + 
			"and rel.id_usuario = u.id_usuario \n" + 
			"and rel.tipo_cuenta = 5 \n";
	
	@Override
	public String getRecuperarCuenta() {
		return RECUPERAR_CUENTA;
	}

}
