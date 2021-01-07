package ar.com.cognisys.sat.core.modelo.factory;

import ar.com.cognisys.sat.core.modelo.comun.TasaAbl;

public class FactoryTasaAbl {

	public static TasaAbl generarInstanciaVacia() {
		
		TasaAbl tasa = new TasaAbl();
		
		return tasa;
	}
	
	public static TasaAbl generarInstanciaCompleta(String f_pago, String f_prim_vto,
												   String f_seg_vto, String i_deuda,
												   String i_deuda_orig, String i_rec_prim_vto,
												   String i_rec_seg_vtou, String n_ano,
												   String n_comprob, String n_cuota) {
		
		TasaAbl tasa = generarInstanciaVacia();
		tasa.setF_pago(f_pago);
		tasa.setF_prim_vto(f_prim_vto);
		tasa.setF_seg_vto(f_seg_vto);
		tasa.setI_deuda(i_deuda);
		tasa.setI_deuda_orig(i_deuda_orig);
		tasa.setI_rec_prim_vto(i_rec_prim_vto);
		tasa.setI_rec_seg_vtou(i_rec_seg_vtou);
		tasa.setN_ano(n_ano);
		tasa.setN_comprob(n_comprob);
		tasa.setN_cuota(n_cuota);
		
		return tasa;
	}
}