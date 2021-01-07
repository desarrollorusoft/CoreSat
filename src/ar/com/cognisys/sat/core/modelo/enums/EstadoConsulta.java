package ar.com.cognisys.sat.core.modelo.enums;

import converter.UnderConvertible;

public enum EstadoConsulta implements UnderConvertible {

    CERRADO(1, "Resuelta", "consulta-cerrado"),
    ABIERTO(0, "Iniciada", ""),
    NOTIFICADO(2, "En Analisis", "consulta-notificada"),
    DEVUELTA(4, "Reapertura", "consulta-devuelta"),
    CANCELADA(5, "Cancelada", "consulta-cancelada");

    private Integer id;
    private String descripcion;
    private String clase;


    EstadoConsulta(Integer id, String descripcion, String clase) {
        this.id = id;
        this.descripcion = descripcion;
        this.clase = clase;
    }

    @Override
    public String getAsString() {
        return this.descripcion;
    }

    public static EstadoConsulta obtenerPorId(Integer id) {
        for (EstadoConsulta e : EstadoConsulta.values())
            if (e.getId().equals(id))
                return e;
        return null;
    }

    public boolean sos(String descripcion) {
        return this.getDescripcion().equalsIgnoreCase(descripcion);
    }

    public boolean isAbierta() {
        return this.equals(EstadoConsulta.ABIERTO);
    }

    public boolean isCerrada() {
        return this.equals(EstadoConsulta.CERRADO);
    }

    public boolean isNotificada() {
        return this.equals(EstadoConsulta.NOTIFICADO);
    }

    public boolean isDevuelta() {
        return this.equals(EstadoConsulta.DEVUELTA);
    }


    public boolean isTerminado() {
        return this.equals(EstadoConsulta.CERRADO) || this.equals(EstadoConsulta.CANCELADA);
    }


    public boolean isCancelada() {
        return this.equals(EstadoConsulta.CANCELADA);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

}
