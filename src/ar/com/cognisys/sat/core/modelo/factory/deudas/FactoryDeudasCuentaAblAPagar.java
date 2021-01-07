package ar.com.cognisys.sat.core.modelo.factory.deudas;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaAblAPagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryDeudasCuentaAblAPagar {
	
	public static DeudasCuentaAblAPagar getInstance(List<CuotaApagar>  cuotasAPagar,TotalCuota totalApagar,TotalCuota totalApagarContado){
		
		DeudasCuentaAblAPagar aPagar = new DeudasCuentaAblAPagar();
		aPagar.setCuotasAPagar(cuotasAPagar);
		aPagar.setTotalApagar(totalApagar);
		aPagar.setTotalApagarContado(totalApagarContado);
		
		return aPagar;	
	}
}