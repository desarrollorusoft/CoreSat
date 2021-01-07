package ar.com.cognisys.sat.v2.core.controlador.director;

import ar.com.cognisys.sat.v2.core.modelo.view.builder.CuentaAsociadaViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.CuentaAsociadaView;

public class DirectorCuentaAsociadaView {

	private CuentaAsociadaViewBuilder builder;

	public DirectorCuentaAsociadaView(CuentaAsociadaViewBuilder builder) {
		this.builder = builder;
	}

	public void construir() {
		this.builder.inicializar();
		this.builder.cargarDatos();
		this.builder.cargarImporte();
	}

	public CuentaAsociadaView getCuentaAsociada() {
		return this.builder.getCuentaAsociada();
	}
}