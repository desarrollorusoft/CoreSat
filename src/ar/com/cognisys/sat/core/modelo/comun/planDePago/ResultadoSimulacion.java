package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import java.util.List;

public class ResultadoSimulacion {

	private Integer numeroSolicitud;
	private Integer porcentajeCalculado;
	private Float importeAnticipo;
	private Float importePlan;
	private List<PlanSimulacion> listaPlanes;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((importeAnticipo == null) ? 0 : importeAnticipo.hashCode());
		result = prime * result + ((importePlan == null) ? 0 : importePlan.hashCode());
		result = prime * result + ((listaPlanes == null) ? 0 : listaPlanes.hashCode());
		result = prime * result + ((numeroSolicitud == null) ? 0 : numeroSolicitud.hashCode());
		result = prime * result + ((porcentajeCalculado == null) ? 0 : porcentajeCalculado.hashCode());
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
		ResultadoSimulacion other = (ResultadoSimulacion) obj;
		if (importeAnticipo == null) {
			if (other.importeAnticipo != null)
				return false;
		} else if (!importeAnticipo.equals(other.importeAnticipo))
			return false;
		if (importePlan == null) {
			if (other.importePlan != null)
				return false;
		} else if (!importePlan.equals(other.importePlan))
			return false;
		if (listaPlanes == null) {
			if (other.listaPlanes != null)
				return false;
		} else if (!listaPlanes.equals(other.listaPlanes))
			return false;
		if (numeroSolicitud == null) {
			if (other.numeroSolicitud != null)
				return false;
		} else if (!numeroSolicitud.equals(other.numeroSolicitud))
			return false;
		if (porcentajeCalculado == null) {
			if (other.porcentajeCalculado != null)
				return false;
		} else if (!porcentajeCalculado.equals(other.porcentajeCalculado))
			return false;
		return true;
	}

	public Integer getNumeroSolicitud() {
		return numeroSolicitud;
	}
	
	public void setNumeroSolicitud(Integer numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}
	
	public Integer getPorcentajeCalculado() {
		return porcentajeCalculado;
	}
	
	public void setPorcentajeCalculado(Integer porcentajeCalculado) {
		this.porcentajeCalculado = porcentajeCalculado;
	}
	
	public Float getImporteAnticipo() {
		return importeAnticipo;
	}
	
	public void setImporteAnticipo(Float importeAnticipo) {
		this.importeAnticipo = importeAnticipo;
	}
	
	public Float getImportePlan() {
		return importePlan;
	}
	
	public void setImportePlan(Float importePlan) {
		this.importePlan = importePlan;
	}

	public List<PlanSimulacion> getListaPlanes() {
		return listaPlanes;
	}

	public void setListaPlanes(List<PlanSimulacion> listaPlanes) {
		this.listaPlanes = listaPlanes;
	}
}