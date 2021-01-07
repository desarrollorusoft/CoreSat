package ar.com.cognisys.sat.core.modelo.factory.configuracion;

import ar.com.cognisys.sat.core.modelo.comun.configuraciones.CorreoOficial;

public class FactoryCorreoOficial {
	
	public static CorreoOficial generarInstancia() {
		return new CorreoOficial();
	}
	
	public static CorreoOficial generarInstancia(Long id, String categoria, String correo) {
		CorreoOficial c = generarInstancia();
		c.setCategoria(categoria);
		c.setCorreo(correo);
		c.setId(id);
		return c;
	}
}