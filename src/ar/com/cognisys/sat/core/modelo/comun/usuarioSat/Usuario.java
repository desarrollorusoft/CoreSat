package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.UsuarioSat;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.permiso.PermisoUsuario;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.TramiteSubsidio;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

import java.util.Date;
import java.util.List;

public class Usuario extends UsuarioSat {

    private static final long serialVersionUID = -4474743340210704716L;
    private Integer idUsuario;
    private CuentasUsuario cuentas;
    private String cuit;
    private String correo;
    private String nombre;
    private String apellido;
    private String telefono1;
    private String telefono2;
    private String clave;
    private boolean newsLetter;
    private boolean activo;
    private Date fechaAlta;
    private boolean migrado;
    private boolean aceptoTyC;
    private boolean dfe;
    private Comercio comercio;
    private Integer nivel;
    private List<PermisoUsuario> listaPermisos;
    private List<PlanDePagoAPagar> planes;
    private TramiteSubsidio tramiteSubsidio;

    public String telefono() {
        if (this.telefono2 == null || this.telefono2.isEmpty())
            return this.telefono1;

        return this.telefono2;
    }

    @Override
    public boolean esContribuyente() {
        if (this.getListaPerfiles().isEmpty())
        	return true;			
		else
			return this.getListaPerfiles().get(0).sos("Contribuyente");	
    }

    @Override
    public boolean esNatatorios() {
        return false;
    }

    public boolean sos(String clave) {
        return (this.getClave().trim().equals(clave.trim()));
    }

    public boolean esCalveMaestra(String clave2) {
        return (clave2.trim().equals("264cd8a1d3a37e8478df0bde00f151a4"));
    }

    public boolean tieneCorreo(String correo) {
        return (this.getCorreo().equalsIgnoreCase(correo));
    }

    public boolean tieneCorreo() {
        return (this.getCorreo() != null && !this.getCorreo().isEmpty() && !this.getCorreo().equals("vacio"));
    }

    public boolean tieneCuenta(Cuenta cuenta) {
        return this.getCuentas() != null && this.getCuentas().hay(cuenta);
    }

    public boolean tieneRSDatos() {
        return this.tieneComercio() && this.getComercio().tieneSolicitante();
    }

    public boolean tieneComercio() {
        return this.getComercio() != null;
    }

    public boolean tieneClave(String otra) {
        return this.getClave().trim().equals(otra);
    }

    @Deprecated
    public CuentaComercios obtenerCuentaComercio(String padron) {
        return null;
//		return this.getComercio().recuperar( padron );
    }

    public boolean tieneCuit() {
        return this.getCuit() != null && !this.getCuit().isEmpty() && !this.getCuit().contains("-");
    }

    public String getCuitOMensaje() {
        if( this.tieneCuit() )
            return this.getCuit();
        else
            return "SIN CUIT";
    }

    public String getClaseCuit() {
        if( this.tieneCuit() )
            return "contenedor-valor";
        else
            return "sin-cuit";
    }

    public boolean tieneCuentaComercio() {
        return this.getCuentas() != null && this.getCuentas().hayCuentas(TiposCuentas.COMERCIOS);
    }

    public boolean sosNivel(Integer nivel) {
        return this.getNivel().equals(nivel);
    }

    public boolean noTieneClave() {
        return this.getClave() != null && this.getClave().trim().isEmpty();
    }

    public boolean tienePlan(Integer numeroPlan) {
        for (PlanDePagoAPagar plan : this.getPlanes())
            if ( plan.getNroPlan().equals( numeroPlan ) )
                return true;

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (activo ? 1231 : 1237);
        result = prime * result + ((clave == null) ? 0 : clave.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((cuentas == null) ? 0 : cuentas.hashCode());
        result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
        result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
        result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
        result = prime * result + (newsLetter ? 1231 : 1237);
        result = prime * result + ((telefono1 == null) ? 0 : telefono1.hashCode());
        result = prime * result + ((telefono2 == null) ? 0 : telefono2.hashCode());
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
        Usuario other = (Usuario) obj;
        if (activo != other.activo)
            return false;
        if (clave == null) {
            if (other.clave != null)
                return false;
        } else if (!clave.equals(other.clave))
            return false;
        if (correo == null) {
            if (other.correo != null)
                return false;
        } else if (!correo.equals(other.correo))
            return false;
        if (cuentas == null) {
            if (other.cuentas != null)
                return false;
        } else if (!cuentas.equals(other.cuentas))
            return false;
        if (cuit == null) {
            if (other.cuit != null)
                return false;
        } else if (!cuit.equals(other.cuit))
            return false;
        if (fechaAlta == null) {
            if (other.fechaAlta != null)
                return false;
        } else if (!fechaAlta.equals(other.fechaAlta))
            return false;
        if (idUsuario == null) {
            if (other.idUsuario != null)
                return false;
        } else if (!idUsuario.equals(other.idUsuario))
            return false;
        if (newsLetter != other.newsLetter)
            return false;
        if (telefono1 == null) {
            if (other.telefono1 != null)
                return false;
        } else if (!telefono1.equals(other.telefono1))
            return false;
        if (telefono2 == null) {
            if (other.telefono2 != null)
                return false;
        } else if (!telefono2.equals(other.telefono2))
            return false;
        return true;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CuentasUsuario getCuentas() {
        return cuentas;
    }

    public void setCuentas(CuentasUsuario cuentas) {
        this.cuentas = cuentas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isNewsLetter() {
        return newsLetter;
    }

    public void setNewsLetter(boolean newsLetter) {
        this.newsLetter = newsLetter;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isMigrado() {
        return migrado;
    }

    public void setMigrado(boolean migrado) {
        this.migrado = migrado;
    }

    public boolean isAceptoTyC() {
        return aceptoTyC;
    }

    public void setAceptoTyC(boolean aceptoTyC) {
        this.aceptoTyC = aceptoTyC;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public List<PermisoUsuario> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<PermisoUsuario> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public boolean isDfe() {
        return dfe;
    }

    public void setDfe(boolean dfe) {
        this.dfe = dfe;
    }

    public List<PlanDePagoAPagar> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanDePagoAPagar> planes) {
        this.planes = planes;
    }

    public TramiteSubsidio getTramiteSubsidio() {
        return tramiteSubsidio;
    }

    public void setTramiteSubsidio(TramiteSubsidio tramiteSubsidio) {
        this.tramiteSubsidio = tramiteSubsidio;
    }
}