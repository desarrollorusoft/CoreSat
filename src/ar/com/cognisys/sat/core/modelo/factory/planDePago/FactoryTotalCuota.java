package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class FactoryTotalCuota {

	public static TotalCuota generarIntanciaVacia() {
		
		TotalCuota totalCuota = new TotalCuota();
		totalCuota.setCapital(0f);
		totalCuota.setRecargo(0f);
		totalCuota.setMulta(0f);
		totalCuota.setTotal(0f);
		
		return totalCuota;
	}
	
	public static TotalCuota generarIntanciaCompleta(Float capital, Float recargo, Float multa, Float total) {
		
		TotalCuota totalCuota = generarIntanciaVacia();
		totalCuota.setCapital(capital);
		totalCuota.setRecargo(recargo);
		totalCuota.setMulta(multa);
		totalCuota.setTotal(total);
		
		return totalCuota;
	}

	public static TotalCuota generarIntanciaCompletaAutomotores(Float capital, Float recargo, Float multa) {
		
		TotalCuota totalCuota = generarIntanciaVacia();
		totalCuota.setCapital(capital);
		totalCuota.setRecargo(recargo);
		totalCuota.setMulta(multa);
		
		return totalCuota;
	}
}