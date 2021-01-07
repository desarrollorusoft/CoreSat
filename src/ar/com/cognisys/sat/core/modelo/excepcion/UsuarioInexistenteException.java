package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioInexistenteException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 6590291877454433165L;

	public UsuarioInexistenteException() {
		super("El correo y/o contraseña ingresados son incorrectos o no existen en el sistema", null);
	}
}
