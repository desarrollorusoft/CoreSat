package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;

public class BonificacionRS {

	private boolean corresponde;
	private List<Cuota> cuotas;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (corresponde ? 1231 : 1237);
		result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
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
		BonificacionRS other = (BonificacionRS) obj;
		if (corresponde != other.corresponde)
			return false;
		if (cuotas == null) {
			if (other.cuotas != null)
				return false;
		} else if (!cuotas.equals(other.cuotas))
			return false;
		return true;
	}

	public boolean isCorresponde() {
		return corresponde;
	}

	public void setCorresponde(boolean corresponde) {
		this.corresponde = corresponde;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
}