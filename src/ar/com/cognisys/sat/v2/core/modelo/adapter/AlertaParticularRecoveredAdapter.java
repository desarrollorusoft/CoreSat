package ar.com.cognisys.sat.v2.core.modelo.adapter;

import java.util.Date;

import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;

public class AlertaParticularRecoveredAdapter implements IAlerta {

	private AlertaUsuarioDTO alertaDTO;

	public AlertaParticularRecoveredAdapter(AlertaUsuarioDTO alertaDTO) {
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
		return this.alertaDTO.getFecha();
	}

	@Override
	public Date getFechaFin() {
		return this.alertaDTO.getFecha();
	}

	@Override
	public String getRedireccion() {
		return null;
	}

}
