package ar.com.cognisys.sat.core.modelo.excepcion;

public class CuitIncorrectoException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -5244538823518503421L;

	public CuitIncorrectoException() {
		super("El CUIT es incorrecto", null);
	}
}