package ar.com.cognisys.sat.core.modelo.comun.simulacion;

public class CuotaPDP {

	private String tasa;
	private String periodo;
	private Float total;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
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
		CuotaPDP other = (CuotaPDP) obj;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
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
	
	public Float getTotal() {
		return total;
	}
	
	public void setTotal(Float total) {
		this.total = total;
	}
}