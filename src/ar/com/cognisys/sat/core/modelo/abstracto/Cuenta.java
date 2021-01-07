package ar.com.cognisys.sat.core.modelo.abstracto;

import java.io.Serializable;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.ICuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public abstract class Cuenta implements Serializable, ICuenta, Comparable<Cuenta> {

	private static final long serialVersionUID = 3616828627795478539L;

	private Integer numero;
	private String descripcion;
	private Contribuyente contribuyente;
	private boolean deudaLegales;
	private Float deuda;
	private Float aVencer;
	private Deuda deudas;
	private String alias;
	private boolean selected;
	private boolean aceptaBE;
	
	public abstract TiposCuentas getTipoCuenta();

	public boolean sos(TiposCuentas tipo) {
		return this.getTipoCuenta() == tipo;
	}

	public boolean esRS() {
		return sos(TiposCuentas.COMERCIOS);
	}

	public boolean esPileta() {
		return sos(TiposCuentas.PILETAS);
	}

	public abstract Integer getSistema();

	public abstract String getDatoCuenta();

	@Override public String obtenerContribuyente() {
		return this.contribuyente == null ? null : this.contribuyente.getNombreApellido();
	}

	public String getLeyenda() {
		return this.getNumero() + " - " + this.getDescripcion();
	}

	public abstract boolean correspondePPC();

	public abstract boolean correspondeDDJJ();

	public boolean sos(Cuenta otra) {
		return this.sos(otra.getDatoCuenta());
	}

	public boolean sos(String datoCuenta) {
		return this.getDatoCuenta().equals( datoCuenta );
	}
	
	public boolean sos(Integer numeroCuenta) {
		return this.getNumero().equals( numeroCuenta );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aVencer == null) ? 0 : aVencer.hashCode());
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((contribuyente == null) ? 0 : contribuyente.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((deuda == null) ? 0 : deuda.hashCode());
		result = prime * result + (deudaLegales ? 1231 : 1237);
		result = prime * result + ((deudas == null) ? 0 : deudas.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
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
		Cuenta other = (Cuenta) obj;
		if (aVencer == null) {
			if (other.aVencer != null)
				return false;
		} else if (!aVencer.equals(other.aVencer))
			return false;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (contribuyente == null) {
			if (other.contribuyente != null)
				return false;
		} else if (!contribuyente.equals(other.contribuyente))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (deuda == null) {
			if (other.deuda != null)
				return false;
		} else if (!deuda.equals(other.deuda))
			return false;
		if (deudaLegales != other.deudaLegales)
			return false;
		if (deudas == null) {
			if (other.deudas != null)
				return false;
		} else if (!deudas.equals(other.deudas))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (selected != other.selected)
			return false;
		return true;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Contribuyente getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(Contribuyente contribuyente) {
		this.contribuyente = contribuyente;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDeudaLegales() {
		return deudaLegales;
	}

	public void setDeudaLegales(boolean deudaLegales) {
		this.deudaLegales = deudaLegales;
	}

	public Float getDeuda() {
		return deuda;
	}

	public void setDeuda(Float deuda) {
		this.deuda = deuda;
	}

	public Float getaVencer() {
		return aVencer;
	}

	public void setaVencer(Float aVencer) {
		this.aVencer = aVencer;
	}

	public Deuda getDeudas() {
		return deudas;
	}

	public void setDeudas(Deuda deudas) {
		this.deudas = deudas;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isAceptaBE() {
		return aceptaBE;
	}

	public void setAceptaBE(boolean aceptaBE) {
		this.aceptaBE = aceptaBE;
	}
}