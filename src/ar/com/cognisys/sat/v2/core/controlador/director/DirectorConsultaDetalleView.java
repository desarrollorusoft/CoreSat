package ar.com.cognisys.sat.v2.core.controlador.director;

import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.ConsultaDetalleViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaDetalleView;

public class DirectorConsultaDetalleView {

	private ConsultaDetalleViewBuilder builder;

	public DirectorConsultaDetalleView(ConsultaDetalleViewBuilder builder) {
		this.builder = builder;
	}

	public void construir() throws ErrorLecturaPropertiesException {
		this.builder.inicializar();
		this.builder.cargarDatos();
		this.builder.agregarMensajes();
		this.builder.construirArchivos();
	}

	public IConsultaDetalleView getConsultaDetalle() {
		return this.builder.getConsultaDetalle();
	}

}
