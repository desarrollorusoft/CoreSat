package ar.com.cognisys.sat.v2.core.modelo.adapter;

import java.util.Date;

import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AlertaGeneralRecoveredAdapter implements IAlerta {

	private IAlertaGeneralDTO alertaDTO;

	public AlertaGeneralRecoveredAdapter(IAlertaGeneralDTO alertaDTO) {
		this.alertaDTO = alertaDTO;
	}

	@Override
	public String getTitulo() {
		return this.alertaDTO.getTitulo();
	}

	@Override
	public String getDescripcion() {
		return this.alertaDTO.getDescripcion();
	}

	@Override
	public Date getFechaInicio() {
		return this.alertaDTO.getFechaInicio();
	}

	@Override
	public Date getFechaFin() {
		return this.alertaDTO.getFechaFin();
	}

	@Override
	public String getRedireccion() {
		return this.alertaDTO.getRedireccion();
	}

}
