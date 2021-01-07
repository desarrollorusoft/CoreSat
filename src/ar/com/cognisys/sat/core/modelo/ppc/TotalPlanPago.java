package ar.com.cognisys.sat.core.modelo.ppc;

public class TotalPlanPago {

	private float capital;
	private float recargo;
	private float multa;

	public float total() {
		return this.capital + this.recargo + this.multa;
	}

	public void sumarCapital(float capital) {
		this.capital += capital;
	}

	public void sumarRecargo(float recargo) {
		this.recargo += recargo;
	}

	public void sumarMulta(float multa) {
		this.multa += multa;
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

}
