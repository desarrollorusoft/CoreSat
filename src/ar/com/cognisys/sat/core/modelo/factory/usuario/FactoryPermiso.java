package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;

public class FactoryPermiso {

	public static Permiso generarIntanciaVacia() {
		
		Permiso permiso = new Permiso();
		
		return permiso;
	}
	
	public static Permiso generarIntanciaCompleta(String nombre) {
		
		Permiso permiso = generarIntanciaVacia();
		permiso.setNombre(nombre);
		
		return permiso;
	}
}