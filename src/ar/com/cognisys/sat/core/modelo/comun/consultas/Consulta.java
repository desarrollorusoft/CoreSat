package ar.com.cognisys.sat.core.modelo.comun.consultas;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;
import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.enums.EstadoConsulta;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Consulta extends ObjetoPersistible implements Serializable {

    private static final long serialVersionUID = -8437608025250904393L;

    private String nombre;
    private String apellido;
    private String correo;
    private boolean telefonoSeleccionado;
    private String telefono;
    private String telefono2;
    private String cuit;
    private String tipoConsulta;
    private Categoria categoria;
    private String subcategoria;
    private String caracter;
    private String descripcion;
    private List<ArchivoConsulta> archivos;
    private String dato;
    private String tipoDato;
    private EstadoConsulta estado;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    private List<ConsultaAsociada> listaConsultasAsociadas;

    private List<IRutaNombreView> listaArchivos;

    public boolean isAbierta() {
        return !isCerrada();
    }

    public boolean isCerrada() {
        return this.getEstado().equals(EstadoConsulta.CERRADO);
    }

    public boolean noEstaCancelada() {
        return !this.getEstado().sos(EstadoConsulta.CANCELADA.getDescripcion());
    }

    public String getEstadoContribuyente() {

        if (this.getEstado().sos("Reapertura"))
            return "En Analisis";

        else return this.getEstado().getDescripcion();


    }

    public boolean puedeCancelar() {
        return !this.estado.isCancelada() && !this.estado.isCerrada();
    }

    public boolean puedeCambiarCategoria() {
        return this.puedeCancelar();
    }

    public String getNombreCompleto() {
        return this.getApellido().trim() + ", " + this.getNombre().trim();
    }

    public String getNombreCompletoFormatoNormal() {
        return this.getNombre().trim() + " " + this.getApellido().trim();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
        result = prime * result + ((archivos == null) ? 0 : archivos.hashCode());
        result = prime * result + ((caracter == null) ? 0 : caracter.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
        result = prime * result + ((dato == null) ? 0 : dato.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((fechaActualizacion == null) ? 0 : fechaActualizacion.hashCode());
        result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((subcategoria == null) ? 0 : subcategoria.hashCode());
        result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
        result = prime * result + ((telefono2 == null) ? 0 : telefono2.hashCode());
        result = prime * result + (telefonoSeleccionado ? 1231 : 1237);
        result = prime * result + ((tipoConsulta == null) ? 0 : tipoConsulta.hashCode());
        result = prime * result + ((tipoDato == null) ? 0 : tipoDato.hashCode());
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
        Consulta other = (Consulta) obj;
        if (apellido == null) {
            if (other.apellido != null)
                return false;
        } else if (!apellido.equals(other.apellido))
            return false;
        if (archivos == null) {
            if (other.archivos != null)
                return false;
        } else if (!archivos.equals(other.archivos))
            return false;
        if (caracter == null) {
            if (other.caracter != null)
                return false;
        } else if (!caracter.equals(other.caracter))
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        if (correo == null) {
            if (other.correo != null)
                return false;
        } else if (!correo.equals(other.correo))
            return false;
        if (cuit == null) {
            if (other.cuit != null)
                return false;
        } else if (!cuit.equals(other.cuit))
            return false;
        if (dato == null) {
            if (other.dato != null)
                return false;
        } else if (!dato.equals(other.dato))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fechaActualizacion == null) {
            if (other.fechaActualizacion != null)
                return false;
        } else if (!fechaActualizacion.equals(other.fechaActualizacion))
            return false;
        if (fechaCreacion == null) {
            if (other.fechaCreacion != null)
                return false;
        } else if (!fechaCreacion.equals(other.fechaCreacion))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (subcategoria == null) {
            if (other.subcategoria != null)
                return false;
        } else if (!subcategoria.equals(other.subcategoria))
            return false;
        if (telefono == null) {
            if (other.telefono != null)
                return false;
        } else if (!telefono.equals(other.telefono))
            return false;
        if (telefono2 == null) {
            if (other.telefono2 != null)
                return false;
        } else if (!telefono2.equals(other.telefono2))
            return false;
        if (telefonoSeleccionado != other.telefonoSeleccionado)
            return false;
        if (tipoConsulta == null) {
            if (other.tipoConsulta != null)
                return false;
        } else if (!tipoConsulta.equals(other.tipoConsulta))
            return false;
        if (tipoDato == null) {
            if (other.tipoDato != null)
                return false;
        } else if (!tipoDato.equals(other.tipoDato))
            return false;
        return true;
    }

    public boolean ultimoMensajeContribuyente() {
        int size = !getListaConsultasAsociadas().isEmpty() ? getListaConsultasAsociadas().size() - 1 : 0;
        if (!getListaConsultasAsociadas().isEmpty()) {
            ConsultaAsociada asociada = getListaConsultasAsociadas().get(size);
            return asociada.isDelConsultante();
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isTelefonoSeleccionado() {
        return telefonoSeleccionado;
    }

    public void setTelefonoSeleccionado(boolean telefonoSeleccionado) {
        this.telefonoSeleccionado = telefonoSeleccionado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ArchivoConsulta> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoConsulta> archivos) {
        this.archivos = archivos;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public List<ConsultaAsociada> getListaConsultasAsociadas() {
        return listaConsultasAsociadas;
    }

    public void setListaConsultasAsociadas(List<ConsultaAsociada> listaConsultasAsociadas) {
        this.listaConsultasAsociadas = listaConsultasAsociadas;
    }

    public List<IRutaNombreView> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<IRutaNombreView> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }
}
