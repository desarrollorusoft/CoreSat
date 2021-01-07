package ar.com.cognisys.sat.v2.core.controlador.director;

import ar.com.cognisys.sat.v2.core.modelo.view.builder.UsuarioViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.UsuarioView;

public class DirectorUsuarioView {

	private UsuarioViewBuilder builder;

	public DirectorUsuarioView(UsuarioViewBuilder builder) {
		this.builder = builder;
	}

	public void construir() {
		this.builder.inicializar();
		this.builder.cargarDatos();
		this.builder.cargarCuentasAsociadas();
		this.builder.cargarRSDatos();
	}

	public UsuarioView getUsuario() {
		return this.builder.getUsuarioView();
	}

}
