package ar.com.cognisys.sat.core.modelo.factory.seguimiento;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.seguimiento.Acceso;
import ar.com.cognisys.sat.core.modelo.comun.seguimiento.Seguimiento;

public class FactorySeguimiento {

	public static Seguimiento generarInstancia() {
		
		Seguimiento seguimiento = new Seguimiento();
		seguimiento.setListaAccesos(new ArrayList<Acceso>());
		
		return seguimiento;
	}
	
	public static Seguimiento generarInstancia(ArrayList<Acceso> listaAccesos) {
		
		Seguimiento seguimiento = new Seguimiento();
		seguimiento.setListaAccesos(listaAccesos);
		
		return seguimiento;
	}
}