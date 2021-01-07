package ar.com.cognisys.sat.v2.core.modelo.exception;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

public class CuentaInvalidaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 8029469347330616117L;

	public CuentaInvalidaException() {
		super( "La cuenta ingresada no es correcta", null );
	}	
}