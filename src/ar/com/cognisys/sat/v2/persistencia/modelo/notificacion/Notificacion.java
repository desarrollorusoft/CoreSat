package ar.com.cognisys.sat.v2.persistencia.modelo.notificacion;

import ar.com.cognisys.sat.core.administrador.AdministradorContribuyente;
import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeNotificacion;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

import java.io.Serializable;
import java.util.Date;

public class Notificacion implements Serializable {

    private Integer id;
    private Integer idAlerta;
    private Integer idUsuario;
    private boolean visto;
    private String titulo;
    private String descripcion;
    private Date fecha;

    public Notificacion(Integer id, Integer idAlerta, Integer idUsuario, boolean visto, String titulo, String descripcion, Date fecha) {
        this.id = id;
        this.visto = visto;
        this.titulo = titulo;
        this.idUsuario = idUsuario;
        this.idAlerta = idAlerta;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void sendMail() throws ExcepcionControladaError, ExcepcionControladaAlerta {
        String mail = AdministradorContribuyente.buscarUsuario(this.getIdUsuario()).getCorreo();
        AdministradorMails.enviar( new MensajeNotificacion(mail,this) );
    }

}
