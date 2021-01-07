package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;

public class FactoryActividades {

	public static Actividades generar() {
		
		Actividades a = new Actividades();
		a.setOtrasActividades(new ArrayList<ActividadComercial>());
		
		return a;
	}
	
	public static Actividades generar(ActividadComercial actividadPrincipal) {
		
		Actividades a = generar();
		a.setActividadPrincipal(actividadPrincipal);
		
		return a;
	}
	
	public static Actividades generar(ActividadComercial actividadPrincipal, List<ActividadComercial> listaOtros) {
		
		Actividades a = generar(actividadPrincipal);
		a.setOtrasActividades(listaOtros);
		
		return a;
	}
}