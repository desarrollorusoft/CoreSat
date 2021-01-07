package ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.validador.CUIT;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TramiteSubsidio implements Serializable {

    private Integer id;
    private String cuitComercio;
    private String domicilio;
    private String titular;
    private String dni;
    private String cuit;
    private String correo;
    private String cbu;
    private String representante;
    private String banco;
    private EstadoTramite estado;
    private List<DatosComercio> datos;
    private String urlPDF;
    private String telefono;
    private String celular;
    private boolean dfe;

    public TramiteSubsidio(Integer id) {
        this.id = id;
        this.setDatos(new ArrayList<DatosComercio>());
    }

    public void validar() throws ExcepcionControladaAlerta {
        if (this.getRepresentante() == null || this.getRepresentante().isEmpty())
            throw new ExcepcionControladaAlerta("Debe ingresar su nombre completo");
        if (this.getDni() == null || this.getDni().isEmpty())
            throw new ExcepcionControladaAlerta("Debe ingresar el DNI");
        if (this.getCuit() == null || this.getCuit().isEmpty() || !CUIT.validar( CUIT.quitarMascara( this.getCuit() )))
            throw new ExcepcionControladaAlerta("Debe ingresar el CUIT correcto");
        if (this.getCorreo() == null || this.getCorreo().isEmpty() || !ValidadorContribuyente.esCorreoValido( this.getCorreo().toLowerCase() ))
            throw new ExcepcionControladaAlerta("Debe ingresar un correo válido");
        if (this.getCbu() == null || this.getCbu().isEmpty() || this.getCbu().length() != 22)
            throw new ExcepcionControladaAlerta("Debe ingresar el CBU válido");
        if (this.getBanco() == null || this.getBanco().isEmpty() || this.getBanco().length() < 3)
            throw new ExcepcionControladaAlerta("Debe indicar el banco");
        for (DatosComercio d: this.getDatos())
            if (!d.estaCompleto())
                throw new ExcepcionControladaAlerta("Debe completar los datos de la cuenta "+d.getCuenta());
    }


    public boolean isCompleto() {
        return this.getEstado() == EstadoTramite.ENVIADO;
    }

    public boolean isCerrado(){
        return this.getEstado() == EstadoTramite.CERRADO;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCuitComercio() {
        return cuitComercio;
    }

    public void setCuitComercio(String cuitComercio) {
        this.cuitComercio = cuitComercio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String calle, String numero, String piso, String depto, String cp) {
        this.domicilio = calle + " "+numero + (piso.isEmpty() ? "" : " "+piso) + (depto.isEmpty() ? "" : " "+depto) + "("+cp+")";
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String apellido, String nombre) {
        this.titular = "";
        if (nombre != null && !nombre.isEmpty())
            this.titular = nombre;

        if (apellido != null && !apellido.isEmpty())
            this.titular += this.titular.isEmpty() ? apellido : " " + apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public EstadoTramite getEstado() {
        return estado;
    }

    public void setEstado(EstadoTramite estado) {
        this.estado = estado;
    }

    public List<DatosComercio> getDatos() {
        return datos;
    }

    public void setDatos(List<DatosComercio> datos) {
        this.datos = datos;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isDfe() {
        return dfe;
    }

    public void setDfe(boolean dfe) {
        this.dfe = dfe;
    }
}