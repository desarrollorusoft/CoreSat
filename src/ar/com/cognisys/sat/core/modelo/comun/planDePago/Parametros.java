package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import java.text.NumberFormat;
import java.util.Date;

public class Parametros {

	private float interes = 0;
	private float recargo = 0;
	private float multa = 0;
	private float capital = 0;
	private int sistema = 0;
	private int sist_rec = 0;
	private int sist_mul = 0;
	private int lc_recargo = 0;

	private Date fecha_vencimiento = new Date();
	private NumberFormat nf = NumberFormat.getCurrencyInstance();

	public Parametros() {
		this.nf.setMaximumFractionDigits(3);
		this.nf.setMinimumFractionDigits(0);
	}

	public void setFechaVencimiento(Date fec) {
		this.fecha_vencimiento = fec;
	}

	public Date getFechaVencimiento() {
		return this.fecha_vencimiento;
	}

	public void setMulta(double mult) {
		this.multa = this.redondeo((float) mult);
	}

	public float getMulta() {
		return this.multa;
	}

	public void setInteres(double inter) {
		this.interes = this.redondeo((float) inter);

	}

	public float getInteres() {
		return this.interes;
	}

	public void setCapital(double capi) {
		this.capital = this.redondeo((float) capi);
	}

	public float getCapital() {
		return this.capital;
	}

	public void setRecargo(double recar) {
		this.recargo = this.redondeo((float) recar);
	}

	public float getRecargo() {
		return this.recargo;
	}

	public void setSistema(int sist) {
		this.sistema = sist;
	}

	public int getSistema() {
		return this.sistema;
	}

	public void setSistRec(int sistrec) {
		this.sist_rec = sistrec;
	}

	public int getSistRec() {
		return this.sist_rec;
	}

	public void setSistMul(int sistmul) {
		this.sist_mul = sistmul;
	}

	public int getSistMul() {
		return this.sist_mul;
	}

	public void setLcRecargo(int creca) {
		this.lc_recargo = creca;
	}

	public int getLcRecargo() {
		return this.lc_recargo;
	}

	private float redondeo(float valor) {
		return valor;
	}
}