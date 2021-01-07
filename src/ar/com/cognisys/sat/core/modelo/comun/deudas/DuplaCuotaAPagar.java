package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.io.Serializable;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;

public class DuplaCuotaAPagar implements Serializable, Comparable<DuplaCuotaAPagar> {

	private static final long serialVersionUID = 5746388017615411720L;
	private CuotaApagar cuotaAbl, cuotaProt;
	private String periodo;
	
	public boolean isEnProcesoJudicial() {
		if (cuotaAbl != null) {
			return cuotaAbl.isEnProcesoJudicial();
		} else {
			if (cuotaProt != null) {
				return cuotaProt.isEnProcesoJudicial();
			} else {
				return false;
			}
		}
	}	

	public void setEnProcesoJudicial(boolean enProcesoJudicial) {
		
		if (cuotaProt == null) {
			cuotaAbl.setEnProcesoJudicial(enProcesoJudicial);
		} else {
			cuotaAbl.setEnProcesoJudicial(enProcesoJudicial);
			cuotaProt.setEnProcesoJudicial(enProcesoJudicial);
		}
	}
	
	public void setPagar(boolean pagar) {
		
		if (this.getCuotaAbl() != null) {
			this.getCuotaAbl().setPagar(pagar);
		}
		
		if (this.getCuotaProt() != null) {
			this.getCuotaProt().setPagar(pagar);
		}
	}
	
	public boolean isPagar() {
		
		boolean respuesta = false;
		
		if (this.getCuotaAbl() != null) {
			respuesta = this.getCuotaAbl().getPagar();
			
		} else if (this.getCuotaProt() != null) {
			respuesta = this.getCuotaProt().getPagar();
		}
		
		return respuesta;
	}
	
	public String getFechaVencimiento() {
		 
		if ( this.getCuotaAbl() != null ) {
			return this.getCuotaAbl().getFechaVencimiento();
		} else {
			if ( this.getCuotaProt() != null ) {
				return this.getCuotaProt().getFechaVencimiento();
			} else {
				return null;
			}
		}
	}
	
	public CuotaApagar getCuotaAbl() {
		return cuotaAbl;
	}

	public void setCuotaAbl(CuotaApagar cuotaAbl) {
		this.cuotaAbl = cuotaAbl;
	}

	public CuotaApagar getCuotaProt() {
		return cuotaProt;
	}

	public void setCuotaProt(CuotaApagar cuotaProt) {
		this.cuotaProt = cuotaProt;
	}

	public Float getDuplaTotal() {
		float total = 0;
		
		if ( this.getCuotaAbl() != null ) {
			total += this.getCuotaAbl().getTotal(); 
		}
		
		if ( this.getCuotaProt() != null ) {
			total += this.getCuotaProt().getTotal();
		}
		
		return total;
	}
	
	public Float getTotalCapital() {
		float total = 0;
		
		if ( this.getCuotaAbl() != null ) {
			total += this.getCuotaAbl().getCapital();
		}
		
		if ( this.getCuotaProt() != null ) {
			total += this.getCuotaProt().getCapital();
		}
		
		return total;
	}
	
	public Float getTotalMulta() {
		float total = 0;
		
		if ( this.getCuotaAbl() != null ) {
			total += this.getCuotaAbl().getMulta();
		}
		
		if ( this.getCuotaProt() != null ) {
			total += this.getCuotaProt().getMulta();
		}
		
		return total;
	}
	
	public Float getTotalRecargo() {
		float total = 0;
		
		if ( this.getCuotaAbl() != null ) {
			total += this.getCuotaAbl().getRecargo();
		}
		
		if ( this.getCuotaProt() != null ) {
			total += this.getCuotaProt().getRecargo();
		}
		
		return total;
	}

	@Override
	public int compareTo(DuplaCuotaAPagar o) {
		return this.getPeriodo().compareTo(o.getPeriodo());
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuotaAbl == null) ? 0 : cuotaAbl.hashCode());
		result = prime * result + ((cuotaProt == null) ? 0 : cuotaProt.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
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
		DuplaCuotaAPagar other = (DuplaCuotaAPagar) obj;
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
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}

	public boolean isVencida() {
		
		boolean resultado = false;
		
		if (this.getCuotaAbl() != null) {
			resultado = this.getCuotaAbl().isVencida();
			
		} else if (this.getCuotaProt() != null) {
			resultado = this.getCuotaProt().isVencida();
		}
		
		return resultado;
	}
	
	public boolean tieneABL() {
		
		return (this.getCuotaAbl() != null);
	}
	
	public boolean tieneProteccionCiudadana() {
		
		return (this.getCuotaProt() != null);
	}
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}