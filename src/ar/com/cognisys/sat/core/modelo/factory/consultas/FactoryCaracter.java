package ar.com.cognisys.sat.core.modelo.factory.consultas;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Caracter;

public class FactoryCaracter {
	
	public static Caracter generarInstancia(){
		return new Caracter();
	}

	public static Caracter generarInstancia(Long id, String nombre){
		Caracter c = generarInstancia();
		c.setId(id);
		c.setNombre(nombre);
		
		return c;
	}
}
