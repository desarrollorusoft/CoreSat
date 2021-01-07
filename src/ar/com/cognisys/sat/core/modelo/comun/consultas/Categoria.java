package ar.com.cognisys.sat.core.modelo.comun.consultas;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;
import converter.UnderConvertible;

import java.io.Serializable;
import java.util.List;

public class Categoria extends ObjetoPersistible implements UnderConvertible, Serializable {

	private static final long serialVersionUID = 7079145206762930602L;

	private String nombre;
	private List<Subcategoria> subcategorias;
	private Dato dato;
	private List<Caracter> caracteresPosibles;

	private Permiso permisoAsociado;

    @Override
    public String getAsString() {
        return this.nombre;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Categoria categoria = (Categoria) o;

		if (nombre != null ? !nombre.equals(categoria.nombre) : categoria.nombre != null) return false;
		if (subcategorias != null ? !subcategorias.equals(categoria.subcategorias) : categoria.subcategorias != null) return false;
		if (dato != null ? !dato.equals(categoria.dato) : categoria.dato != null) return false;
		if (caracteresPosibles != null ? !caracteresPosibles.equals(categoria.caracteresPosibles) : categoria.caracteresPosibles != null) return false;
		return permisoAsociado != null ? permisoAsociado.equals(categoria.permisoAsociado) : categoria.permisoAsociado == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
		result = 31 * result + (subcategorias != null ? subcategorias.hashCode() : 0);
		result = 31 * result + (dato != null ? dato.hashCode() : 0);
		result = 31 * result + (caracteresPosibles != null ? caracteresPosibles.hashCode() : 0);
		result = 31 * result + (permisoAsociado != null ? permisoAsociado.hashCode() : 0);
		return result;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public Dato getDato() {
		return dato;
	}

	public void setDato(Dato dato) {
		this.dato = dato;
	}

	public List<Caracter> getCaracteresPosibles() {
		return caracteresPosibles;
	}

	public void setCaracteresPosibles(List<Caracter> caracteresPosibles) {
		this.caracteresPosibles = caracteresPosibles;
	}

	public Permiso getPermisoAsociado() {
		return permisoAsociado;
	}

	public void setPermisoAsociado(Permiso permisoAsociado) {
		this.permisoAsociado = permisoAsociado;
	}

}
