package ar.com.cognisys.sat.core.modelo.comun.permiso;

import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class PermisoUsuario {

	private PermisoSAT permiso;
	private TiposCuentas tipoCuenta;
	private String datoCuenta;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datoCuenta == null) ? 0 : datoCuenta.hashCode());
		result = prime * result + ((permiso == null) ? 0 : permiso.hashCode());
		result = prime * result + ((tipoCuenta == null) ? 0 : tipoCuenta.hashCode());
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
		PermisoUsuario other = (PermisoUsuario) obj;
		if (datoCuenta == null) {
			if (other.datoCuenta != null)
				return false;
		} else if (!datoCuenta.equals(other.datoCuenta))
			return false;
		if (permiso == null) {
			if (other.permiso != null)
				return false;
		} else if (!permiso.equals(other.permiso))
			return false;
		if (tipoCuenta != other.tipoCuenta)
			return false;
		return true;
	}

	public PermisoSAT getPermiso() {
		return permiso;
	}

	public void setPermiso(PermisoSAT permiso) {
		this.permiso = permiso;
	}

	public TiposCuentas getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TiposCuentas tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getDatoCuenta() {
		return datoCuenta;
	}

	public void setNumeroCuenta(String datoCuenta) {
		this.datoCuenta = datoCuenta;
	}
}