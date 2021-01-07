package ar.com.cognisys.sat.core.modelo.factory.deudas;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaAbl;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryDeudasCuentaAbl {

	public static DeudasCuentaAbl generarIntanciaVacia() {
		
		DeudasCuentaAbl deudasCuentaAbl = new DeudasCuentaAbl();
		deudasCuentaAbl.setListaCuotasAVencer(new ArrayList<Cuota>());
		deudasCuentaAbl.setListaCuotasVencidas(new ArrayList<Cuota>());
		
		return deudasCuentaAbl;
	}

	public static DeudasCuentaAbl generarIntanciaCompleta(List<Cuota> listaCuotasAVencer, List<Cuota> listaCuotasVencidas,
														  TotalCuota totalCoutasAVencer, TotalCuota totalCoutasVencidas, 
														  TotalCuota totalCoutasGeneral) {
		
		DeudasCuentaAbl deudasCuentaAbl = new DeudasCuentaAbl();
		deudasCuentaAbl.setListaCuotasAVencer(listaCuotasAVencer);
		deudasCuentaAbl.setListaCuotasVencidas(listaCuotasVencidas);
		deudasCuentaAbl.setTotalCoutasAVencer(totalCoutasAVencer);
		deudasCuentaAbl.setTotalCoutasVencidas(totalCoutasVencidas);
		deudasCuentaAbl.setTotalCoutasGeneral(totalCoutasGeneral);
		
		return deudasCuentaAbl;
	}

	public static DeudasCuentaAbl generarIntanciaCompleta(
			List<Cuota> recuperarAVencer, List<Cuota> recuperarVencidas,
			TotalCuota recuperarTotalAVencer,
			TotalCuota recuperarTotalVencidas,
			TotalCuota recuperarTotalGeneral, boolean tieneDeudaLegales) {
		DeudasCuentaAbl output = generarIntanciaCompleta(recuperarAVencer, recuperarVencidas, recuperarTotalAVencer, recuperarTotalVencidas, recuperarTotalGeneral);
		output.setTieneDeudaLegales(tieneDeudaLegales);
		return output;
	}
}