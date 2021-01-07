package ar.com.cognisys.sat.v2.core.controlador.director;

import ar.com.cognisys.sat.v2.core.modelo.view.builder.ConsultaCabeceraViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaCabeceraView;

public class DirectorConsultaCabeceraView {

	private ConsultaCabeceraViewBuilder builder;

	public DirectorConsultaCabeceraView(ConsultaCabeceraViewBuilder builder) {
		this.builder = builder;
	}

	public void construir() {
		this.builder.inicializar();
		this.builder.cargarDatos();
	}

	public IConsultaCabeceraView getConsultaCabecera() {
		return this.builder.getConsultaCabecera();
	}

}
