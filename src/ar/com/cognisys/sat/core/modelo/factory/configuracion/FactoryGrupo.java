package ar.com.cognisys.sat.core.modelo.factory.configuracion;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Grupo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Pantalla;

public class FactoryGrupo {

	public static Grupo generarInstancia() {
		
		Grupo g = new Grupo();
		g.setListaHijos(new ArrayList<Grupo>());
		g.setListaItems(new ArrayList<Item>());
		g.setDisponible(false);
		
		return g;
	}
	
	public static Grupo generarInstancia(String nombre, Grupo padre) {
		
		Grupo g = generarInstancia();
		g.setNombre(nombre);
		g.setPadre(padre);
		
		return g;
	}
	
	public static Grupo generarInstancia(String nombre, Grupo padre, boolean disponible, Integer orden) {
		
		Grupo g = generarInstancia();
		g.setNombre(nombre);
		g.setPadre(padre);
		g.setDisponible(disponible);
		g.setOrden(orden);
		
		return g;
	}
	
	public static Grupo generarInstancia(String nombre, Pantalla pantalla) {
		
		Grupo g = generarInstancia();
		g.setNombre(nombre);
		g.setPantalla(pantalla);
		
		return g;
	}
	
	
	public static Grupo generarInstancia(Long id, String nombre) {
		
		Grupo g = generarInstancia();
		g.setId(id);
		g.setNombre(nombre);
		
		return g;
	}
	
	public static Grupo generarInstancia(Long id, String nombre, boolean disponible, Integer orden) {
		
		Grupo g = generarInstancia();
		g.setId(id);
		g.setNombre(nombre);
		g.setDisponible(disponible);
		g.setOrden(orden);
		
		return g;
	}
	
	public static Grupo generarInstancia(Long id, String nombre, List<Grupo> listaHijos) {
		
		Grupo g = generarInstancia();
		g.setId(id);
		g.setNombre(nombre);
		g.setListaHijos(listaHijos);
		
		return g;
	}
	
	public static Grupo generarInstancia(Long id, String nombre, Grupo padre, Pantalla pantalla, List<Grupo> listaHijos, List<Item> listaItems) {
		
		Grupo g = generarInstancia();
		g.setId(id);
		g.setNombre(nombre);
		g.setListaHijos(listaHijos);
		g.setPadre(padre);
		g.setPantalla(pantalla);
		g.setListaItems(listaItems);
		
		return g;
	}
}