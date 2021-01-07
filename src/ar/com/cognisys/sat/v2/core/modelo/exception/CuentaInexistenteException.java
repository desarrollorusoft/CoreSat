package ar.com.cognisys.sat.v2.core.modelo.exception;

public class CuentaInexistenteException extends CoreSATException {

	private static final long serialVersionUID = -6529797098642424155L;

	public CuentaInexistenteException() {
		super( "La cuenta no se encuentra en el sistema" );
	}

	
}
