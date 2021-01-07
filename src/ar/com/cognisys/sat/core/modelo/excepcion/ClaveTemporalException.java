package ar.com.cognisys.sat.core.modelo.excepcion;

public class ClaveTemporalException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -2047781636386986270L;

	public ClaveTemporalException() {
		super("La contraseña ingresada temporal. Debe cambiarla", null);
	}
}