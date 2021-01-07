package ar.com.cognisys.sat.core.testing;

import org.junit.Test;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class QA_Automotores_BoletaDePago {

	@Test
	public void probarGetDatosAutomotores() throws ExcepcionControladaError {

		CuentaVehiculos cuenta = new CuentaVehiculos();
		
		cuenta.setNumero(47507);
		
		Contribuyente contribuyente = new Contribuyente();
		
		contribuyente.setNombre("EDUARDO LUIS");;
		contribuyente.setApellido("SAVIGLIANO");
		
		cuenta.setDominio("AHZ164");
		
		cuenta.setContribuyente(contribuyente);
		
		// TODO Se ha eliminado getDatosAutomotores, hay que hacerlo devuelta
		
//		DatosReciboAutomotores recibo = AdministradorRecibo.getDatosAutomotores(cuenta.getNumero().toString(), cuenta.getDominio().toString());
		
//		assertNotNull("Debe recuperar año", recibo.getAnio());
//		assertNotNull("Debe recuperar base imponible",recibo.getBaseImponible());
//		assertNotNull("Debe recuperar categoria", recibo.getCategoria());
//		assertNotNull("Debe recuperar codigo", recibo.getCodigo());
//		assertNotNull("Debe recuperar codigo banelco", recibo.getCodigoBanelco());
//		assertNotNull("Debe recuperar cuota", recibo.getCuota());
//		assertTrue("Debe recuperar cuota", (recibo.getCuotasAdeudadas() == null || recibo.getCuotasAdeudadas() == ""));
//		assertNotNull("Debe recuperar domicilio", recibo.getDomicilio());
//		assertNotNull("Debe recuperar dominio", recibo.getDominio());
//		assertNotNull("Debe recuperar importe anual", recibo.getImporte_anual());
//		assertNotNull("Debe recuperar marca", recibo.getMarca());
//		assertNotNull("Debe recuperar modelo", recibo.getModelo());
//		assertNotNull("Debe recuperar tipo", recibo.getTipo());
//		assertNotNull("Debe recuperar titular", recibo.getTitular());
				
	}
}
