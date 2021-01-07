package ar.com.cognisys.sat.core.modelo.comun.planDePago;

public class PlanDePagoAPagar {

	private Integer nroPlan;
	private String solicitante;
	private String estadoPlan;
	private Integer cantCuotas;
	private Float importeCancelacion;
	private String alias;
	private boolean asociado;
	private Integer cuenta;
	private Integer sistema;

	public boolean sos(PlanDePagoAPagar otro) {
		return this.getNroPlan().equals( otro.getNroPlan() );
	}

	public boolean sos(Integer numeroPlan) {
		return this.getNroPlan().equals( numeroPlan );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantCuotas == null) ? 0 : cantCuotas.hashCode());
		result = prime * result + ((estadoPlan == null) ? 0 : estadoPlan.hashCode());
		result = prime * result + ((nroPlan == null) ? 0 : nroPlan.hashCode());
		result = prime * result + ((solicitante == null) ? 0 : solicitante.hashCode());
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
		PlanDePagoAPagar other = (PlanDePagoAPagar) obj;
		if (cantCuotas == null) {
			if (other.cantCuotas != null)
				return false;
		} else if (!cantCuotas.equals(other.cantCuotas))
			return false;
		if (estadoPlan == null) {
			if (other.estadoPlan != null)
				return false;
		} else if (!estadoPlan.equals(other.estadoPlan))
			return false;
		if (nroPlan == null) {
			if (other.nroPlan != null)
				return false;
		} else if (!nroPlan.equals(other.nroPlan))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanDePagoAPagar{" +
				"nroPlan=" + nroPlan +
				", solicitante='" + solicitante + '\'' +
				", estadoPlan='" + estadoPlan + '\'' +
				", cantCuotas=" + cantCuotas +
				", importeCancelacion=" + importeCancelacion +
				'}';
	}

	public Integer getNroPlan() {
		return nroPlan;
	}
	
	public void setNroPlan(Integer nroPlan) {
		this.nroPlan = nroPlan;
	}
	
	public String getSolicitante() {
		return solicitante;
	}
	
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	
	public String getEstadoPlan() {
		return estadoPlan;
	}
	
	public void setEstadoPlan(String estadoPlan) {
		this.estadoPlan = estadoPlan;
	}
	
	public Integer getCantCuotas() {
		return cantCuotas;
	}
	
	public void setCantCuotas(Integer cantCuotas) {
		this.cantCuotas = cantCuotas;
	}

	public Float getImporteCancelacion() {
		return importeCancelacion;
	}

	public void setImporteCancelacion(Float importeCancelacion) {
		this.importeCancelacion = importeCancelacion;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isAsociado() {
		return asociado;
	}

	public void setAsociado(boolean asociado) {
		this.asociado = asociado;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Integer getSistema() {
		return sistema;
	}

	public void setSistema(Integer sistema) {
		this.sistema = sistema;
	}
}