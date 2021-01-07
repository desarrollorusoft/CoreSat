package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

public class RutaNombreView implements IRutaNombreView {

	private static final long serialVersionUID = 3837675559924615134L;
	private String nombre;
	private String ruta;

	// @formatter:off
	public RutaNombreView() { }
	// @formatter:on

	public RutaNombreView(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = ruta;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getRuta() {
		return ruta;
	}

}
