package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IActividadView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IRegimenView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.ISolicitanteView;

@Deprecated public class RegimenView implements IRegimenView {

	private static final long serialVersionUID = -7594792438424255260L;
	private RSActividadView datosActividad;
	private RSSolicitanteView datosSolicitante;
	
	public RegimenView() {}
	
	public RegimenView(RSActividadView datosActividad, RSSolicitanteView datosSolicitante) {
		this.datosActividad = datosActividad;
		this.datosSolicitante = datosSolicitante;
	}

	@Override
	public IActividadView getDatosActividad() {
		return datosActividad;
	}

	@Override
	public ISolicitanteView getDatosSolicitante() {
		return datosSolicitante;
	}

	public void setDatosActividad(RSActividadView datosActividad) {
		this.datosActividad = datosActividad;
	}

	public void setDatosSolicitante(RSSolicitanteView datosSolicitante) {
		this.datosSolicitante = datosSolicitante;
	}
	
}
