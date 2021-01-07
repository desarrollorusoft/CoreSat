package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

public class DomicilioDTO {

	private String calle;
	private Integer numero;
	private String piso;
	private String departamento;
	private Integer codigoPostal;

	public DomicilioDTO(String calle, Integer numero, String piso, String departamento, Integer codigoPostal) {
		this.calle = calle;
		this.numero = numero;
		this.piso = piso;
		this.departamento = departamento;
		this.codigoPostal = codigoPostal;
	}

	public String getCalle() {
		return calle;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getPiso() {
		return piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}
}