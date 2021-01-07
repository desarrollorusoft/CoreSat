package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorReciboException extends ExcepcionControladaError {
	
	private static final long serialVersionUID = 9000668123875268959L;

	public ErrorReciboException(Throwable cause) {
		super("No se pudo recuperar los datos correspondientes a su recibo. Comuniquese con la municipalidad", cause);
	}
}