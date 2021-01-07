package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;

public class FactoryDDJJOEP {

	public static DDJJOEP generar() {
		
		DDJJOEP oep = new DDJJOEP();
		
		return oep;
	}
	
	public static DDJJOEP generar(TipoOEP tipo, Float valor) {
		
		DDJJOEP oep = generar();
		oep.setTipo(tipo);
		oep.setValor(valor);
		
		return oep;
	}
}