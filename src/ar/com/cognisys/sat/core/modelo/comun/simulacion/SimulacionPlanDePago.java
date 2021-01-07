package ar.com.cognisys.sat.core.modelo.comun.simulacion;

import java.util.List;

public class SimulacionPlanDePago {

	private Float porcentajeAnticipo;
	private Float importeAnticipo;
	private Float derechosAnticipo;
	private List<CuotaPDP> listaCuotaAnticipo;
	private Float montoFinanciacion;
	private Float interesFinanciacion;
	private List<CuotaFinanciacion> listaCuotaFinanciacion;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaCuotaAnticipo == null) ? 0 : listaCuotaAnticipo.hashCode());
		result = prime * result + ((derechosAnticipo == null) ? 0 : derechosAnticipo.hashCode());
		result = prime * result + ((importeAnticipo == null) ? 0 : importeAnticipo.hashCode());
		result = prime * result + ((interesFinanciacion == null) ? 0 : interesFinanciacion.hashCode());
		result = prime * result + ((listaCuotaFinanciacion == null) ? 0: listaCuotaFinanciacion.hashCode());
		result = prime * result + ((montoFinanciacion == null) ? 0 : montoFinanciacion.hashCode());
		result = prime * result + ((porcentajeAnticipo == null) ? 0 : porcentajeAnticipo.hashCode());
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
		SimulacionPlanDePago other = (SimulacionPlanDePago) obj;
		if (listaCuotaAnticipo == null) {
			if (other.listaCuotaAnticipo != null)
				return false;
		} else if (!listaCuotaAnticipo.equals(other.listaCuotaAnticipo))
			return false;
		if (derechosAnticipo == null) {
			if (other.derechosAnticipo != null)
				return false;
		} else if (!derechosAnticipo.equals(other.derechosAnticipo))
			return false;
		if (importeAnticipo == null) {
			if (other.importeAnticipo != null)
				return false;
		} else if (!importeAnticipo.equals(other.importeAnticipo))
			return false;
		if (interesFinanciacion == null) {
			if (other.interesFinanciacion != null)
				return false;
		} else if (!interesFinanciacion.equals(other.interesFinanciacion))
			return false;
		if (listaCuotaFinanciacion == null) {
			if (other.listaCuotaFinanciacion != null)
				return false;
		} else if (!listaCuotaFinanciacion.equals(other.listaCuotaFinanciacion))
			return false;
		if (montoFinanciacion == null) {
			if (other.montoFinanciacion != null)
				return false;
		} else if (!montoFinanciacion.equals(other.montoFinanciacion))
			return false;
		if (porcentajeAnticipo == null) {
			if (other.porcentajeAnticipo != null)
				return false;
		} else if (!porcentajeAnticipo.equals(other.porcentajeAnticipo))
			return false;
		return true;
	}

	public Float getPorcentajeAnticipo() {
		return porcentajeAnticipo;
	}
	
	public String getPorcentajeAnticipoFormateado() {
		return porcentajeAnticipo + " %";
	}
	
	public void setPorcentajeAnticipo(Float porcentajeAnticipo) {
		this.porcentajeAnticipo = porcentajeAnticipo;
	}
	
	public Float getImporteAnticipo() {
		return importeAnticipo;
	}
	
	public String getImporteAnticipoFormateado() {
		return "$ " + importeAnticipo.toString();
	}
	
	public void setImporteAnticipo(Float importeAnticipo) {
		this.importeAnticipo = importeAnticipo;
	}
	
	public Float getDerechosAnticipo() {
		return derechosAnticipo;
	}
	
	public void setDerechosAnticipo(Float derechosAnticipo) {
		this.derechosAnticipo = derechosAnticipo;
	}
	
	public List<CuotaPDP> getCuotaAnticipo() {
		return listaCuotaAnticipo;
	}
	
	public void setCuotaAnticipo(List<CuotaPDP> listaCuotaAnticipo) {
		this.listaCuotaAnticipo = listaCuotaAnticipo;
	}
	
	public Float getMontoFinanciacion() {
		return montoFinanciacion;
	}
	
	public String getMontoFinanciacionFormateado() {
		return "$ " + montoFinanciacion.toString();
	}
	
	public void setMontoFinanciacion(Float montoFinanciacion) {
		this.montoFinanciacion = montoFinanciacion;
	}
	
	public Float getInteresFinanciacion() {
		return interesFinanciacion;
	}
	
	public void setInteresFinanciacion(Float interesFinanciacion) {
		this.interesFinanciacion = interesFinanciacion;
	}
	
	public List<CuotaFinanciacion> getListaCuotaFinanciacion() {
		return listaCuotaFinanciacion;
	}
	
	public void setListaCuotaFinanciacion(List<CuotaFinanciacion> listaCuotaFinanciacion) {
		this.listaCuotaFinanciacion = listaCuotaFinanciacion;
	}	
}