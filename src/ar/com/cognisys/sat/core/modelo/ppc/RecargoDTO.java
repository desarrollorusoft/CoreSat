package ar.com.cognisys.sat.core.modelo.ppc;

import java.util.Date;

public class RecargoDTO {

	private int codMovimiento;
	private int lsCod130;
	private int lsRecargo;
	private int lsMulta;
	private int lsCod139;
	private float lnRecargo;
	private float liRecargo;
	private Date primerVencimiento;
	private Date segundoVencimiento;
	private Date fechaCorrespondiente;
	private int lnPlan;
	private float lnMulta;
	private float liTotMul;

	public void cargarMovimiento130() {
		this.lsCod130 = 1;
		this.lsRecargo = 1;
		this.lsMulta = 1;
	}
	
	public void cargarMovimiento141() {
		this.lsRecargo = 0;
		this.lsMulta = 0;
	}

	public boolean isCod130() {
		return this.lsCod130 == 1;
	}

	public void cargarRecargo130(TransaccionMultaDTO transaccion, Date vencimiento, float capital) {
		this.liRecargo = capital * coeficienteFecha( vencimiento ) * this.lnRecargo;
		if ( transaccion.isActual( "PV" ) )
			this.liRecargo = this.liRecargo / 2;
	}

	private float coeficienteFecha(Date vencimiento) {
		return ( this.fechaCorrespondiente.getTime() - vencimiento.getTime() ) / 86400000;
	}

	public boolean hayLsMulta() {
		return this.lsMulta == 1;
	}
	
	public boolean hayLsRecargo() {
		return this.lsRecargo != 0;
	}

	public boolean hayLnPlan() {
		return this.lnPlan != 0;
	}
	
	public void actualizarFechaCorrespondiente() {
		if ( this.fechaCorrespondiente.before( this.primerVencimiento )|| this.fechaCorrespondiente.equals( this.primerVencimiento ) )
			this.fechaCorrespondiente = this.primerVencimiento;
		else if ( this.fechaCorrespondiente.before( this.segundoVencimiento )|| this.fechaCorrespondiente.equals( this.segundoVencimiento ) )
			this.fechaCorrespondiente = this.segundoVencimiento;
	}
	
	public boolean isMovimiento(int codMovimiento) {
		return this.codMovimiento == codMovimiento;
	}
	
	public void setCodMovimiento(int codMovimiento) {
		this.codMovimiento = codMovimiento;
	}

	public void setLsRecargo(int lsRecargo) {
		this.lsRecargo = lsRecargo;
	}
	
	public void setLsMulta(int lsMulta) {
		this.lsMulta = lsMulta;
	}

	public void setLsCod139(int lsCod139) {
		this.lsCod139 = lsCod139;
	}

	public void setLnRecargo(float lnRecargo) {
		this.lnRecargo = lnRecargo;
	}

	public void setPrimerVencimiento(Date primerVencimiento) {
		this.primerVencimiento = primerVencimiento;
	}
	
	public void setSegundoVencimiento(Date segundoVencimiento) {
		this.segundoVencimiento = segundoVencimiento;
	}

	public void setFechaCorrespondiente(Date fechaCorrespondiente) {
		this.fechaCorrespondiente = fechaCorrespondiente;
	}

	public void setLnPlan(int lnPlan) {
		this.lnPlan = lnPlan;
	}

	public int getLnPlan() {
		return lnPlan;
	}

	public void setLnMulta(float lnMulta) {
		this.lnMulta = lnMulta;
	}

	public boolean hayLnMulta() {
		return this.lnMulta != 0;
	}

	public void setLiTotMul(float liTotMul) {
		this.liTotMul = liTotMul;
	}

	public boolean hayLiTotMul() {
		return this.liTotMul != 0;
	}

	public float getLiRecargo() {
		return liRecargo;
	}
	
	public float getLiTotMul() {
		return liTotMul;
	}

	public void setLiRecargo(float liRecargo) {
		this.liRecargo = liRecargo;
	}

	public boolean isCod139() {
		return this.lsCod139 == 1;
	}

}
