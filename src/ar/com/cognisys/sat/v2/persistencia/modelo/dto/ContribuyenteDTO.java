package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

public class ContribuyenteDTO {

	private String nombre;
	private String apellido;
	private Integer tipoDocumento;
	private Integer numeroDocumento;
	private String correo;
	private String telefono;
	private DomicilioDTO domicilio;

	public ContribuyenteDTO(String nombre, String apellido, Integer tipoDocumento, Integer numeroDocumento, String correo, String telefono, DomicilioDTO domicilio) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.correo = correo;
		this.telefono = telefono;
		this.domicilio = domicilio;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}

	public String getCorreo() {
		return correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public DomicilioDTO getDomicilio() {
		return domicilio;
	}
}