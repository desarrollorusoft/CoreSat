package ar.com.cognisys.sat.core.modelo.factory.deudas;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryDeudaAdapter {

	public static DeudaAdapter generar() {
		
		DeudaAdapter deuda = new DeudaAdapter();
		deuda.setListaCuotasAVencer(new ArrayList<Cuota>());
		deuda.setListaCuotasVencidas(new ArrayList<Cuota>());
		
		return deuda;
	}
	
	public static DeudaAdapter generar(List<Cuota> listaCuotasAVencer, List<Cuota> listaCuotasVencidas,
									   TotalCuota totalCoutasAVencer, TotalCuota totalCoutasVencidas, TotalCuota totalCoutasGeneral) {
		
		DeudaAdapter deuda = new DeudaAdapter();
		deuda.setListaCuotasAVencer(listaCuotasAVencer);
		deuda.setListaCuotasVencidas(listaCuotasVencidas);
		deuda.setTotalCoutasAVencer(totalCoutasAVencer);
		deuda.setTotalCoutasVencidas(totalCoutasVencidas);
		deuda.setTotalCoutasGeneral(totalCoutasGeneral);
		deuda.generarAdapters();
		
		return deuda;
	}
	
	public static DeudaAdapter generar(List<Cuota> recuperarAVencer, List<Cuota> recuperarVencidas,
									   TotalCuota totalCoutasAVencer, TotalCuota totalCoutasVencidas, 
									   TotalCuota totalCoutasGeneral, boolean tieneDeudaLegales) {
		
		DeudaAdapter deuda = generar(recuperarAVencer, recuperarVencidas, totalCoutasAVencer, totalCoutasVencidas, totalCoutasGeneral);
		deuda.setTieneDeudaLegales(tieneDeudaLegales);
		
		return deuda;
	}
}