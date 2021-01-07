package ar.com.cognisys.sat.core.modelo.factory.tramite.pagoMultiple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.tramite.pagoMultiple.PagoMultiple;

public class FactoryPagoMultiple {

	public static PagoMultiple generarInstancia() {
		
		PagoMultiple pago = new PagoMultiple();
		pago.setListaArchivos(new ArrayList<Archivo>());
		
		return pago;
	}

	public static PagoMultiple generarInstancia(Long id, Float importe, Date fecha, Long numeroComprobante, 
											   String lugarPago, List<Archivo> listaArchivos) {
		
		PagoMultiple pago = generarInstancia();
		pago.setId(id);
		pago.setImporte(importe);
		pago.setFecha(fecha);
		pago.setNumeroComprobante(numeroComprobante);
		pago.setLugarPago(lugarPago);
		pago.setListaArchivos(listaArchivos);
		
		return pago;
	}
}