package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.Domicilio;

public class FactoryDomicilio {

	public static Domicilio generarInstancia() {
		
		Domicilio domicilio = new Domicilio();
		
		return domicilio;
	}

	public static Domicilio generarInstanciaCompleta(String calle, Integer altura, String piso, 
													 String departamento, Integer codigoPostal) {
		
		Domicilio domicilio = generarInstancia();
		domicilio.setCalle(calle);
		domicilio.setAltura(altura);
		domicilio.setPiso(piso);
		domicilio.setDepartamento(departamento);
		domicilio.setCodigoPostal(codigoPostal);
		
		return domicilio;
	}
}