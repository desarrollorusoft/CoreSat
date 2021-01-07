package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJ;

public class ResumenDDJJ {
	
	private DDJJ ddjj;
	
	private String razonSocial;
	private Integer codigo;
	
	public ResumenDDJJ(DDJJ ddjj, String razonSocial, Integer codigo) {
		this.ddjj = ddjj;
		this.razonSocial = razonSocial;
		this.codigo = codigo;
	}
	public DDJJ getDdjj() {
		return ddjj;
	}
	public void setDdjj(DDJJ ddjj) {
		this.ddjj = ddjj;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}	
}