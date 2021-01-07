package ar.com.cognisys.sat.core.modelo.excepcion;

public class ClaveInvalidaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -2047781636386986270L;

	public ClaveInvalidaException() {
		super("La contraseña ingresada es incorrecta", null);
	}
}