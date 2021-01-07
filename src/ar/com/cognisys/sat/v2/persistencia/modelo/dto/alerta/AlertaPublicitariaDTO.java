package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AlertaPublicitariaDTO extends AlertaDTO implements IAlertaGeneralDTO {

	private Date fechaInicio;
	private Date fechaFin;
	private String redireccion;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getRedireccion() {
		return redireccion;
	}

	public void setRedireccion(String redireccion) {
		this.redireccion = redireccion;
	}

}
