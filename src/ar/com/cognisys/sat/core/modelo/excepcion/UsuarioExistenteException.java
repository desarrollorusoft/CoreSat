package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioExistenteException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 6590291877454433165L;

	public UsuarioExistenteException() {
		super("El nombre de usuario (login) ingresado, ya existe en el sistema", null);
	}
}
