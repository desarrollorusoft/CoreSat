package ar.com.cognisys.sat.core.modelo.excepcion;

import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;

public class ExcepcionControladaAlerta extends ExcepcionControlada {

	private static final long serialVersionUID = 6174629632022987908L;

	public ExcepcionControladaAlerta(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
	
	public ExcepcionControladaAlerta(String mensaje) {
		super(mensaje);
	}
}