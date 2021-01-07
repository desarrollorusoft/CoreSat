package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividadComercial;

public class ActividadComercial {

	private Integer codigo;
	private String nombre;
	private boolean permitido;
	
	public boolean sos(String nombre) {
		return nombre.contains(this.getNombre());
	}
	
	public boolean sos(Integer codigo) {
		return codigo.equals(this.getCodigo());
	}
	
	public ActividadComercial generarClon() {
		return FactoryActividadComercial.generar(this.getCodigo(), this.getNombre(), this.isPermitido());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + (permitido ? 1231 : 1237);
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
		ActividadComercial other = (ActividadComercial) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (permitido != other.permitido)
			return false;
		return true;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isPermitido() {
		return permitido;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}
}