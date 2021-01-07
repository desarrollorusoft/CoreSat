package ar.com.cognisys.sat.core.modelo.factory.deudas;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaAutomotoresAPagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryDeudasCuentaAutomotoresAPagar {
	
	public static DeudasCuentaAutomotoresAPagar getInstance(List<CuotaApagar> cuotasAPagar, TotalCuota totalApagar, 
															TotalCuota totalApagarContado){
		
		DeudasCuentaAutomotoresAPagar aPagar = new DeudasCuentaAutomotoresAPagar();
		aPagar.setCuotasAPagar(cuotasAPagar);
		aPagar.setTotalApagar(totalApagar);
		aPagar.setTotalApagarContado(totalApagarContado);
		
		return aPagar;	
	}
}