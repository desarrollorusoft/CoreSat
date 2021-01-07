package ar.com.cognisys.sat.core.modelo.factory.consultas;

import ar.com.cognisys.sat.core.modelo.comun.consultas.ArchivoRutaNombre;

public class FactoryArchivoRutaNombre {

	public static ArchivoRutaNombre crear(){
		return new ArchivoRutaNombre();
	}
	
	public static ArchivoRutaNombre generar(String nombre, String ruta){
		
		ArchivoRutaNombre a = crear();
		
		a.setNombre( nombre );
		a.setRuta( ruta );
		
		return a;
	}
	
}
