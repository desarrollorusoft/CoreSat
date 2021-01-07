package ar.com.cognisys.sat.core.modelo.factory.configuracion;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;

public class FactoryPantalla {

	public static Pantalla generarInstancia() {
		
		Pantalla p = new Pantalla();
		p.setListaGrupos(new ArrayList<Grupo>());
		
		return p;
	}
	
	public static Pantalla generarInstancia(Long id, String nombre) {
		
		Pantalla p = generarInstancia();
		p.setId(id);
		p.setNombre(nombre);
		
		return p;
	}
}