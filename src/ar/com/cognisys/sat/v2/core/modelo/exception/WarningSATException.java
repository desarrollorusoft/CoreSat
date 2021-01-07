package ar.com.cognisys.sat.v2.core.modelo.exception;

public class WarningSATException extends Exception {

	private static final long serialVersionUID = -4569305939257240952L;

	public WarningSATException(String message, Throwable e) {
		super( message, e );
	}
	
	public WarningSATException(String message) {
		super( message );
	}
}
