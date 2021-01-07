package ar.com.cognisys.sat.v2.core.modelo.exception;

public class UsuarioInexistenteException extends CoreSATException {

	private static final long serialVersionUID = 1464882248593672194L;

	public UsuarioInexistenteException() {
		super( "Usted no se encuentra registrado en el sistema" );
	}
}