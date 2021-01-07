package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;

public class FactoryComercio {

	public static Comercio generar() {
		
		Comercio comercio = new Comercio();
		comercio.setListaDeclaraciones(new ArrayList<DDJJRS>());
		
		return comercio;
	}
	
	public static Comercio generar(String cuit, String razonSocial) {
		
		Comercio comercio = generar();
		comercio.setCuit(cuit);
		comercio.setRazonSocial(razonSocial);
		
		return comercio;
	}
}