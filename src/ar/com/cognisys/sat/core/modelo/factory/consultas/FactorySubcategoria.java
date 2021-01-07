package ar.com.cognisys.sat.core.modelo.factory.consultas;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Subcategoria;

public class FactorySubcategoria {

	public static Subcategoria generarInstancia(){
		Subcategoria s = new Subcategoria();
		return s;
	}
	
	public static Subcategoria generarInstancia(Long id, String nombre){
		
		Subcategoria s = generarInstancia();
		
		s.setId(id);
		s.setNombre(nombre);
		
		return s;	
	}
}
