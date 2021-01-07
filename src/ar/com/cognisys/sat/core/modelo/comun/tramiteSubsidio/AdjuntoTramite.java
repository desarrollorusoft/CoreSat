package ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio;

import java.io.Serializable;

public class AdjuntoTramite implements Serializable {

    private String url;
    private String nombre;

    public AdjuntoTramite(String url, String nombre) {
        this.url = url;
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}