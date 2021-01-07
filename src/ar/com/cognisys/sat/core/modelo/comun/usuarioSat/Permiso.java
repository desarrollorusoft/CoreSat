package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

import java.io.Serializable;

public class Permiso implements Serializable {

    private static final long serialVersionUID = 863189545960874309L;

    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permiso permiso = (Permiso) o;

        return nombre != null ? nombre.equals(permiso.nombre) : permiso.nombre == null;

    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}