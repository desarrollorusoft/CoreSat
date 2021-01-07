package ar.com.cognisys.sat.core.modelo.factory.seguimiento;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;
import ar.com.cognisys.sat.core.modelo.comun.seguimiento.Acceso;

public class FactoryAcceso {

	public static Acceso generarInstancia() {
		
		Acceso acceso = new Acceso();
		
		return acceso;
	}
	
	public static Acceso generarInstancia(NavegacionSAT acceso) {
		
		Acceso a = new Acceso();
		a.setAcceso(acceso);
		a.setFecha(new Date());
		
		return a;
	}
}