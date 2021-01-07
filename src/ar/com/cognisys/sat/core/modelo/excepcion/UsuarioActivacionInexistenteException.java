package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioActivacionInexistenteException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 6590291877454433165L;

	public UsuarioActivacionInexistenteException() {
		super("El correo ingresado no existe en el sistema", null);
	}
}
