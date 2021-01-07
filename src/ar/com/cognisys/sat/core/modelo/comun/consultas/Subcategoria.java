package ar.com.cognisys.sat.core.modelo.comun.consultas;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

import java.io.Serializable;

public class Subcategoria extends ObjetoPersistible implements Serializable {

    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Subcategoria that = (Subcategoria) o;

        return nombre != null ? nombre.equals(that.nombre) : that.nombre == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
