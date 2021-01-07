package ar.com.cognisys.sat.core.modelo.comun;

import java.io.Serializable;


public class Contribuyente implements Serializable {

	private static final long serialVersionUID = -5388461820183552833L;
	
	private String nombre;
	private String apellido;
	private Integer tipoDocumento;
	private Integer numeroDocumento;
	private String correo;
	private Domicilio domicilio;
	private String telefono;
	private String telefono2;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((telefono2 == null) ? 0 : telefono2.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
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
		Contribuyente other = (Contribuyente) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (telefono2 == null) {
			if (other.telefono2 != null)
				return false;
		} else if (!telefono2.equals(other.telefono2))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		
		if (nombre != null) {
			nombre = nombre.trim();
		}
		
		this.nombre = nombre;
	}
	
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		
		if (correo != null) {
			correo = correo.trim();
		}
		
		this.correo = correo;
	}
	
	public Domicilio getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		
		if (telefono != null) {
			telefono = telefono.trim();
		}
		
		this.telefono = telefono;
	}

	public String getFax() {
		return telefono2;
	}

	public void setFax(String telefono2) {
		
		if (telefono2 != null) {
			telefono2 = telefono2.trim();
		}
		
		this.telefono2 = telefono2;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		
		if (apellido != null) {
			apellido = apellido.trim();
		}
		
		this.apellido = apellido;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombreApellido() {
		return this.getNombre() + ", " + this.getApellido();
	}
}