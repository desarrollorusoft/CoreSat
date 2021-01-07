package ar.com.cognisys.sat.core.modelo.ppc.cuota;

import java.util.Date;

public class CuotaPlanPagoRecuperadoDTO extends CuotaPlanPagoDTO {

	public CuotaPlanPagoRecuperadoDTO(float capital, float recargo, float multa, Date vencimiento) {
		super( capital, recargo, multa, vencimiento );
	}

}
