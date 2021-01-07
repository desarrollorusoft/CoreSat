package ar.com.cognisys.sat.core.modelo.factory.pago;

import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.pago.BoletaPagoCementerio;

@Deprecated
public class FactoryBoletaPagoCementerio {

	public static BoletaPagoCementerio generarInstancia() {
		
		BoletaPagoCementerio bp = new BoletaPagoCementerio();
		
		return bp;
	}
	
	public static BoletaPagoCementerio generarInstancia(DeudasCuentaCementerio deudaNormal, DeudasCuentaCementerio deudaContado) {
		
		BoletaPagoCementerio bp = new BoletaPagoCementerio();
		bp.setDeudaNormal(deudaNormal);
		bp.setDeudaContado(deudaContado);
		
		return bp;
	}
}