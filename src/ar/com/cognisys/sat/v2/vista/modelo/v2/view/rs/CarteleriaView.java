package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ICarteleriaView;

public class CarteleriaView implements ICarteleriaView {

	private static final long serialVersionUID = 7064479304122559733L;
	private String tipo;
	private String metros;

	public CarteleriaView() {}
	
	public CarteleriaView(String tipo, String metros) {
		this.tipo = tipo;
		this.metros = metros;
	}
	
	@Override public String getTipo() {
		return tipo;
	}

	@Override public String getMetros() {
		return metros;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setMetros(String metros) {
		this.metros = metros;
	}
	
}
