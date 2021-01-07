package ar.com.cognisys.sat.core.modelo.comun.rs.versiones;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryVersionPadronRS;

public abstract class VersionPadronRS {

	private Integer id;
	private Integer version;
	private Float facturacion;
	private Integer cantidadPersonas;
	private String cuentaABL;
	private Date fechaHabilitacion;
	private Date fechaActualizacion;
	private boolean completo;
	
	public abstract int getAno();
	
	public VersionPadronRS generarClon() {
		VersionPadronRS clon = FactoryVersionPadronRS.generar(this.getAno(), 
															  this.getId(), 
															  this.getVersion(), 
															  this.getCantidadPersonas(), 
															  this.getCuentaABL(), 
															  this.getFacturacion(), 
															  this.getFechaHabilitacion(), 
															  this.getFechaActualizacion(), 
															  this.isCompleto());
		
		this.cargarDatos( clon );
		
		return clon;
	}

	public boolean tieneID() {
		return this.getId() != null;
	}
	
	protected abstract void cargarDatos(VersionPadronRS clon);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadPersonas == null) ? 0 : cantidadPersonas.hashCode());
		result = prime * result + (completo ? 1231 : 1237);
		result = prime * result + ((cuentaABL == null) ? 0 : cuentaABL.hashCode());
		result = prime * result + ((facturacion == null) ? 0 : facturacion.hashCode());
		result = prime * result + ((fechaActualizacion == null) ? 0 : fechaActualizacion.hashCode());
		result = prime * result + ((fechaHabilitacion == null) ? 0 : fechaHabilitacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		VersionPadronRS other = (VersionPadronRS) obj;
		if (cantidadPersonas == null) {
			if (other.cantidadPersonas != null)
				return false;
		} else if (!cantidadPersonas.equals(other.cantidadPersonas))
			return false;
		if (completo != other.completo)
			return false;
		if (cuentaABL == null) {
			if (other.cuentaABL != null)
				return false;
		} else if (!cuentaABL.equals(other.cuentaABL))
			return false;
		if (facturacion == null) {
			if (other.facturacion != null)
				return false;
		} else if (!facturacion.equals(other.facturacion))
			return false;
		if (fechaActualizacion == null) {
			if (other.fechaActualizacion != null)
				return false;
		} else if (!fechaActualizacion.equals(other.fechaActualizacion))
			return false;
		if (fechaHabilitacion == null) {
			if (other.fechaHabilitacion != null)
				return false;
		} else if (!fechaHabilitacion.equals(other.fechaHabilitacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Float getFacturacion() {
		return facturacion;
	}

	public void setFacturacion(Float facturacion) {
		this.facturacion = facturacion;
	}

	public Integer getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(Integer cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public String getCuentaABL() {
		return cuentaABL;
	}

	public void setCuentaABL(String cuentaABL) {
		this.cuentaABL = cuentaABL;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public boolean isCompleto() {
		return completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	public Date getFechaHabilitacion() {
		return fechaHabilitacion;
	}

	public void setFechaHabilitacion(Date fechaHabilitacion) {
		this.fechaHabilitacion = fechaHabilitacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}