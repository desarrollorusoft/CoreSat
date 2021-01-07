package ar.com.cognisys.sat.core.modelo.factory.pago;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.pago.BoletaPago;

public class FactoryBoletaPago {

	public static BoletaPago generarInstancia() {
		
		BoletaPago bp = new BoletaPago();
		
		return bp;
	}
	
	public static BoletaPago generarInstancia(Deuda deudaNormal, Deuda deudaContado) {
		
		BoletaPago bp = generarInstancia();
		bp.setDeudaNormal(deudaNormal);
		bp.setDeudaContado(deudaContado);
		
		return bp;
	}
}