package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AlertaVencimientoDTO extends AlertaDTO implements IAlertaGeneralDTO {

	private Date fecha;


	@Override
	public Date getFechaInicio() {
		return this.fecha;
	}

	@Override
	public Date getFechaFin() {
		return this.fecha;
	}

	@Override
	public String getRedireccion() {
		return null;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
