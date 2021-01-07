package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta.ICuentaDetalleView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class CuentaDetalleView implements ICuentaDetalleView {

	private static final long serialVersionUID = -4571713208495373612L;
	private String contribuyente;
	private IClaveValorView[] datosExtra;
	private String caracter;
	private String telefono;
	private String domicilioFiscal;

	// @formatter:off
	public CuentaDetalleView() { }
	// @formatter:on

	public CuentaDetalleView(String contribuyente, IClaveValorView[] datosExtra, String caracter, String telefono, String domicilioFiscal) {
		this.contribuyente = contribuyente;
		this.datosExtra = datosExtra;
		this.caracter = caracter;
		this.telefono = telefono;
		this.domicilioFiscal = domicilioFiscal;
	}

	@Override
	public String getContribuyente() {
		return contribuyente;
	}

	@Override
	public IClaveValorView[] getDatosExtra() {
		return datosExtra;
	}

	@Override
	public String getCaracter() {
		return caracter;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public String getDomicilioFiscal() {
		return domicilioFiscal;
	}

	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}

	public void setDatosExtra(IClaveValorView[] datosExtra) {
		this.datosExtra = datosExtra;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}
}