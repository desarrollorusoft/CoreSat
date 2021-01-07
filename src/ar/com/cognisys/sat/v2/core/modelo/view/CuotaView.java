package ar.com.cognisys.sat.v2.core.modelo.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaView;

public class CuotaView implements ICuotaView {

	private static final long serialVersionUID = -7312381372073147560L;
	private String numero;
	private String importe;
	private Date vencimiento;

	// @formatter:off
	public CuotaView() { }
	// @formatter:on

	public CuotaView(String numero, String importe) {
		this.numero = numero;
		this.importe = importe;
	}
	
	public CuotaView(String numero, String importe, Date vencimiento) {
		this.numero = numero;
		this.importe = importe;
		this.vencimiento = vencimiento;
	}

	@Override
	public String getNumero() {
		return numero;
	}

	@Override
	public String getImporte() {
		return importe;
	}
	@Override
	public Date getVencimiento() {
		return vencimiento;
	}
}