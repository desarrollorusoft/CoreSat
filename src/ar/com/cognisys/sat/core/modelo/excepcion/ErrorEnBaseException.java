package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorEnBaseException extends ExcepcionControladaError {

	private static final long serialVersionUID = 691617181407969912L;

	public ErrorEnBaseException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}

}
