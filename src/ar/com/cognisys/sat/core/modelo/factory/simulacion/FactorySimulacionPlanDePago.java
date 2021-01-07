package ar.com.cognisys.sat.core.modelo.factory.simulacion;

import ar.com.cognisys.sat.core.modelo.comun.simulacion.SimulacionPlanDePago;

public class FactorySimulacionPlanDePago {

	public static SimulacionPlanDePago generarInstanciaVacia() {

		SimulacionPlanDePago simulacion = new SimulacionPlanDePago();

		return simulacion;
	}
}