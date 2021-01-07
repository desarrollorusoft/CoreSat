package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BajaUsuario {
    private Usuario usuario;
    private List<MotivoBajaUsuario> motivos;
    private Date fechaBaja;

    public BajaUsuario(Usuario usuario, Date fechaBaja) {
        this.setUsuario( usuario );
        this.setFechaBaja( fechaBaja );
        this.setMotivos(new ArrayList<MotivoBajaUsuario>());
    }

    public boolean tieneUsuario(int idUsuario) {
        return this.getUsuario() != null && this.getUsuario().getIdUsuario().equals(idUsuario);
    }

    public void agregarMotivo(MotivoBajaUsuario motivo) {
        this.getMotivos().add( motivo );
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<MotivoBajaUsuario> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoBajaUsuario> motivos) {
        this.motivos = motivos;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}