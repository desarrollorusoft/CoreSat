package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta;

import java.util.Date;

public class AlertaUsuarioDTO extends AlertaDTO {

	private Integer idUsuario;
	private Date fecha;
	private String cuit;
	private boolean notificada;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public boolean isNotificada() {
		return notificada;
	}

	public void setNotificada(boolean notificada) {
		this.notificada = notificada;
	}
}
