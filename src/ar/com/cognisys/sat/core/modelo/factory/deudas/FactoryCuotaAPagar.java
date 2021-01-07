package ar.com.cognisys.sat.core.modelo.factory.deudas;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;

public class FactoryCuotaAPagar {
	
	public static  CuotaApagar getInstance(Integer numeroTasa, Float capital, Float multa, Float recargo, String tasa, 
										   Integer numeroTransaccion, String fechaVencimiento, Integer vencida, boolean enProcesoJudicial) {
		
		CuotaApagar cuotaApagar = new CuotaApagar();
		cuotaApagar.setNumeroTasa(numeroTasa);
		cuotaApagar.setCapital(capital);
		cuotaApagar.setMulta(multa);
		cuotaApagar.setRecargo(recargo);
		cuotaApagar.setTasa(tasa);
		cuotaApagar.setNumeroTransaccion(numeroTransaccion);
		cuotaApagar.setFechaVencimiento(fechaVencimiento);
		cuotaApagar.setVencida(vencida == 1);
		cuotaApagar.setEnProcesoJudicial(enProcesoJudicial);
		
		return cuotaApagar;	
	}
}