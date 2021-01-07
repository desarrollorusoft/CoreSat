package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.rs.SolicitanteRS;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

public class FactorySolicitanteRS {

	public static SolicitanteRS generar() {
		
	SolicitanteRS solicitante = new SolicitanteRS();
		
		return solicitante;
	}
	
	public static SolicitanteRS generar(String cuit, String nombre, String apellido, CaracterSAT caracter, 
									    String correo, String telefono, String celular) {
		
		SolicitanteRS solicitante = generar();
		solicitante.setCuit(cuit);
		solicitante.setNombre(nombre);
		solicitante.setApellido(apellido);
		solicitante.setCorreo(correo);
		solicitante.setCaracter(caracter);
		solicitante.setTelefono(telefono);
		solicitante.setCelular(celular);
		
		return solicitante;
	}
}