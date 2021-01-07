package ar.com.cognisys.sat.core.modelo.comun.simulacion;

public class CuotaFinanciacion {

	private Integer cuotas;
	private Float importe;
	private Float coeficiente;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coeficiente == null) ? 0 : coeficiente.hashCode());
		result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
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
		CuotaFinanciacion other = (CuotaFinanciacion) obj;
		if (coeficiente == null) {
			if (other.coeficiente != null)
				return false;
		} else if (!coeficiente.equals(other.coeficiente))
			return false;
		if (cuotas == null) {
			if (other.cuotas != null)
				return false;
		} else if (!cuotas.equals(other.cuotas))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		return true;
	}

	public Integer getCuotas() {
		return cuotas;
	}

	public void setCuotas(Integer cuotas) {
		this.cuotas = cuotas;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Float getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(Float coeficiente) {
		this.coeficiente = coeficiente;
	}
}