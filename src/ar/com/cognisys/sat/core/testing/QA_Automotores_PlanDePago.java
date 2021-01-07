package ar.com.cognisys.sat.core.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ar.com.cognisys.sat.core.administrador.AdministradorPlanDePago;
import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;

public class QA_Automotores_PlanDePago {

	@Test
	public void probarTienePlanPagoAutomotor() throws ExcepcionControlada{
			
		CuentaVehiculos cuentaSeleccionada = new CuentaVehiculos();
		
		cuentaSeleccionada.setNumero(47507);
		
		Contribuyente contribuyente = new Contribuyente();
		
		contribuyente.setNombre("EDUARDO LUIS");;
		contribuyente.setApellido("SAVIGLIANO");
		
		cuentaSeleccionada.setDominio("AHZ164");
		
		cuentaSeleccionada.setContribuyente(contribuyente);
	
		assertTrue("No debe poseer planes de pago",!(AdministradorPlanDePago.tienePlanPagoAutomotor(cuentaSeleccionada)));
	}
}
