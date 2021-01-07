package ar.com.cognisys.sat.core.modelo.comun.planDePago;


public class CuotaAVencer implements Comparable<CuotaAVencer>{

	private String tasa;
	private String periodo;
	private Float capital;
	private Float recargo;
	private Float multa;
	private Float total;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((multa == null) ? 0 : multa.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((recargo == null) ? 0 : recargo.hashCode());
		result = prime * result + ((tasa == null) ? 0 : tasa.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		CuotaAVencer other = (CuotaAVencer) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (multa == null) {
			if (other.multa != null)
				return false;
		} else if (!multa.equals(other.multa))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
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
		return true;
	}

	public String getTasa() {
		if (tasa.trim().equals("ABL")) {
			return "A.L C.V.P y S.V ";
		}
		if ((tasa.trim().equals("ROD"))
			||(tasa.trim().equals("VEH"))){
			return "RODADOS";
		}
		return tasa;
	}
	
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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
	
	public Float getTotal() {
		return total;
	}
	
	public void setTotal(Float total) {
		this.total = total;
	}
	
	@Override
	public int compareTo(CuotaAVencer o) {
		if (o == null) {
			return 1;
		}
		int out = this.getPeriodo().compareTo(o.getPeriodo()); 
		if (out != 0)
			return out;
		return this.getTasa().compareTo(o.getTasa());
	}	
}