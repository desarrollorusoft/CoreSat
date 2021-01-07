package ar.com.cognisys.sat.core.modelo.factory;

import ar.com.cognisys.sat.core.modelo.comun.TasaProteccionCiudadana;

public class FactoryTasaProteccionCiudadana {

	public static TasaProteccionCiudadana generarInstanciaVacia() {
		
		TasaProteccionCiudadana tasa = new TasaProteccionCiudadana();
		
		return tasa;
	}
	
	public static TasaProteccionCiudadana generarInstanciaCompleta(String i_deuda, String i_deuda_orig,
																   String i_rec_prim_vto, String i_rec_seg_vtou) {
		
		TasaProteccionCiudadana tasa = generarInstanciaVacia();
		tasa.setI_deuda(i_deuda);
		tasa.setI_deuda_orig(i_deuda_orig);
		tasa.setI_rec_prim_vto(i_rec_prim_vto);
		tasa.setI_rec_seg_vtou(i_rec_seg_vtou);
		
		return tasa;
	}
}