package ar.com.cognisys.sat.core.modelo.factory.tramite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.tramite.TramitePagoMultiple;
import ar.com.cognisys.sat.core.modelo.comun.tramite.pagoMultiple.PagoMultiple;

public class FactoryTramitePagoMultiple {

	public static TramitePagoMultiple generarInstancia() {
		
		TramitePagoMultiple tramite = new TramitePagoMultiple();
		tramite.setListaPagos(new ArrayList<PagoMultiple>());
		
		return tramite;
	}

	public static TramitePagoMultiple generarInstancia(Long id, String cuentaDominio, String periodo, String tributo, 
													   List<PagoMultiple> listaPagos, String estado, 
													   Date fechaCreacion, Date fechaActualizacion, String correo) {
		
		TramitePagoMultiple tramite = generarInstancia();
		tramite.setId(id);
		tramite.setCuentaDominio(cuentaDominio);
		tramite.setPeriodo(periodo);
		tramite.setTributo(tributo);
		tramite.setListaPagos(listaPagos);
		tramite.setEstado(estado);
		tramite.setFechaCreacion(fechaCreacion);
		tramite.setFechaActualizacion(fechaActualizacion);
		tramite.setCorreo(correo);
		
		return tramite;
	}
}