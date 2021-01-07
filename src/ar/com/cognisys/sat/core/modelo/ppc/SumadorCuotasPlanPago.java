package ar.com.cognisys.sat.core.modelo.ppc;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.ppc.cuota.CuotaPlanPagoDTO;

public class SumadorCuotasPlanPago {

	private List<CuotaPlanPagoDTO> multas;

	public SumadorCuotasPlanPago(List<CuotaPlanPagoDTO> multas) {
		this.multas = multas;
	}

	public TotalPlanPago sumar() {

		TotalPlanPago total = new TotalPlanPago();

		for ( CuotaPlanPagoDTO multaDTO: this.multas ) {
			total.sumarCapital( multaDTO.getCapital() );
			total.sumarRecargo( multaDTO.getRecargo() / 2 );
			total.sumarMulta( multaDTO.getMulta() );
		}

		return total;
	}

}
