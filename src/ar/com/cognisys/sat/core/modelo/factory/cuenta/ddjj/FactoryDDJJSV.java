package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJSV;

public class FactoryDDJJSV {

	public static DDJJSV generar() {
		
		DDJJSV sv = new DDJJSV();
		
		return sv;
	}
	
	public static DDJJSV generar(Integer motores, Integer calderas) {
		
		DDJJSV sv = generar();
		sv.setMotores(motores);
		sv.setCalderas(calderas);
		
		return sv;
	}
}