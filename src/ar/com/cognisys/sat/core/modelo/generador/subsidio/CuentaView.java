package ar.com.cognisys.sat.core.modelo.generador.subsidio;

import java.io.Serializable;

public class CuentaView {
    private String cuenta;
    private String nombre;
    private String propietario;
    private String expediente;
    private String contrato;
    private String abl;
    private String responsableAbl;

    public CuentaView() {}

    public CuentaView(String cuenta, String nombre, String propietario, String expediente, String contrato, String abl, String responsableAbl) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.propietario = propietario;
        this.expediente = expediente;
        this.contrato = contrato;
        this.abl = abl;
        this.responsableAbl = responsableAbl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaView that = (CuentaView) o;
        return getCuenta().equals(that.getCuenta()) &&
                getNombre().equals(that.getNombre()) &&
                getPropietario().equals(that.getPropietario()) &&
                getExpediente().equals(that.getExpediente()) &&
                getContrato().equals(that.getContrato()) &&
                getAbl().equals(that.getAbl()) &&
                getResponsableAbl().equals(that.getResponsableAbl());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNombre() == null) ? 0 : getNombre().hashCode());
        result = prime * result + ((getPropietario() == null) ? 0 : getPropietario().hashCode());
        result = prime * result + ((getCuenta() == null) ? 0 : getCuenta().hashCode());
        result = prime * result + ((getExpediente() == null) ? 0 : getExpediente().hashCode());
        result = prime * result + ((getContrato() == null) ? 0 : getContrato().hashCode());
        result = prime * result + ((getAbl() == null) ? 0 : getAbl().hashCode());
        result = prime * result + ((getResponsableAbl() == null) ? 0 : getResponsableAbl().hashCode());
        return result;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getAbl() {
        return abl;
    }

    public void setAbl(String abl) {
        this.abl = abl;
    }

    public String getResponsableAbl() {
        return responsableAbl;
    }

    public void setResponsableAbl(String responsableAbl) {
        this.responsableAbl = responsableAbl;
    }
}