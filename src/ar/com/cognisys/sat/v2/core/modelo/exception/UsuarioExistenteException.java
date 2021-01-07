package ar.com.cognisys.sat.v2.core.modelo.exception;

public class UsuarioExistenteException extends CoreSATException {

	private static final long serialVersionUID = -5903659015314572940L;

	public UsuarioExistenteException() {
		super( "Usted ya se encuentra registrado en el sistema" );
	}

}
