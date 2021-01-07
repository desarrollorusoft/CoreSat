package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IActividadView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ISolicitanteView;

public class RegimenView implements IRegimenView {

	private static final long serialVersionUID = -6150244792879734457L;
	private ActividadView actividad;
	private SolicitanteView solicitante;
	private boolean completo;
	private boolean rechazado;
	
	public RegimenView() {}
	
	public RegimenView(ActividadView actividad, SolicitanteView solicitante, boolean completo, boolean rechazado) {
		this.actividad = actividad;
		this.solicitante = solicitante;
		this.completo = completo;
		this.rechazado = rechazado;
	}
	
	@Override public IActividadView getActividad() {
		return actividad;
	}

	@Override public ISolicitanteView getSolicitante() {
		return solicitante;
	}

	public void setActividad(ActividadView actividad) {
		this.actividad = actividad;
	}

	public void setSolicitante(SolicitanteView solicitante) {
		this.solicitante = solicitante;
	}

	@Override
	public boolean isCompleto() {
		return this.completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	@Override
	public boolean isRechazado() {
		return rechazado;
	}
}