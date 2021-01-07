package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRubroView;

public class RubroView implements IRubroView {

	private static final long serialVersionUID = 332628382448808481L;
	private String nombre;
	private boolean permitido;

	public RubroView() {}
	
	public RubroView(String nombre, boolean permitido) {
		this.nombre = nombre;
		this.permitido = permitido;
	}

	@Override public String getNombre() {
		return nombre;
	}

	@Override public boolean isPermitido() {
		return permitido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}

}
