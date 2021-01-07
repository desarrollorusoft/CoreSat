package ar.com.cognisys.sat.core.modelo.excepcion;

public class PasswordsAnteriorDistintaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 13322870297613973L;

	public PasswordsAnteriorDistintaException() {
		super("Las contraseña actual ingresada es incorrecta", null);
	}

}
