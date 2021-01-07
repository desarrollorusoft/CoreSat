package ar.com.cognisys.sat.v2.core.modelo.adapter;

import java.util.Date;

import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IAlertaView;

public class AlertaParticularFromViewAdapter implements IAlerta {

	private IAlertaView alertaView;

	public AlertaParticularFromViewAdapter(IAlertaView alertaView) {
		this.alertaView = alertaView;
	}

	@Override
	public String getTitulo() {
		return this.alertaView.getClave();
	}

	@Override
	public String getDescripcion() {
		return this.alertaView.getTitle();
	}

	@Override
	public Date getFechaInicio() {
		return this.alertaView.getStart();
	}

	@Override
	public Date getFechaFin() {
		return this.alertaView.getEnd();
	}

	@Override
	public String getRedireccion() {
		return this.alertaView.getRedireccion();
	}

}
