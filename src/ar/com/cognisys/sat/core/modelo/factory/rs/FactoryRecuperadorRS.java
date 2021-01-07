package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS2020;

public class FactoryRecuperadorRS {

	public static RecuperadorVersionesRS generar(DDJJRS ddjj, Connection con) {
		
		RecuperadorVersionesRS r = null;
		
		if (ddjj.getAno() <= 2018) 
			r = new RecuperadorVersionesRS2018(ddjj, con);
		else if (ddjj.getAno() == 2019)
			r = new RecuperadorVersionesRS2019(ddjj, con);
		else if (ddjj.getAno() == 2020)
			r = new RecuperadorVersionesRS2020(ddjj, con);
			
		return r;
	}
}