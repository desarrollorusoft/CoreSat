package ar.com.cognisys.sat.core.modelo.comun.estadisticas.general;

import ar.com.cognisys.sat.core.modelo.comun.estadisticas.Estadisticas;

public class EstadisticasGeneralOrigen extends Estadisticas {

	private static final long serialVersionUID = -7293662313180080108L;
	
	private Long pmc;
	private Long recibo;
	private Long interbanking;
	private Long tarjetaCredito;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((interbanking == null) ? 0 : interbanking.hashCode());
		result = prime * result + ((pmc == null) ? 0 : pmc.hashCode());
		result = prime * result + ((recibo == null) ? 0 : recibo.hashCode());
		result = prime * result + ((tarjetaCredito == null) ? 0 : tarjetaCredito.hashCode());
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
		EstadisticasGeneralOrigen other = (EstadisticasGeneralOrigen) obj;
		if (interbanking == null) {
			if (other.interbanking != null)
				return false;
		} else if (!interbanking.equals(other.interbanking))
			return false;
		if (pmc == null) {
			if (other.pmc != null)
				return false;
		} else if (!pmc.equals(other.pmc))
			return false;
		if (recibo == null) {
			if (other.recibo != null)
				return false;
		} else if (!recibo.equals(other.recibo))
			return false;
		if (tarjetaCredito == null) {
			if (other.tarjetaCredito != null)
				return false;
		} else if (!tarjetaCredito.equals(other.tarjetaCredito))
			return false;
		return true;
	}

	public Long getPmc() {
		return pmc;
	}

	public void setPmc(Long pmc) {
		this.pmc = pmc;
	}

	public Long getRecibo() {
		return recibo;
	}

	public void setRecibo(Long recibo) {
		this.recibo = recibo;
	}

	public Long getInterbanking() {
		return interbanking;
	}

	public void setInterbanking(Long interbanking) {
		this.interbanking = interbanking;
	}

	public Long getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(Long tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}
}