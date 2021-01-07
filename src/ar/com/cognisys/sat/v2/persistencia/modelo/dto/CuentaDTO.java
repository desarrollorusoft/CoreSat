package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

public class CuentaDTO {

	protected String tipo;
	protected String codigo;
	protected Integer numero;
	protected String descripcion;
	protected String alias;
	protected boolean aceptaBE;

	public CuentaDTO(String tipo, String codigo, Integer numero, String descripcion, String alias, boolean aceptaBE) {
		this.tipo = tipo;
		this.codigo = codigo;
		this.numero = numero;
		this.descripcion = descripcion;
		this.alias = alias;
		this.aceptaBE = aceptaBE;
	}
	
	public String getTipo() {
		return tipo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAlias() {
		return alias;
	}

	public boolean isAceptaBE() {
		return aceptaBE;
	}

	public void setAceptaBE(boolean aceptaBE) {
		this.aceptaBE = aceptaBE;
	}
}