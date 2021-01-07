package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSDeudaPadron;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSPeriodoDeuda;

public class FactoryRSDeudaPadron {

	public static RSDeudaPadron generar() {
		
		RSDeudaPadron deuda = new RSDeudaPadron();
		
		return deuda;
	}
	
	public static RSDeudaPadron generar(Float total, Float monto200, Float monto201, Float monto202, Float monto203, 
										List<RSPeriodoDeuda> vencimientos, String cuenta) {
		
		RSDeudaPadron deuda = new RSDeudaPadron();
		deuda.setTotal(total);
		deuda.setMonto200(monto200);
		deuda.setMonto201(monto201);
		deuda.setMonto202(monto202);
		deuda.setMonto203(monto203);
		deuda.setVencimientos(vencimientos);
		deuda.setCuenta(cuenta);
		deuda.cargarVencimientos();
		
		return deuda;
	}
}