package ar.com.cognisys.sat.core.modelo.factory.recibo;

import ar.com.cognisys.sat.core.modelo.comun.recibos.abl.InformacionPrincipalReciboAbl;

public class FactoryInfomacionPrincipalReciboAbl {

	public static InformacionPrincipalReciboAbl generarInstanciaVacia() {
		
		InformacionPrincipalReciboAbl info = new InformacionPrincipalReciboAbl();
		
		return info;
	}
	
	public static InformacionPrincipalReciboAbl generarInstanciaCompleta(String c_cuenta, String c_eximido,
																		String c_postal, String c_seccion,
																		String d_apellido, String d_calle_pro,
																		String d_calle_tit, String d_lmanz,
																		String d_loc_tit, String d_lparc,
																		String d_lufun, String d_nombre,
																		String f_proceso, String n_cate,
																		String n_circ, String n_fraccion,
																		String n_frente, String n_manz,
																		String n_nro_pro, String n_nro_tit,
																		String n_parc, String n_partida,
																		String n_poli, String n_ufun,
																		String n_valua, String n_verificador,
																		 String s_deuda,
																		String n_reparto, String n_orden) {
		
		InformacionPrincipalReciboAbl info = generarInstanciaVacia();
		info.setC_cuenta(c_cuenta);
		info.setC_eximido(c_eximido);
		info.setC_postal(c_postal);
		info.setC_seccion(c_seccion);
		info.setD_apellido(d_apellido);
		info.setD_calle_pro(d_calle_pro);
		info.setD_calle_tit(d_calle_tit);
		info.setD_lmanz(d_lmanz);
		info.setD_loc_tit(d_loc_tit);
		info.setD_lparc(d_lparc);
		info.setD_lufun(d_lufun);
		info.setD_nombre(d_nombre);
		info.setF_proceso(f_proceso);
		info.setN_cate(n_cate);
		info.setN_circ(n_circ);
		info.setN_fraccion(n_fraccion);
		info.setN_frente(n_frente);
		info.setN_manz(n_manz);
		info.setN_nro_pro(n_nro_pro);
		info.setN_nro_tit(n_nro_tit);
		info.setN_parc(n_parc);
		info.setN_partida(n_partida);
		info.setN_poli(n_poli);
		info.setN_ufun(n_ufun);
		info.setN_valua(n_valua);
		info.setN_verificador(n_verificador);
		info.setS_deuda(s_deuda);
		info.setN_reparto(n_reparto);
		info.setN_orden(n_orden);
		
		return info;
	}
}