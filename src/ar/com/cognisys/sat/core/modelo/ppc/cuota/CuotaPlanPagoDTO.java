package ar.com.cognisys.sat.core.modelo.ppc.cuota;

import java.util.Date;

public abstract class CuotaPlanPagoDTO {

	protected float capital;
	protected float recargo;
	protected float multa;
	protected Date vencimiento;

	protected CuotaPlanPagoDTO() { }
	
	protected CuotaPlanPagoDTO(float capital, float recargo, float multa, Date vencimiento) {
		this.capital = capital;
		this.recargo = recargo;
		this.multa = multa;
		this.vencimiento = vencimiento;
	}
	
	public float getCapital() {
		return capital;
	}

	public float getRecargo() {
		return recargo;
	}

	public float getMulta() {
		return multa;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

}
