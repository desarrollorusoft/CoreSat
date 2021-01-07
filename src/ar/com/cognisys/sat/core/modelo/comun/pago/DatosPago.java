package ar.com.cognisys.sat.core.modelo.comun.pago;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

public class DatosPago extends ObjetoPersistible {
	
	private String tipo;
	private String datos;
	private String tasa;
	private String cuenta;
	private String razonSocial;
	private String recibo;
	private Date fechaPago;
	private Float valor;
	private Date fechaSistema;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime * result + ((datos == null) ? 0 : datos.hashCode());
		result = prime * result + ((fechaPago == null) ? 0 : fechaPago.hashCode());
		result = prime * result + ((fechaSistema == null) ? 0 : fechaSistema.hashCode());
		result = prime * result + ((razonSocial == null) ? 0 : razonSocial.hashCode());
		result = prime * result + ((recibo == null) ? 0 : recibo.hashCode());
		result = prime * result + ((tasa == null) ? 0 : tasa.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		DatosPago other = (DatosPago) obj;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (datos == null) {
			if (other.datos != null)
				return false;
		} else if (!datos.equals(other.datos))
			return false;
		if (fechaPago == null) {
			if (other.fechaPago != null)
				return false;
		} else if (!fechaPago.equals(other.fechaPago))
			return false;
		if (fechaSistema == null) {
			if (other.fechaSistema != null)
				return false;
		} else if (!fechaSistema.equals(other.fechaSistema))
			return false;
		if (razonSocial == null) {
			if (other.razonSocial != null)
				return false;
		} else if (!razonSocial.equals(other.razonSocial))
			return false;
		if (recibo == null) {
			if (other.recibo != null)
				return false;
		} else if (!recibo.equals(other.recibo))
			return false;
		if (tasa == null) {
			if (other.tasa != null)
				return false;
		} else if (!tasa.equals(other.tasa))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRecibo() {
		return recibo;
	}
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public Date getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
	}
	
}
