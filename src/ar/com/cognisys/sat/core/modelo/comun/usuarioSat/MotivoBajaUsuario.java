package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

public class MotivoBajaUsuario {
    private Integer codigo;
    private String descripcion;
    private String aclaracion;
    private boolean seleccionado;

    public MotivoBajaUsuario(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getAclaracion() {
        return aclaracion == null ? "" : aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        this.aclaracion = aclaracion;
    }
}