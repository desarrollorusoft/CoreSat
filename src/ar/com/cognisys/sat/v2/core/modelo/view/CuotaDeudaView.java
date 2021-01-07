package ar.com.cognisys.sat.v2.core.modelo.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaDeudaView;

public class CuotaDeudaView implements ICuotaDeudaView {

	private static final long serialVersionUID = -7564719901787876982L;
	private String periodo;
	private String capital;
	private String recargo;
	private String multa;
	private String total;
	private Date vencimiento;
	private boolean seleccionado;
	private boolean enMora;
	private String tasa;
	private ICuotaDeudaView cuotaAsociada;

	// @formatter:off
	public CuotaDeudaView() { }
	// @formatter:on

	public CuotaDeudaView(String periodo, String capital, String recargo, String multa, String total, Date vencimiento, boolean enMora, String tasa, ICuotaDeudaView cuotaAsociada) {
		this.periodo = periodo;
		this.capital = capital;
		this.recargo = recargo;
		this.multa = multa;
		this.total = total;
		this.vencimiento = vencimiento;
		this.enMora = enMora;
		this.tasa = tasa;
		this.cuotaAsociada = cuotaAsociada;
	}
	
	@Override
	public String getPeriodo() {
		return periodo;
	}

	@Override
	public String getCapital() {
		return capital;
	}

	@Override
	public String getRecargo() {
		return recargo;
	}

	@Override
	public String getMulta() {
		return multa;
	}

	@Override
	public String getTotal() {
		return total;
	}

	@Override
	public Date getVencimiento() {
		return vencimiento;
	}

	@Override
	public boolean isSeleccionado() {
		return seleccionado;
	}

	@Override
	public boolean isEnMora() {
		return enMora;
	}

	@Override
	public String getTasa() {
		return tasa;
	}

	@Override
	public ICuotaDeudaView getCuotaAsociada() {
		return cuotaAsociada;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public void setRecargo(String recargo) {
		this.recargo = recargo;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void setEnMora(boolean enMora) {
		this.enMora = enMora;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public void setCuotaAsociada(ICuotaDeudaView cuotaAsociada) {
		this.cuotaAsociada = cuotaAsociada;
	}
	
}
