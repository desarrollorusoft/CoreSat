package ar.com.cognisys.sat.core.modelo.factory.simulacion;

import ar.com.cognisys.sat.core.modelo.comun.simulacion.CuotaPDP;

public class FactoryCuotaPDP {

	public static CuotaPDP generarIntanciaCompleta(String tasa, String periodo, Float total) {
		
		CuotaPDP cuota = new CuotaPDP();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setTotal(total);
		
		return cuota;
	}
}