package ar.com.cognisys.sat.v2.core.modelo.exception;

public abstract class CoreSATException extends Exception {

	private static final long serialVersionUID = -6936025718773550229L;

	public CoreSATException(String message) {
		super( message );
	}
	
	public CoreSATException(String message, Throwable e) {
		super( message, e );
	}
}
