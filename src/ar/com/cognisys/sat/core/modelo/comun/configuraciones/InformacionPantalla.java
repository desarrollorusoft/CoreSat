package ar.com.cognisys.sat.core.modelo.comun.configuraciones;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

public class InformacionPantalla extends ObjetoPersistible {

	private String nombre;
	private List<Grupo> listaGrupos;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaGrupos == null) ? 0 : listaGrupos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformacionPantalla other = (InformacionPantalla) obj;
		if (listaGrupos == null) {
			if (other.listaGrupos != null)
				return false;
		} else if (!listaGrupos.equals(other.listaGrupos))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}
	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}	
}
