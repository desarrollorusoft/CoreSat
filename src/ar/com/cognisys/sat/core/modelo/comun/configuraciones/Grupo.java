package ar.com.cognisys.sat.core.modelo.comun.configuraciones;

import java.util.List;

public class Grupo extends Item {

	private String nombre;
	private List<Grupo> listaHijos;
	private List<Item> listaItems;
	private Grupo padre;
	private Pantalla pantalla;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((listaHijos == null) ? 0 : listaHijos.hashCode());
		result = prime * result + ((listaItems == null) ? 0 : listaItems.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((padre == null) ? 0 : padre.hashCode());
		result = prime * result + ((pantalla == null) ? 0 : pantalla.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		if (listaHijos == null) {
			if (other.listaHijos != null)
				return false;
		} else if (!listaHijos.equals(other.listaHijos))
			return false;
		if (listaItems == null) {
			if (other.listaItems != null)
				return false;
		} else if (!listaItems.equals(other.listaItems))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (padre == null) {
			if (other.padre != null)
				return false;
		} else if (!padre.equals(other.padre))
			return false;
		if (pantalla == null) {
			if (other.pantalla != null)
				return false;
		} else if (!pantalla.equals(other.pantalla))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Grupo> getListaHijos() {
		return listaHijos;
	}
	
	public void setListaHijos(List<Grupo> listaHijos) {
		this.listaHijos = listaHijos;
	}
	
	public List<Item> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<Item> listaItems) {
		this.listaItems = listaItems;
	}

	public Grupo getPadre() {
		return padre;
	}

	public void setPadre(Grupo padre) {
		this.padre = padre;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}
}