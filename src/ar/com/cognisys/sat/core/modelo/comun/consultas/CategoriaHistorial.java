package ar.com.cognisys.sat.core.modelo.comun.consultas;

import java.io.Serializable;
import java.util.Date;

public class CategoriaHistorial implements Serializable {

    private Integer id;
    private Consulta consulta;
    private Date fecha;
    private UsuarioHistorial usuario;
    private Categoria categoriaAnterior;
    private Categoria categoriaActual;

    public CategoriaHistorial(Integer id, Consulta consulta, Date fecha, UsuarioHistorial usuario, Categoria categoriaAnterior, Categoria categoriaActual) {
        this.id = id;
        this.consulta = consulta;
        this.fecha = fecha;
        this.usuario = usuario;
        this.categoriaAnterior = categoriaAnterior;
        this.categoriaActual = categoriaActual;
    }

    public String getAsString() {
        if (this.categoriaAnterior == null)
            return "Se ha asignado la categoría " + this.categoriaActual.getAsString();

        return "La categoría pasó de ser " + this.categoriaAnterior.getAsString() + " a " + this.categoriaActual.getAsString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UsuarioHistorial getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioHistorial usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoriaAnterior() {
        return categoriaAnterior;
    }

    public void setCategoriaAnterior(Categoria categoriaAnterior) {
        this.categoriaAnterior = categoriaAnterior;
    }

    public Categoria getCategoriaActual() {
        return categoriaActual;
    }

    public void setCategoriaActual(Categoria categoriaActual) {
        this.categoriaActual = categoriaActual;
    }
}
