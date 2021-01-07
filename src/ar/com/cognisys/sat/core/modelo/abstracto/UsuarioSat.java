package ar.com.cognisys.sat.core.modelo.abstracto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.seguimiento.Seguimiento;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Perfil;

public abstract class UsuarioSat implements Serializable {

	private static final long serialVersionUID = -1576467380360264745L;
	private String nombreUsuario;
	private List<Perfil> listaPerfiles;
	private Date fechaUltimoIngreso;
	private Seguimiento seguimiento;
	
	public abstract boolean esContribuyente();
	public abstract boolean esNatatorios();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaUltimoIngreso == null) ? 0 : fechaUltimoIngreso.hashCode());
		result = prime * result + ((listaPerfiles == null) ? 0 : listaPerfiles.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		result = prime * result + ((seguimiento == null) ? 0 : seguimiento.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioSat other = (UsuarioSat) obj;
		if (fechaUltimoIngreso == null) {
			if (other.fechaUltimoIngreso != null)
				return false;
		} else if (!fechaUltimoIngreso.equals(other.fechaUltimoIngreso))
			return false;
		if (listaPerfiles == null) {
			if (other.listaPerfiles != null)
				return false;
		} else if (!listaPerfiles.equals(other.listaPerfiles))
			return false;
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null)
				return false;
		} else if (!nombreUsuario.equals(other.nombreUsuario))
			return false;
		if (seguimiento == null) {
			if (other.seguimiento != null)
				return false;
		} else if (!seguimiento.equals(other.seguimiento))
			return false;
		return true;
	}
	public Date getFechaUltimoIngreso() {
		return fechaUltimoIngreso;
	}

	public void setFechaUltimoIngreso(Date fechaUltimoIngreso) {
		this.fechaUltimoIngreso = fechaUltimoIngreso;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public List<Perfil> getListaPerfiles() {
		return listaPerfiles;
	}
	
	public void setListaPerfiles(List<Perfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}
	
	public Seguimiento getSeguimiento() {
		return seguimiento;
	}
	
	public void setSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}
}