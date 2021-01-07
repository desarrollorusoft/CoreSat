package ar.com.cognisys.sat.core.modelo.comun.consultas;

import java.io.Serializable;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

public class Dato extends ObjetoPersistible implements Serializable {

	private static final long serialVersionUID = -4596433160372432586L;

	private String nombre;
	private boolean opcional;
	private String expresionRegular;
	private String mensajeError;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Dato dato = (Dato) o;

		if (opcional != dato.opcional) return false;
		if (nombre != null ? !nombre.equals(dato.nombre) : dato.nombre != null) return false;
		if (expresionRegular != null ? !expresionRegular.equals(dato.expresionRegular) : dato.expresionRegular != null) return false;
		return mensajeError != null ? mensajeError.equals(dato.mensajeError) : dato.mensajeError == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
		result = 31 * result + (opcional ? 1 : 0);
		result = 31 * result + (expresionRegular != null ? expresionRegular.hashCode() : 0);
		result = 31 * result + (mensajeError != null ? mensajeError.hashCode() : 0);
		return result;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}

	public String getExpresionRegular() {
		return expresionRegular;
	}

	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
