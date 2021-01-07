package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanSimulacion;

public class FactoryPlanSimulacion {

	public static PlanSimulacion generarInstancia() {
		
		PlanSimulacion cuota = new PlanSimulacion();
		
		return cuota;
	}
	
	public static PlanSimulacion generarInstancia(Integer numero, Float importe) {
		
		PlanSimulacion cuota = generarInstancia();
		cuota.setNumeroCuotas(numero);
		cuota.setImporteCuota(importe);
		
		return cuota;
	}
}