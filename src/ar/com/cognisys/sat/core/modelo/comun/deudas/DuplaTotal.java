package ar.com.cognisys.sat.core.modelo.comun.deudas;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class DuplaTotal {
	
	private TotalCuota totalSimple;
	private TotalCuota totalContado;
	
	public TotalCuota getTotalSimple() {
		return totalSimple;
	}
	
	public void setTotalSimple(TotalCuota totalSimple) {
		this.totalSimple = totalSimple;
	}
	
	public TotalCuota getTotalContado() {
		return totalContado;
	}
	
	public void setTotalContado(TotalCuota totalContado) {
		this.totalContado = totalContado;
	}
}