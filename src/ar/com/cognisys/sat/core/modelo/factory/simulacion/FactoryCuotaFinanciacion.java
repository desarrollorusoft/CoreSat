package ar.com.cognisys.sat.core.modelo.factory.simulacion;

import ar.com.cognisys.sat.core.modelo.comun.simulacion.CuotaFinanciacion;

public class FactoryCuotaFinanciacion {

	public static CuotaFinanciacion generarIntanciaCompleta(int cuotas, float importe, float coeficiente) {
		
		CuotaFinanciacion cuota = new CuotaFinanciacion();
		
		cuota.setCoeficiente(coeficiente);
		cuota.setCuotas(cuotas);
		cuota.setImporte(importe);
		
		return cuota;
	}
}