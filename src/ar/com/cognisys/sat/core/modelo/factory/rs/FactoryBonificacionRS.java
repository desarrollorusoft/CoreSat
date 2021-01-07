package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.util.ArrayList;
import java.util.Calendar;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.dao.rs.BonificacionRS;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuota;

public class FactoryBonificacionRS {

	public static BonificacionRS generar() {
		
		BonificacionRS brs = new BonificacionRS();
		brs.setCuotas(new ArrayList<Cuota>());
		brs.setCorresponde(false);
		
		return brs;
	}

	public static BonificacionRS generar(float cuota2, float cuota3) {
		BonificacionRS brs = generar();
		brs.setCorresponde(true);
		Calendar c = Calendar.getInstance();
		int ano = c.get(Calendar.YEAR);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.YEAR, ano+1);
		
		brs.getCuotas().add( FactoryCuota.generarIntanciaCompleta("Régimen Simplificado", "BONIFICADO", c.getTime(), ano+"-02", cuota2, 0f, 0f, cuota2,null,null,203) );
		brs.getCuotas().add( FactoryCuota.generarIntanciaCompleta("Régimen Simplificado", "BONIFICADO", c.getTime(), ano+"-03", cuota3, 0f, 0f, cuota3,null,null, 203) );

		return brs;
	}
}