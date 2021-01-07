package ar.com.cognisys.sat.v2.persistencia.modelo.queries;

import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaABLQuery implements ICuentaQuery {

	private static final String RECUPERAR_CUENTA = 
			"select distinct \n" + 
			"'ABL' as tipo_cuenta, \n" + 
			"rel.cuenta as codigo_cuenta, \n" + 
			"abl.c_cuenta as numero_cuenta, \n" + 
			"rel.descripcion as alias, \n" + 
			"trim( nvl( cal.d_calle, '' ) ) || ' ' || \n" + 
			"decode ( trim ( nvl( abl.n_nro_pro, '' ) ), '', '', trim ( nvl( abl.n_nro_pro, '' ) ) || ' ' ) || \n" + 
			"decode ( trim ( nvl( abl.c_piso_pro, '' ) ), '', '', trim ( nvl( abl.c_piso_pro, '' ) ) || ' ' ) || \n" + 
			"decode ( trim ( nvl( abl.c_dpto_pro, '' ) ) , '', '', trim ( nvl( abl.c_dpto_pro, '' ) ) ) as descripcion \n" + 
			"from web_recau:usuario_cuentas_rel_cogmvl rel, web_recau:usuario_cogmvl u, abl abl, calles cal \n" + 
			"where u.cuit = ? \n" +  
			"and rel.cuenta = ? \n" +
			"and rel.tipo_cuenta = 1 \n" + 
			"and abl.c_baja = 0 \n" + 
			"and rel.id_usuario = u.id_usuario \n" +
			"and abl.c_cuenta = rel.cuenta \n" + 
			"and cal.c_calle = abl.c_calle_pro \n";
	
	@Override
	public String getRecuperarCuenta() {
		return RECUPERAR_CUENTA;
	}

}
