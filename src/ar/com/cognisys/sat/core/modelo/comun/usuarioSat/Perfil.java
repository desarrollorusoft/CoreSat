package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

import java.io.Serializable;
import java.util.List;

public class Perfil implements Serializable {

	private static final long serialVersionUID = 5631002424640358801L;

	private String nombre;
	private List<Permiso> listaPermisos;

	public boolean sos(String nombre) {
		return this.getNombre().equals( nombre );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((listaPermisos == null) ? 0 : listaPermisos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Perfil other = (Perfil) obj;
		if (listaPermisos == null) {
			if (other.listaPermisos != null)
				return false;
		} else if (!listaPermisos.equals(other.listaPermisos))
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

	public List<Permiso> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(List<Permiso> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}
}