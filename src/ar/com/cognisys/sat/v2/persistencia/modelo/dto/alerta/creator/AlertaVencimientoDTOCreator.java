package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaVencimientoDTO;

public class AlertaVencimientoDTOCreator extends AlertaDTOCreator<AlertaVencimientoDTOCreator, AlertaVencimientoDTO> {

	public AlertaVencimientoDTOCreator fecha(Date fecha) {
		this.alertaDTO.setFecha( fecha );
		
		return this;
	}
	
	@Override
	protected AlertaVencimientoDTO inicializar() {
		return new AlertaVencimientoDTO();
	}

	@Override
	public AlertaVencimientoDTO create() {
		return this.alertaDTO;
	}

}
