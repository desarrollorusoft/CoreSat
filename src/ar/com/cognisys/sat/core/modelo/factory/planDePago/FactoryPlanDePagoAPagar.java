package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;

public class FactoryPlanDePagoAPagar {
	
	public static PlanDePagoAPagar getInstance(Integer nroPlan,String solicitante,String estadoPlan, Integer cantCuotas,
											   Float importeCancelacion){
		PlanDePagoAPagar p = new PlanDePagoAPagar();
		p.setCantCuotas(cantCuotas);
		p.setEstadoPlan(estadoPlan);
		p.setNroPlan(nroPlan);
		p.setSolicitante(solicitante != null ? solicitante.trim() : "");
		p.setImporteCancelacion( importeCancelacion );
		
		return p;
	}

	public static PlanDePagoAPagar getInstance(Integer nroPlan, String solicitante, String estadoPlan, Integer cantCuotas,
											   Float importeCancelacion, Integer cuenta, Integer sistema){

		PlanDePagoAPagar p = getInstance(nroPlan, solicitante, estadoPlan, cantCuotas, importeCancelacion);
		p.setCuenta( cuenta );
		p.setSistema( sistema );

		return p;
	}

	public static PlanDePagoAPagar getInstance(Integer nroPlan, String solicitante, String estadoPlan, Integer cantCuotas,
											   Float importeCancelacion, Integer cuenta, Integer sistema, String alias){

		PlanDePagoAPagar p = getInstance(nroPlan, solicitante, estadoPlan, cantCuotas, importeCancelacion, cuenta, sistema);
		p.setAlias( alias );

		return p;
	}

	public static PlanDePagoAPagar getInstance(Integer nroPlan, String solicitante, String estadoPlan, Integer cantCuotas,
											   Float importeCancelacion, Integer cuenta, Integer sistema, String alias, boolean asociado){

		PlanDePagoAPagar p = getInstance(nroPlan, solicitante, estadoPlan, cantCuotas, importeCancelacion, cuenta, sistema, alias);
		p.setAsociado( asociado );

		return p;
	}
}