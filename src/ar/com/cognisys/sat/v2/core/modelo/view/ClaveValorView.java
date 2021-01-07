package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class ClaveValorView implements IClaveValorView {

	private static final long serialVersionUID = -1763237029484911419L;
	private String clave;
	private String valor;

	// @formatter:off
	public ClaveValorView() { }
	// @formatter:on

	public ClaveValorView(String clave, String valor) {
		this.clave = clave;
		this.valor = valor;
	}

	@Override
	public String getClave() {
		return clave;
	}

	@Override
	public String getValor() {
		return valor;
	}
}