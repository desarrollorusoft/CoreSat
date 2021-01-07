package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSSolicitante;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

@Deprecated
public class FactoryRSSolicitante {

	public static RSSolicitante generar() {
		
		RSSolicitante solicitante = new RSSolicitante();
		
		return solicitante;
	}
	
	public static RSSolicitante generar(String nombre, String apellido, CaracterSAT caracter, String correo, String telefono, String celular) {
		
		RSSolicitante solicitante = generar();
		solicitante.setNombre(nombre);
		solicitante.setApellido(apellido);
		solicitante.setCorreo(correo);
		solicitante.setCaracter(caracter);
		solicitante.setTelefono(telefono);
		solicitante.setCelular(celular);
		
		return solicitante;
	}
}