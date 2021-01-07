package ar.com.cognisys.sat.v2.core.modelo.exception;

public class LoginIncorrectoException extends CoreSATException {

	private static final long serialVersionUID = -5089371354422263026L;

	public LoginIncorrectoException() {
		super( "El usuario y/o contraseña ingresados son incorrectos" );
	}
}
