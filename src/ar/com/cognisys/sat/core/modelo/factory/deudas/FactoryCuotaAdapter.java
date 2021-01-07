package ar.com.cognisys.sat.core.modelo.factory.deudas;

import ar.com.cognisys.sat.core.modelo.comun.deudas.CuotaAdapter;

public class FactoryCuotaAdapter {

	public static CuotaAdapter generar() {
		return new CuotaAdapter();
	}
}