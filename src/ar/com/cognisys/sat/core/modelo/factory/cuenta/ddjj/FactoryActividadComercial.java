package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;

public class FactoryActividadComercial {

	public static ActividadComercial generar() {
	
		ActividadComercial actividad = new ActividadComercial();
		
		return actividad;
	}
	
	public static ActividadComercial generar(Integer codigo, String nombre) {
		
		ActividadComercial a = generar();
		a.setCodigo(codigo);
		a.setNombre(nombre);
		a.setPermitido(true);
		
		return a;
	}
	
	public static ActividadComercial generar(Integer codigo, String nombre, boolean permitido) {
		
		ActividadComercial a = generar(codigo, nombre);
		a.setPermitido(permitido);
		
		return a;
	}
}