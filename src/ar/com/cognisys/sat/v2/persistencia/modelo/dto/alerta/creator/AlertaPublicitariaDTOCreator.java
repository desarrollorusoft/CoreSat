package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaPublicitariaDTO;

public class AlertaPublicitariaDTOCreator extends AlertaDTOCreator<AlertaPublicitariaDTOCreator, AlertaPublicitariaDTO>{

	public AlertaPublicitariaDTOCreator fechaInicio(Date fechaInicio) {
		this.alertaDTO.setFechaInicio( fechaInicio );
		
		return this;
	}
	
	public AlertaPublicitariaDTOCreator fechaFin(Date fechaFin) {
		this.alertaDTO.setFechaFin( fechaFin );
		
		return this;
	}
	
	public AlertaPublicitariaDTOCreator redireccion(String redireccion) {
		this.alertaDTO.setRedireccion( redireccion );
		
		return this;
	}
	
	@Override
	protected AlertaPublicitariaDTO inicializar() {
		return new AlertaPublicitariaDTO();
	}

	@Override
	public AlertaPublicitariaDTO create() {
		return this.alertaDTO;
	}

}
