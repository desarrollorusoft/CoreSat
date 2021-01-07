package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import java.util.Date;

public class CuotaPlanDePago {
	
	private Integer cuotaPlan;
	private Date vencimiento;
	private float capital;
	private float recargo;
	private float multa;
    private float interes;
    private Date fechaPago;
    private boolean pagar;
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(capital);
		result = prime * result + ((cuotaPlan == null) ? 0 : cuotaPlan.hashCode());
		result = prime * result + ((fechaPago == null) ? 0 : fechaPago.hashCode());
		result = prime * result + Float.floatToIntBits(interes);
		result = prime * result + Float.floatToIntBits(multa);
		result = prime * result + (pagar ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(recargo);
		result = prime * result + ((vencimiento == null) ? 0 : vencimiento.hashCode());
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
		CuotaPlanDePago other = (CuotaPlanDePago) obj;
		if (Float.floatToIntBits(capital) != Float.floatToIntBits(other.capital))
			return false;
		if (cuotaPlan == null) {
			if (other.cuotaPlan != null)
				return false;
		} else if (!cuotaPlan.equals(other.cuotaPlan))
			return false;
		if (fechaPago == null) {
			if (other.fechaPago != null)
				return false;
		} else if (!fechaPago.equals(other.fechaPago))
			return false;
		if (Float.floatToIntBits(interes) != Float.floatToIntBits(other.interes))
			return false;
		if (Float.floatToIntBits(multa) != Float.floatToIntBits(other.multa))
			return false;
		if (pagar != other.pagar)
			return false;
		if (Float.floatToIntBits(recargo) != Float.floatToIntBits(other.recargo))
			return false;
		if (vencimiento == null) {
			if (other.vencimiento != null)
				return false;
		} else if (!vencimiento.equals(other.vencimiento))
			return false;
		return true;
	}

	public Integer getCuotaPlan() {
		return cuotaPlan;
	}
	
	public void setCuotaPlan(Integer cuotaPlan) {
		this.cuotaPlan = cuotaPlan;
	}
	
	public float getRecargo() {
		return recargo;
	}
	
	public void setRecargo(Float recargo) {
		this.recargo = recargo;
	}
	public float getMulta() {
		return multa;
	}
	
	public void setMulta(Float multa) {
		this.multa = multa;
	}
	
	public float getInteres() {
		return interes;
	}
	
	public void setInteres(Float interes) {
		this.interes = interes;
	}
	
	public float getCapital() {
		return capital;
	}
	
	public void setCapital(Float capital) {
		this.capital = capital;
	}
	
	public Date getVencimiento() {
		return vencimiento;
	}
	
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	public Date getFechaPago() {
		return fechaPago;
	}
	
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public float getTotal() {
		return (multa+recargo+interes+capital);
	}

	public boolean isPagar() {
		return pagar;
	}
	
	public void setPagar(boolean pagar) {
		this.pagar = pagar;
	}
}