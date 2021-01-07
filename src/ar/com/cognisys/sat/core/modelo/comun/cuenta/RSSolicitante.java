package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSSolicitante;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

public class RSSolicitante implements IRSSolicitante {

	private String nombre;
	private String apellido;
	private CaracterSAT caracter;
	private String correo;
	private String telefono;
	private String celular;
	
	public String getNombreCompleto() {
		return this.getApellido() + ", " + this.getNombre();
	}
	
	@Override
	public boolean isCompleto() {
		return (nombre != null && !nombre.equals("")) &&
				(apellido != null && !apellido.equals("")) &&
				(caracter != null) &&
				(correo != null && !correo.equals(""));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((caracter == null) ? 0 : caracter.hashCode());
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		RSSolicitante other = (RSSolicitante) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (caracter == null) {
			if (other.caracter != null)
				return false;
		} else if (!caracter.equals(other.caracter))
			return false;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public CaracterSAT getCaracter() {
		return caracter;
	}

	public void setCaracter(CaracterSAT caracter) {
		this.caracter = caracter;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}