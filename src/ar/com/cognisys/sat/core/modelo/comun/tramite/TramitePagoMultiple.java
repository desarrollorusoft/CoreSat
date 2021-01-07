package ar.com.cognisys.sat.core.modelo.comun.tramite;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Tramite;
import ar.com.cognisys.sat.core.modelo.comun.tramite.pagoMultiple.PagoMultiple;

public class TramitePagoMultiple extends Tramite {

	private String cuentaDominio;
	private String tributo;
	private String periodo;
	private List<PagoMultiple> listaPagos;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuentaDominio == null) ? 0 : cuentaDominio.hashCode());
		result = prime * result + ((listaPagos == null) ? 0 : listaPagos.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((tributo == null) ? 0 : tributo.hashCode());
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
		TramitePagoMultiple other = (TramitePagoMultiple) obj;
		if (cuentaDominio == null) {
			if (other.cuentaDominio != null)
				return false;
		} else if (!cuentaDominio.equals(other.cuentaDominio))
			return false;
		if (listaPagos == null) {
			if (other.listaPagos != null)
				return false;
		} else if (!listaPagos.equals(other.listaPagos))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (tributo == null) {
			if (other.tributo != null)
				return false;
		} else if (!tributo.equals(other.tributo))
			return false;
		return true;
	}

	public String getCuentaDominio() {
		return cuentaDominio;
	}
	
	public void setCuentaDominio(String cuentaDominio) {
		this.cuentaDominio = cuentaDominio;
	}
	
	public String getTributo() {
		return tributo;
	}
	
	public void setTributo(String tributo) {
		this.tributo = tributo;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public List<PagoMultiple> getListaPagos() {
		return listaPagos;
	}
	
	public void setListaPagos(List<PagoMultiple> listaPagos) {
		this.listaPagos = listaPagos;
	}
}