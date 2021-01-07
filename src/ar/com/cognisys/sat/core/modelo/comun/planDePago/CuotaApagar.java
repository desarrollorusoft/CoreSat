package ar.com.cognisys.sat.core.modelo.comun.planDePago;

public class CuotaApagar implements Comparable<CuotaApagar> {

	private String tasa;
	private Float capital;
	private Float recargo;
	private Float multa;
	private Integer numeroTransaccion;
	private String fechaVencimiento;
	private boolean enProcesoJudicial;
	private boolean pagar;
	private boolean vencida;
	private Float total;
	private Integer numeroTasa;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + (enProcesoJudicial ? 1231 : 1237);
		result = prime * result + ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
		result = prime * result + ((multa == null) ? 0 : multa.hashCode());
		result = prime * result + ((numeroTasa == null) ? 0 : numeroTasa.hashCode());
		result = prime * result + ((numeroTransaccion == null) ? 0 : numeroTransaccion.hashCode());
		result = prime * result + (pagar ? 1231 : 1237);
		result = prime * result + ((recargo == null) ? 0 : recargo.hashCode());
		result = prime * result + ((tasa == null) ? 0 : tasa.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + (vencida ? 1231 : 1237);
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
		CuotaApagar other = (CuotaApagar) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (enProcesoJudicial != other.enProcesoJudicial)
			return false;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		if (multa == null) {
			if (other.multa != null)
				return false;
		} else if (!multa.equals(other.multa))
			return false;
		if (numeroTasa == null) {
			if (other.numeroTasa != null)
				return false;
		} else if (!numeroTasa.equals(other.numeroTasa))
			return false;
		if (numeroTransaccion == null) {
			if (other.numeroTransaccion != null)
				return false;
		} else if (!numeroTransaccion.equals(other.numeroTransaccion))
			return false;
		if (pagar != other.pagar)
			return false;
		if (recargo == null) {
			if (other.recargo != null)
				return false;
		} else if (!recargo.equals(other.recargo))
			return false;
		if (tasa == null) {
			if (other.tasa != null)
				return false;
		} else if (!tasa.equals(other.tasa))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (vencida != other.vencida)
			return false;
		return true;
	}

	@Override
	public int compareTo(CuotaApagar o) {
		if (o == null) {
			return 1;
		}
		int out = this.getFechaVencimiento().compareTo(o.getFechaVencimiento());
		if (out != 0)
			return out;
		return this.getTasa().compareTo(o.getTasa());
	}
	
	public boolean isEnProcesoJudicial() {
		return enProcesoJudicial;
	}

	public void setEnProcesoJudicial(boolean enProcesoJudicial) {
		this.enProcesoJudicial = enProcesoJudicial;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public void setNumeroTransaccion(Integer numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public int getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(int numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public String getTasa() {
		
		if (tasa.trim().equals("ABL")) {
			return "A.L C.V.P y S.V ";
		} else if ((tasa.trim().equals("ROD")) ||(tasa.trim().equals("VEH"))){
			return "RODADOS";
		} else {
			return tasa;
		}
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public Float getTotal() {
		return getCapital() + getRecargo() + getMulta();
	}

	public Float getCapital() {
		return capital;
	}

	public void setCapital(Float capital) {
		this.capital = capital;
	}

	public Float getRecargo() {
		return recargo;
	}

	public void setRecargo(Float recargo) {
		this.recargo = recargo;
	}

	public Float getMulta() {
		return multa;
	}

	public void setMulta(Float multa) {
		this.multa = multa;
	}

	public boolean getPagar() {
		return pagar;
	}

	public void setPagar(boolean pagar) {
		this.pagar = pagar;
	}

	public boolean isVencida() {
		return vencida;
	}

	public void setVencida(boolean vencida) {
		this.vencida = vencida;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getNumeroTasa() {
		return numeroTasa;
	}

	public void setNumeroTasa(Integer numeroTasa) {
		this.numeroTasa = numeroTasa;
	}
}