package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJSV;

/**
 * DDJJ de Servicios Varios
 * @author fgarnero
 *
 */
public class DDJJSV {
	
	private int motores;
	private int calderas;
	
	public DDJJSV generarClon() {
		return FactoryDDJJSV.generar(this.getMotores(), this.getCalderas());
	}
	
	public int getUnidadesTotales() {
		return this.motores + this.calderas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + calderas;
		result = prime * result + motores;
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
		DDJJSV other = (DDJJSV) obj;
		if (calderas != other.calderas)
			return false;
		if (motores != other.motores)
			return false;
		return true;
	}

	public Integer getMotores() {
		return motores;
	}

	public void setMotores(Integer motores) {
		this.motores = motores;
	}

	public Integer getCalderas() {
		return calderas;
	}

	public void setCalderas(Integer calderas) {
		this.calderas = calderas;
	}
}