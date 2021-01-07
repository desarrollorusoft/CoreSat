package ar.com.cognisys.sat.core.modelo.comun.rs;

import java.util.Date;

public class TopeRS {

	private Integer cuentaLimite;
	private Float facturacionPadron;
	private Float facturacionAcumulada;
	private Integer cantidadPersonas;
	private Float metrosPyP;
	private Date fechaAdhesion;
	private Float metrosToldo;
	private Float unidadesPoste;
	private Integer unidadesSV;
	private static final float METROS_AVISOS = 2f;
	
	public TopeRS(Integer cuentaLimite, Float facturacionPadron, Float facturacionAcumulada, Integer cantidadPersonas,
				 Float metrosPyP, Date fechaAdhesion, Float metrosToldo, Float unidadesPoste, Integer unidadesSV) {
		this.setCuentaLimite(cuentaLimite);
		this.setFacturacionPadron(facturacionPadron);
		this.setFacturacionAcumulada(facturacionAcumulada);
		this.setCantidadPersonas(cantidadPersonas);
		this.setMetrosPyP(metrosPyP);
		this.setFechaAdhesion(fechaAdhesion);
		this.setMetrosToldo(metrosToldo);
		this.setUnidadesPoste(unidadesPoste);
		this.setUnidadesSV(unidadesSV);
	}
	
	public boolean excedeMetrosPyPLetreros(float metrosDeclarados) {
		return (metrosDeclarados > this.metrosPyP);
	}
	
	public boolean excedeMetrosPyPAvisos(float metrosDeclarados) {
		return (metrosDeclarados > METROS_AVISOS);
	}
	
	public boolean excedeFacturacionPadron(float facturacionDeclarada) {
		return (facturacionDeclarada > this.facturacionPadron);
	}

	public boolean excedeMetrosToldo(float metrosDeclarados) {
		return (metrosDeclarados > this.metrosToldo);
	}

	public boolean excedeUnidadesPoste(float unidadesDeclaradas) {
		return (unidadesDeclaradas > this.unidadesPoste);
	}
	
	public boolean excedeUnidadesSV(int unidadesDeclaradas) {
		return (unidadesDeclaradas > this.unidadesSV);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadPersonas == null) ? 0 : cantidadPersonas.hashCode());
		result = prime * result + ((cuentaLimite == null) ? 0 : cuentaLimite.hashCode());
		result = prime * result + ((facturacionAcumulada == null) ? 0 : facturacionAcumulada.hashCode());
		result = prime * result + ((facturacionPadron == null) ? 0 : facturacionPadron.hashCode());
		result = prime * result + ((fechaAdhesion == null) ? 0 : fechaAdhesion.hashCode());
		result = prime * result + ((metrosPyP == null) ? 0 : metrosPyP.hashCode());
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
		TopeRS other = (TopeRS) obj;
		if (cantidadPersonas == null) {
			if (other.cantidadPersonas != null)
				return false;
		} else if (!cantidadPersonas.equals(other.cantidadPersonas))
			return false;
		if (cuentaLimite == null) {
			if (other.cuentaLimite != null)
				return false;
		} else if (!cuentaLimite.equals(other.cuentaLimite))
			return false;
		if (facturacionAcumulada == null) {
			if (other.facturacionAcumulada != null)
				return false;
		} else if (!facturacionAcumulada.equals(other.facturacionAcumulada))
			return false;
		if (facturacionPadron == null) {
			if (other.facturacionPadron != null)
				return false;
		} else if (!facturacionPadron.equals(other.facturacionPadron))
			return false;
		if (fechaAdhesion == null) {
			if (other.fechaAdhesion != null)
				return false;
		} else if (!fechaAdhesion.equals(other.fechaAdhesion))
			return false;
		if (metrosPyP == null) {
			if (other.metrosPyP != null)
				return false;
		} else if (!metrosPyP.equals(other.metrosPyP))
			return false;
		return true;
	}

	public Integer getCuentaLimite() {
		return cuentaLimite;
	}

	public void setCuentaLimite(Integer cuentaLimite) {
		this.cuentaLimite = cuentaLimite;
	}

	public Float getFacturacionPadron() {
		return facturacionPadron;
	}

	public void setFacturacionPadron(Float facturacionPadron) {
		this.facturacionPadron = facturacionPadron;
	}

	public Float getFacturacionAcumulada() {
		return facturacionAcumulada;
	}

	public void setFacturacionAcumulada(Float facturacionAcumulada) {
		this.facturacionAcumulada = facturacionAcumulada;
	}

	public Integer getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(Integer cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public Float getMetrosPyP() {
		return metrosPyP;
	}

	public void setMetrosPyP(Float metrosPyP) {
		this.metrosPyP = metrosPyP;
	}

	public Date getFechaAdhesion() {
		return fechaAdhesion;
	}

	public void setFechaAdhesion(Date fechaAdhesion) {
		this.fechaAdhesion = fechaAdhesion;
	}

	public Float getMetrosToldo() {
		return metrosToldo;
	}

	public void setMetrosToldo(Float metrosToldo) {
		this.metrosToldo = metrosToldo;
	}

	public Float getUnidadesPoste() {
		return unidadesPoste;
	}

	public void setUnidadesPoste(Float unidadesPoste) {
		this.unidadesPoste = unidadesPoste;
	}

	public Integer getUnidadesSV() {
		return unidadesSV;
	}

	public void setUnidadesSV(Integer unidadesSV) {
		this.unidadesSV = unidadesSV;
	}
}