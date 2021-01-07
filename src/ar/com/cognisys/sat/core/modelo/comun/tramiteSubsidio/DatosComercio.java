package ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatosComercio implements Serializable {
    private Integer cuenta;
    private String nombreFantasia;
    private String expediente;
    private boolean propietario; // inquilino o propietario
    private Date inicioContrato;
    private Date finContrato;
    private Integer cuentaAbl;
    private boolean responsableAbl;
    private String rubro;
    private String razonSocial;
    private String domicilio;
    private List<AdjuntoTramite> adjuntos;

    public DatosComercio() {
        this.propietario = true;
        this.responsableAbl = true;
        this.adjuntos = new ArrayList<AdjuntoTramite>();
    }

    public boolean estaCompleto() {
        try {
            this.validar();
            return true;
        } catch (ExcepcionControladaAlerta excepcionControladaAlerta) {
            return false;
        }
    }

    public void validar() throws ExcepcionControladaAlerta {
        if (this.getNombreFantasia() == null || this.getNombreFantasia().isEmpty())
            throw new ExcepcionControladaAlerta("Debe ingresar el Nombre de Fantasia");
        if (this.getExpediente() == null || this.getExpediente().isEmpty() || this.getExpediente().length() < 8)
            throw new ExcepcionControladaAlerta("Debe ingresar el expediente");
        if (!this.isPropietario() && this.getInicioContrato() == null)
            throw new ExcepcionControladaAlerta("Debe ingresar el inicio del contrato");
        if (!this.isPropietario() && this.getFinContrato() == null)
            throw new ExcepcionControladaAlerta("Debe ingresar el fin del contrato");
        if (this.getCuentaAbl() == null || this.getCuentaAbl().toString().length() < 5)
            throw new ExcepcionControladaAlerta("Debe ingresar la partida de A.L.C.V.P.y S.V. válida");
        if (this.getAdjuntos() == null || this.getAdjuntos().size() == 0)
            throw new ExcepcionControladaAlerta("Debe cargar al menos un adjunto");

        // VALIDAR
    }

    public void agregarAdjunto(Archivo archivo, String urlImagen) {
        this.getAdjuntos().add(new AdjuntoTramite(urlImagen, archivo.getNombre()));
    }

    public void eliminarAdjunto(AdjuntoTramite adjunto) {
        this.getAdjuntos().remove(adjunto);
    }

    public boolean sos(DatosComercio datosActivos) {
        return this.getCuenta().equals(datosActivos.getCuenta());
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public boolean isPropietario() {
        return propietario;
    }

    public void setPropietario(boolean propietario) {
        this.propietario = propietario;
        if (propietario)
            this.setResponsableAbl(true);
    }

    public Date getInicioContrato() {
        return inicioContrato;
    }

    public void setInicioContrato(Date inicioContrato) {
        this.inicioContrato = inicioContrato;
    }

    public Date getFinContrato() {
        return finContrato;
    }

    public void setFinContrato(Date finContrato) {
        this.finContrato = finContrato;
    }

    public Integer getCuentaAbl() {
        return cuentaAbl;
    }

    public void setCuentaAbl(Integer cuentaAbl) {
        this.cuentaAbl = cuentaAbl;
    }

    public boolean isResponsableAbl() {
        return responsableAbl;
    }

    public void setResponsableAbl(boolean responsableAbl) {
        this.responsableAbl = responsableAbl;
    }

    public List<AdjuntoTramite> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoTramite> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String calle, String numero, String piso, String depto, String cp) {
        this.domicilio = calle + " "+numero + (piso.isEmpty() ? "" : " "+piso) + (depto.isEmpty() ? "" : " "+depto) + "("+cp+")";
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }
}