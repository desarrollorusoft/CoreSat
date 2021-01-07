package ar.com.cognisys.sat.core.modelo.abstracto;

public abstract class ExcepcionControlada extends Exception {

	private static final long serialVersionUID = -2123941977018530230L;

	public ExcepcionControlada(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}

	public ExcepcionControlada(String mensaje) {
		super( mensaje );
	}
}
