package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanSimulacion;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.ResultadoSimulacion;

public class FactoryResultadoSimulacion {
	
	public static ResultadoSimulacion generarInstancia() {
		
		ResultadoSimulacion resultado = new ResultadoSimulacion();
		resultado.setListaPlanes(new ArrayList<PlanSimulacion>());
		
		return resultado;
	}
	
	public static ResultadoSimulacion generarInstancia(Integer numeroSolicitud, Integer anticipoCalculado, 
													   Float imoporteAnticipo, Float importePlan) {
		
		ResultadoSimulacion resultado = new ResultadoSimulacion();
		resultado.setNumeroSolicitud(numeroSolicitud);
		resultado.setPorcentajeCalculado(anticipoCalculado);
		resultado.setImporteAnticipo(imoporteAnticipo);
		resultado.setImportePlan(importePlan);
		
		return resultado;
	}
}