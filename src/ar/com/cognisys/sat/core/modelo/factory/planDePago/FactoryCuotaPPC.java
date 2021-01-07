package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPPC;

public class FactoryCuotaPPC {

	public static CuotaPPC generar() {
		
		CuotaPPC cuota = new CuotaPPC();
		
		return cuota;
	}
	
	public static CuotaPPC generar(Date fechaVencimiento, Float capital, Float recargo, Float multa, Float interes, 
								   Float total, Integer numeroCuota) {
		
		CuotaPPC cuota = generar();
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setRecargo(recargo);
		cuota.setMulta(multa);
		cuota.setInteres(interes);
		cuota.setTotal(total);
		cuota.setNumero(numeroCuota);
		
		return cuota;
	}
}