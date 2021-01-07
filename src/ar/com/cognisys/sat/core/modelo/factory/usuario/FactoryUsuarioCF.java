package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;

public class FactoryUsuarioCF {

	public static UsuarioCF generar() {
		
		UsuarioCF usuario = new UsuarioCF();
		
		return usuario; 
	}
	
	public static UsuarioCF generar(String cuit, String correo, String clave, String telefono, String razonSocial) {
		
		UsuarioCF usuario = generar();
		usuario.setCuit(cuit);
		usuario.setCorreo(correo);
		usuario.setClave(clave);
		usuario.setTelefono(telefono);
		usuario.setRazonSocial(razonSocial);
		
		return usuario; 
	}
}