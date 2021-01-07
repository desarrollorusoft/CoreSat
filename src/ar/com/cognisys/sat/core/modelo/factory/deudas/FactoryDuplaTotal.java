package ar.com.cognisys.sat.core.modelo.factory.deudas;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DuplaTotal;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryDuplaTotal {
	
	public static DuplaTotal getInstance(TotalCuota totalSimple,TotalCuota totalContado){
		
		DuplaTotal duplaTotal = new DuplaTotal();
		
		duplaTotal.setTotalContado(totalContado);
		duplaTotal.setTotalSimple(totalSimple);
		
		return duplaTotal;	
	}
}