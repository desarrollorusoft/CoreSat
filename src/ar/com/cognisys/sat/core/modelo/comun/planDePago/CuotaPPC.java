package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;

public class CuotaPPC extends Cuota {

	private Integer numero;
	private Float interes;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((interes == null) ? 0 : interes.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		CuotaPPC other = (CuotaPPC) obj;
		if (interes == null) {
			if (other.interes != null)
				return false;
		} else if (!interes.equals(other.interes))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Float getInteres() {
		return interes;
	}

	public void setInteres(Float interes) {
		this.interes = interes;
	}
}