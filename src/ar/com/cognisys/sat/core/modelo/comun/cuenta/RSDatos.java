package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSActividad;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSDatos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSSolicitante;

@Deprecated
public class RSDatos implements IRSDatos {

	private Long id;
	private RSActividad actividad;
	private RSSolicitante solicitante;
	private boolean rechazo;
	public RSDatos(Long id, RSActividad actividad, RSSolicitante solicitante) {
		this.id = id;
		this.actividad = actividad;
		this.solicitante = solicitante;
	}

	public String getNombreCompleto() {
		return this.getSolicitante().getNombreCompleto();
	}
	
	public boolean tieneDatosCompletos() {
		return (this.getActividad().isCompleto() && this.getSolicitante().isCompleto());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actividad == null) ? 0 : actividad.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (rechazo ? 1231 : 1237);
		result = prime * result + ((solicitante == null) ? 0 : solicitante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RSDatos other = (RSDatos) obj;
		if (actividad == null) {
			if (other.actividad != null)
				return false;
		} else if (!actividad.equals(other.actividad))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rechazo != other.rechazo)
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IRSActividad getActividad() {
		return actividad;
	}

	public void setActividad(RSActividad actividad) {
		this.actividad = actividad;
	}
	
	public IRSSolicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(RSSolicitante solicitante) {
		this.solicitante = solicitante;
	}

	public boolean isRechazo() {
		return rechazo;
	}

	public void setRechazo(boolean rechazo) {
		this.rechazo = rechazo;
	}
}