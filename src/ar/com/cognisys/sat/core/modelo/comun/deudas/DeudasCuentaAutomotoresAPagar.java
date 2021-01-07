package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class DeudasCuentaAutomotoresAPagar {
	
	private List<CuotaApagar> cuotasAPagar;
	private TotalCuota totalApagar;
	private TotalCuota totalApagarContado;
	
	public TotalCuota getTotalApagarContado() {
		return totalApagarContado;
	}

	public void setTotalApagarContado(TotalCuota totalApagarContado) {
		this.totalApagarContado = totalApagarContado;
	}
	
	public List<CuotaApagar> getCuotasAPagar() {
		return cuotasAPagar;
	}
	
	public void setCuotasAPagar(List<CuotaApagar> cuotasAPagar) {
		this.cuotasAPagar = cuotasAPagar;
	}
	
	public TotalCuota getTotalApagar() {
		return totalApagar;
	}
	
	public void setTotalApagar(TotalCuota totalApagar) {
		this.totalApagar = totalApagar;
	}
}