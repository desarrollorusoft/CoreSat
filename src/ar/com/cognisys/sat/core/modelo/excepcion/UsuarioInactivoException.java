package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioInactivoException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1609035011500467894L;

	public UsuarioInactivoException() {
		super("El usuario no se encuentra activo", null);
	}	
}