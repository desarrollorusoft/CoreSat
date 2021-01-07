package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class ClaveValorView implements IClaveValorView {

	private static final long serialVersionUID = -9212019828555343946L;
	
	private String clave;
	private String valor;
	
	public ClaveValorView() {}

	public ClaveValorView(String clave, String valor) {
		this.clave = clave;
		this.valor = valor;
	}

	@Override public String getClave() {
		return clave;
	}

	@Override public String getValor() {
		return valor;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}