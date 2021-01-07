package ar.com.cognisys.sat.core.modelo.comun.planDePago;

public class PlanSimulacion {

	private Integer numeroCuotas;
	private Float importeCuota;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroCuotas == null) ? 0 : numeroCuotas.hashCode());
		result = prime * result + ((importeCuota == null) ? 0 : importeCuota.hashCode());
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
		PlanSimulacion other = (PlanSimulacion) obj;
		if (numeroCuotas == null) {
			if (other.numeroCuotas != null)
				return false;
		} else if (!numeroCuotas.equals(other.numeroCuotas))
			return false;
		if (importeCuota == null) {
			if (other.importeCuota != null)
				return false;
		} else if (!importeCuota.equals(other.importeCuota))
			return false;
		return true;
	}
	
	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public Float getImporteCuota() {
		return importeCuota;
	}
	
	public void setImporteCuota(Float importeCuota) {
		this.importeCuota = importeCuota;
	}
}