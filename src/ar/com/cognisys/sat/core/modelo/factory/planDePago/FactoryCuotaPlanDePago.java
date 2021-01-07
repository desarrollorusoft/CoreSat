package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import java.text.NumberFormat;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPlanDePago;

public class FactoryCuotaPlanDePago {
	
	public static  CuotaPlanDePago getInstance (Integer cuotaPlan, Date vencimiento, Float capital, Float recargo, Float multa,
												 Float interes, Date fechaPago) {	
		
		NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        
		CuotaPlanDePago cuotaPlanDePago = new CuotaPlanDePago();
		
		cuotaPlanDePago.setCapital(capital);
		cuotaPlanDePago.setCuotaPlan(cuotaPlan);
		cuotaPlanDePago.setFechaPago(fechaPago);
		cuotaPlanDePago.setInteres(interes);
		cuotaPlanDePago.setMulta(multa);
		cuotaPlanDePago.setRecargo(recargo);
		cuotaPlanDePago.setVencimiento(vencimiento);
		cuotaPlanDePago.setPagar(false);
		
		return cuotaPlanDePago;
		
	}
	
	
	

}
