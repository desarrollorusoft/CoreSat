package ar.com.cognisys.sat.core.modelo.abstracto;

import java.io.Serializable;

public abstract class CuentaAutomotor extends Cuenta implements Serializable {

	private static final long serialVersionUID = 3616828627795478539L;
	
	private String dominio;
	
	@Override
	public String getLeyenda() {
		
		return this.getDominio() + " - " + this.getDescripcion();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dominio == null) ? 0 : dominio.hashCode());
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
		CuentaAutomotor other = (CuentaAutomotor) obj;
		if (dominio == null) {
			if (other.dominio != null)
				return false;
		} else if (!dominio.equals(other.dominio))
			return false;
		return true;
	}
	
	public abstract boolean isVehiculo();
	
	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
}