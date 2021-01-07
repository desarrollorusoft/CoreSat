package ar.com.cognisys.sat.core.modelo.factory.consultas;

import ar.com.cognisys.sat.core.modelo.comun.consultas.TipoConsulta;

public class FactoryTipoConsulta {

	public static TipoConsulta generarInstancia(){
		return new TipoConsulta();
	}

	public static TipoConsulta generarInstancia(Long id, String nombre){
		TipoConsulta t = generarInstancia();
		t.setId(id);
		t.setNombre(nombre);
		
		return t;
	}
	
	
}
