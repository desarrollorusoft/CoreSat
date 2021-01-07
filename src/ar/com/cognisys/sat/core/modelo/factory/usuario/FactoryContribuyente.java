package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.Domicilio;

public class FactoryContribuyente {

	public static Contribuyente generarInstanciaVacia() {
		
		Contribuyente contribuyente = new Contribuyente();
		
		return contribuyente;
	}
	
	public static Contribuyente generarInstanciaCompleta(String nombre, String apellido) {
		
		Contribuyente contribuyente = generarInstanciaVacia();
		contribuyente.setNombre(nombre);
		contribuyente.setApellido(apellido);
		
		return contribuyente;
	}	
	
	public static Contribuyente generarInstanciaCompleta(String nombre, String apellido, Integer numeroDocumento, 
													  	 String correo, Domicilio domicilio, String telefono, 
													  	 String fax, Integer tipoDocumento) {
		
		Contribuyente contribuyente = generarInstanciaVacia();
		contribuyente.setNombre(nombre);
		contribuyente.setApellido(apellido);
		contribuyente.setNumeroDocumento(numeroDocumento);
		contribuyente.setCorreo(correo);
		contribuyente.setDomicilio(domicilio);
		contribuyente.setTelefono(telefono);
		contribuyente.setFax(fax);
		contribuyente.setTipoDocumento(tipoDocumento);
		
		return contribuyente;
	}
}