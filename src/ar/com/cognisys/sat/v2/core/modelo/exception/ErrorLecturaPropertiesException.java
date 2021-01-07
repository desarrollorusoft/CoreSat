package ar.com.cognisys.sat.v2.core.modelo.exception;

public class ErrorLecturaPropertiesException extends CoreSATException {

	private static final long serialVersionUID = 4935413910291243225L;

	public ErrorLecturaPropertiesException(Throwable e) {
		super( "Ocurrio un Error al tratar de leer el properties",e);
	}

}
