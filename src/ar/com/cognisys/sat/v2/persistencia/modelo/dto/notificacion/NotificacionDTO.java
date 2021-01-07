package ar.com.cognisys.sat.v2.persistencia.modelo.dto.notificacion;

import java.util.Date;

public class NotificacionDTO {
    private Integer id;
    private int idAlerta;
    private int idUsuario;
    private boolean visto;
    private String titulo;
    private String descripcion;
    private Date fecha;


    public NotificacionDTO(Integer id, int idAlerta, int idUsuario, boolean visto, String titulo, String descripcion, Date fecha) {
        this.id = id;
        this.idAlerta = idAlerta;
        this.idUsuario = idUsuario;
        this.visto = visto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
