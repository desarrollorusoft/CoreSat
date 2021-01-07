package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaAVencer;

public class FactoryCuotaAVencer {

	public static CuotaAVencer generarIntanciaVacia() {
		
		CuotaAVencer cuota = new CuotaAVencer();
		
		return cuota;
	}
	
	public static CuotaAVencer generarIntanciaCompleta(String tasa, String periodo, 
												Float capital, Float multa, Float recargo, Float total) {
		
		CuotaAVencer cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		cuota.setTotal(total);
		
		return cuota;
	}
}