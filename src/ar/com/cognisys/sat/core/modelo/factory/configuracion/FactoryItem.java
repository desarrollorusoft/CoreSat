package ar.com.cognisys.sat.core.modelo.factory.configuracion;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;

public class FactoryItem {

	public static Item generarInstancia() {
		
		Item item = new Item();
		
		return item;
	}

	public static Item generarInstancia(String titulo, String descripcion) {
		
		Item item = generarInstancia();
		item.setTitulo(titulo);
		item.setDescripcion(descripcion);
		
		return item;
	}
	
	public static Item generarInstancia(Long id, String titulo, String descripcion, boolean disponible, Integer orden) {
		
		Item item = generarInstancia();
		item.setId(id);
		item.setTitulo(titulo);
		item.setDescripcion(descripcion);
		item.setDisponible(disponible);
		item.setOrden(orden);
		
		return item;
	}
	
	public static Item generarInstancia(Long id, String titulo, String descripcion, Archivo archivo) {
		
		Item item = generarInstancia();
		item.setId(id);
		item.setTitulo(titulo);
		item.setDescripcion(descripcion);
		item.setArchivo(archivo);
		
		return item;
	}
}