package ar.com.cognisys.sat.core.modelo.comun.configuraciones;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;

public class Item extends ObjetoPersistible implements Comparable<Item> {
	
	private String titulo;
	private String descripcion;
	private Archivo archivo;
	private boolean disponible;
	private Integer orden;
	
	public boolean tieneArchivo() {
		return (this.getArchivo() != null);
	}
	
	@Override
	public int compareTo(Item o) {
		return this.getOrden().compareTo(o.getOrden());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((archivo == null) ? 0 : archivo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (disponible ? 1231 : 1237);
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Item other = (Item) obj;
		if (archivo == null) {
			if (other.archivo != null)
				return false;
		} else if (!archivo.equals(other.archivo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (disponible != other.disponible)
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}