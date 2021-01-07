package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.io.Serializable;
import java.util.Date;

public class DuplaCuota implements Serializable {

	private static final long serialVersionUID = 5746388017615411720L;
	private Cuota cuotaAbl, cuotaProt;
	
	public boolean getEsDupla() {
		return (getCuotaProt() != null && getCuotaAbl() != null);
	}
	
	public boolean isPagar() {
		if (getCuotaProt() == null) {
			return getCuotaAbl().isPagar();
		} else {
			return getCuotaAbl().isPagar() && getCuotaProt().isPagar();
		}
	}
	
	public void setPagar(boolean pagar) {
		if (getCuotaProt() == null) {
			getCuotaAbl().setPagar(pagar);
		} else {
			getCuotaAbl().setPagar(pagar);
			getCuotaProt().setPagar(pagar);
		}
	}
	
	public Cuota getCuotaAbl() {
		return cuotaAbl;
	}

	public void setCuotaAbl(Cuota cuotaAbl) {
		this.cuotaAbl = cuotaAbl;
	}

	public Cuota getCuotaProt() {
		return cuotaProt;
	}

	public void setCuotaProt(Cuota cuotaProt) {
		this.cuotaProt = cuotaProt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuotaAbl == null) ? 0 : cuotaAbl.hashCode());
		result = prime * result + ((cuotaProt == null) ? 0 : cuotaProt.hashCode());
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
		DuplaCuota other = (DuplaCuota) obj;
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
		
		if (this.getCuotaAbl() != null)
			total += getCuotaAbl().getTotal(); 
		
		if (this.getCuotaProt() != null)
			total += getCuotaProt().getTotal();
		
		return total;
	}
	
	public Float getTotalCapital() {
		float total = 0;
		
		if (this.getCuotaAbl() != null)
			total += getCuotaAbl().getCapital(); 
		
		if (this.getCuotaProt() != null)
			total += getCuotaProt().getCapital();
		
		return total;
	}
	
	public Float getTotalMulta() {
		float total = 0;
		
		if (this.getCuotaAbl() != null)
			total += getCuotaAbl().getMulta(); 
		
		if (this.getCuotaProt() != null)
			total += getCuotaProt().getMulta();
		
		return total;
	}
	
	public Float getTotalRecargo() {
		float total = 0;
		
		if (this.getCuotaAbl() != null)
			total += getCuotaAbl().getRecargo(); 
		
		if (this.getCuotaProt() != null)
			total += getCuotaProt().getRecargo();
		
		return total;
	}	
	
	public String getPeriodo() {
		 
		String periodo = "";
		
		if (this.getCuotaAbl() != null) {
			periodo = this.getCuotaAbl().getPeriodo();
			
		} else if (this.getCuotaProt() != null) {
			periodo = this.getCuotaProt().getPeriodo();
		}
		
		return periodo;
	}
	
	public String getEstado() {
		 
		String estado = "";
		
		if (this.getCuotaAbl() != null) {
			estado = this.getCuotaAbl().getEstado();
			
		} else if (this.getCuotaProt() != null) {
			estado = this.getCuotaProt().getEstado();
		}
		
		return estado;
	}
	
	public Date getFechaVencimiento() {
		 
		Date fecha = null;
		
		if (this.getCuotaAbl() != null) {
			fecha = this.getCuotaAbl().getFechaVencimiento();
			
		} else if (this.getCuotaProt() != null) {
			fecha = this.getCuotaProt().getFechaVencimiento();
		}
		
		return fecha;
	}
	
	public boolean tieneABL() {
		
		return (this.getCuotaAbl() != null);
	}
	
	public boolean tieneProteccionCiudadana() {
		
		return (this.getCuotaProt() != null);
	}
}