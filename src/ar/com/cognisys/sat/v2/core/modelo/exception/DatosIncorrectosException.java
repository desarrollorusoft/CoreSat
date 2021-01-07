package ar.com.cognisys.sat.v2.core.modelo.exception;

public class DatosIncorrectosException extends Exception {

	private static final long serialVersionUID = -4569305939257240952L;

	public DatosIncorrectosException(String message, Throwable e) {
		super( message, e );
	}
}
