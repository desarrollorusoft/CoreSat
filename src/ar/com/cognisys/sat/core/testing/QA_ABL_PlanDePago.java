package ar.com.cognisys.sat.core.testing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ar.com.cognisys.sat.core.administrador.AdministradorPlanDePago;
import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;

public class QA_ABL_PlanDePago {
	
	@Test
	public void probarTienePlanPago() throws ExcepcionControlada{
	
		CuentaABL cuentaSeleccionada = new CuentaABL();
		
		cuentaSeleccionada.setNumero(242217);
		
		Contribuyente contribuyente = new Contribuyente();
		
		contribuyente.setNombre("LUCIA INES");;
		contribuyente.setApellido("LLERENA DE COLAUTT");
		
		cuentaSeleccionada.setContribuyente(contribuyente);
		cuentaSeleccionada.setDescripcion("BERNARDO O'HIGGINS 1665");
		
		assertTrue("Debe poseer planes de pago", AdministradorPlanDePago.tienePlanPago(cuentaSeleccionada));
	}

	@Test
	public void probarGetPlanesDePago() throws ExcepcionControlada{
		
		CuentaABL cuentaSeleccionada = new CuentaABL();
		
		cuentaSeleccionada.setNumero(242217);
		
		Contribuyente contribuyente = new Contribuyente();
		
		contribuyente.setNombre("LUCIA INES");;
		contribuyente.setApellido("LLERENA DE COLAUTT");
		
		cuentaSeleccionada.setContribuyente(contribuyente);
		cuentaSeleccionada.setDescripcion("BERNARDO O'HIGGINS 1665");
		
		assertNotNull("Debe poder obtenerse porque posee 1 plan de pago.",AdministradorPlanDePago.obtenerPlanesDePago(cuentaSeleccionada));
		
	}
}
