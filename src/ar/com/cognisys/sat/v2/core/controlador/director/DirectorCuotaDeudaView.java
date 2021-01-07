package ar.com.cognisys.sat.v2.core.controlador.director;

import ar.com.cognisys.sat.v2.core.modelo.view.CuotaDeudaView;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.CuotaDeudaViewBuilder;

public class DirectorCuotaDeudaView {

	private CuotaDeudaViewBuilder builder;

	public DirectorCuotaDeudaView(CuotaDeudaViewBuilder builder) {
		this.builder = builder;
	}

	public void construir() {
		this.builder.inicializar();
		this.builder.cargarDatos();
		this.builder.cargarCuotaAsociada();
	}

	public CuotaDeudaView getCuotaDeuda() {
		return this.builder.getCuotaDeuda();
	}

}
