package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import java.io.Serializable;

public class DuplaCuotaAVencer implements Serializable {

	private static final long serialVersionUID = 5746388017615411720L;
	private CuotaAVencer cuotaAbl, cuotaProt;
		
	public CuotaAVencer getCuotaAbl() {
		return cuotaAbl;
	}

	public void setCuotaAbl(CuotaAVencer cuotaAbl) {
		this.cuotaAbl = cuotaAbl;
	}

	public CuotaAVencer getCuotaProt() {
		return cuotaProt;
	}

	public void setCuotaProt(CuotaAVencer cuotaProt) {
		this.cuotaProt = cuotaProt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cuotaAbl == null) ? 0 : cuotaAbl.hashCode());
		result = prime * result
				+ ((cuotaProt == null) ? 0 : cuotaProt.hashCode());
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
		DuplaCuotaAVencer other = (DuplaCuotaAVencer) obj;
		if (cuotaAbl == null) {
			if (other.cuotaAbl != null)
				return false;
		} else if (!cuotaAbl.equals(other.cuotaAbl))
			return false;
		if (cuotaProt == null) {
			if (other.cuotaProt != null)
				return false;
		} else if (!cuotaProt.equals(other.cuotaProt))
			return false;
		return true;
	}
	
	public Float getDuplaTotal() {
		float total = 0;
		
		total = getCuotaAbl().getTotal(); 
		
		if (getCuotaProt()!=null){
			total =+ getCuotaProt().getTotal();
		}
		
		return total;
	}
	
	public Float getTotalCapital() {
		float total = 0;
		
		total = getCuotaAbl().getCapital();
		
		if (getCuotaProt()!=null){
			total =+ getCuotaProt().getCapital();
		}
		
		return total;
	}
	
	public Float getTotalMulta() {
		float total = 0;
		
		total = getCuotaAbl().getMulta();
		
		if (getCuotaProt()!=null){
			total =+ getCuotaProt().getMulta();
		}
		
		return total;
	}
	
	public Float getTotalRecargo() {
		float total = 0;
		
		total = getCuotaAbl().getRecargo();
		
		if (getCuotaProt()!=null){
			total =+ getCuotaProt().getRecargo();
		}
		
		return total;
	}	
}
