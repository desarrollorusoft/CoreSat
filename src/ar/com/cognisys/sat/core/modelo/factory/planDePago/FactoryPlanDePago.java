package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePago;

public class FactoryPlanDePago {
	
	public static PlanDePago generarInstanciaVacia() {
	
		PlanDePago plan = new PlanDePago();
		
		return plan;
	}
}