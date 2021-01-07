package ar.com.cognisys.sat.core.modelo.factory.seguimiento;

import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;

public class FactoryNavegacionSAT {

	public static NavegacionSAT generarInstancia() {
		
		NavegacionSAT n = new NavegacionSAT();
		
		return n;
	}
	
	public static NavegacionSAT generarInstancia(String etiqueta) {
		
		NavegacionSAT n = generarInstancia();
		n.setEtiqueta(etiqueta);
		
		return n;
	}
}