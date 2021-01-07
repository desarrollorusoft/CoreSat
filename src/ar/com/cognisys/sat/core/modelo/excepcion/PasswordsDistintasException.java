package ar.com.cognisys.sat.core.modelo.excepcion;

public class PasswordsDistintasException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 13322870297613973L;

	public PasswordsDistintasException() {
		super("Las contraseñas proporcionadas deben ser iguales", null);
	}

}
