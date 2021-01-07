package ar.com.cognisys.sat.core.modelo.ppc;

import java.util.Date;

public class Vencimientos {

	private Date primerVencimiento;
	private Date segundoVencimiento;

	public Vencimientos(Date primerVencimiento, Date segundoVencimiento) {
		this.primerVencimiento = primerVencimiento;
		this.segundoVencimiento = segundoVencimiento;
	}

	public Date getPrimerVencimiento() {
		return primerVencimiento;
	}

	public Date getSegundoVencimiento() {
		return segundoVencimiento;
	}

}
