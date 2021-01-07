package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSActividad;

@Deprecated
public class FactoryRSActividad {

	public static RSActividad generar() {
		
		RSActividad actividad = new RSActividad();
		
		return actividad;
	}
	
	public static RSActividad generar(String cuit, String correo, String telefono, String celular) {
		
		RSActividad actividad = generar();
		actividad.setCuit(cuit);
		actividad.setCorreo(correo);
		actividad.setTelefono(telefono);
		actividad.setCelular(celular);
		
		return actividad;
	}

	public static RSActividad generar(String cuit, String correo, String clave, String telefono, String celular) {
		
		RSActividad actividad = generar(cuit, correo, telefono, celular);
		actividad.setClave(clave);
		
		return actividad;
	}
}