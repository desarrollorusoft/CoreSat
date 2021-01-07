package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryTipoCartel;

public class TipoCartel {

	public static final String OTROS = "Otros";
	private Integer codigo;
	private String nombre;
	
	public boolean sos(String nombre) {
		return this.getNombre().equals(nombre);
	}
	
	public boolean sos(int categoria) {
		return this.getCodigo().equals(categoria);
	}
	
	public TipoCartel generarClon() {
		return FactoryTipoCartel.generar(this.getCodigo(), this.getNombre());
	}
	
	public boolean sosOtros() {
		return this.getNombre().equals( OTROS );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		TipoCartel other = (TipoCartel) obj;
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
}