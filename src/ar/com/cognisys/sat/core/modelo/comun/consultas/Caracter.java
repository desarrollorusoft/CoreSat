package ar.com.cognisys.sat.core.modelo.comun.consultas;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

import java.io.Serializable;

public class Caracter extends ObjetoPersistible implements Serializable {

    private static final long serialVersionUID = -1228386810842211118L;

    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Caracter caracter = (Caracter) o;

        return nombre != null ? nombre.equals(caracter.nombre) : caracter.nombre == null;

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
