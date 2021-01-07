package ar.com.cognisys.sat.core.modelo.ppc.cuota;

import java.util.Date;

public class CuotaPlanPagoCeroDTO extends CuotaPlanPagoDTO {

	public CuotaPlanPagoCeroDTO(Date vencimiento) {
		super( 0, 0, 0, vencimiento );
	}

}
