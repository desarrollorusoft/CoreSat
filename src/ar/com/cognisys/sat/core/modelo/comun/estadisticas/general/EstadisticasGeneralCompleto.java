package ar.com.cognisys.sat.core.modelo.comun.estadisticas.general;

import ar.com.cognisys.sat.core.modelo.comun.estadisticas.Estadisticas;

public class EstadisticasGeneralCompleto extends Estadisticas {

	private static final long serialVersionUID = -6027051060972858635L;
	
	private String medio;
	private String tributo;
	private Long cantidadWeb;
	private Long cantidadMobile;
	private Long total;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cantidadMobile == null) ? 0 : cantidadMobile.hashCode());
		result = prime * result
				+ ((cantidadWeb == null) ? 0 : cantidadWeb.hashCode());
		result = prime * result + ((medio == null) ? 0 : medio.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((tributo == null) ? 0 : tributo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadisticasGeneralCompleto other = (EstadisticasGeneralCompleto) obj;
		if (cantidadMobile == null) {
			if (other.cantidadMobile != null)
				return false;
		} else if (!cantidadMobile.equals(other.cantidadMobile))
			return false;
		if (cantidadWeb == null) {
			if (other.cantidadWeb != null)
				return false;
		} else if (!cantidadWeb.equals(other.cantidadWeb))
			return false;
		if (medio == null) {
			if (other.medio != null)
				return false;
		} else if (!medio.equals(other.medio))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (tributo == null) {
			if (other.tributo != null)
				return false;
		} else if (!tributo.equals(other.tributo))
			return false;
		return true;
	}

	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public Long getCantidadWeb() {
		return cantidadWeb;
	}

	public void setCantidadWeb(Long cantidadWeb) {
		this.cantidadWeb = cantidadWeb;
	}

	public Long getCantidadMobile() {
		return cantidadMobile;
	}

	public void setCantidadMobile(Long cantidadMobile) {
		this.cantidadMobile = cantidadMobile;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}