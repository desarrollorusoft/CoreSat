package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;

public class FactoryTipoCartel {
	
	public static TipoCartel generar() {
		
		TipoCartel tc = new TipoCartel();
		
		return tc;
	}
	
	public static TipoCartel generar(Integer codigo, String nombre) {
		
		TipoCartel tc = generar();
		tc.setCodigo(codigo);
		tc.setNombre(nombre);
		
		return tc;
	}
}
