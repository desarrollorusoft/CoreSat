package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

public class ConflictosDDJJRS {
	
	private Integer codigo;
	private String descripcion;
	
	public ConflictosDDJJRS(Integer codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}
}