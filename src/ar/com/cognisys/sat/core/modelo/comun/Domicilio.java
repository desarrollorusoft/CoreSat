package ar.com.cognisys.sat.core.modelo.comun;

import java.io.Serializable;

public class Domicilio implements Serializable {

	private static final long serialVersionUID = -5282358248170806595L;
	
	private String calle;
	private Integer altura;
	private String piso;
	private String departamento;
	private Integer codigoPostal;
	
	private String descripcionCompleta;

	@Override
	public String toString() {
		
		String direccion = "";
		
		if (this.getDescripcionCompleta() != null && !this.getDescripcionCompleta().equals("")) {
			direccion = this.getDescripcionCompleta();
			
		} else {
			if (this.getCalle() != null || !this.getCalle().equals("")) {
				
				direccion = this.getCalle();
				
			} if (this.getAltura() != null || !this.getAltura().equals("")) {

				direccion = direccion + " " + this.getAltura().toString();
				
			} if (this.getPiso() != null && !this.getPiso().equals("")) {
				
				direccion = direccion + " " + this.getPiso();

			} if (this.getDepartamento() != null && !this.getDepartamento().equals("")) {
				
				direccion = direccion + " " + this.getDepartamento();

			} if (this.getCodigoPostal() != null && !this.getCodigoPostal().equals("")) {
				
				direccion = direccion + " (" + this.getCodigoPostal() + ")";
			}
		}
		
		return direccion;
	}
	
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		
		if (calle != null) {
			calle = calle.trim();
		}
		
		this.calle = calle;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		
		if (piso != null) {
			piso = piso.trim();
		}
		
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		
		if (departamento != null) {
			departamento = departamento.trim();
		}
		
		this.departamento = departamento;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDescripcionCompleta() {
		return descripcionCompleta;
	}

	public void setDescripcionCompleta(String descripcionCompleta) {
		this.descripcionCompleta = descripcionCompleta;
	}
}