package ar.com.cognisys.sat.v2.core.modelo.exception;

public class FileManagerException extends CoreSATException {

	private static final long serialVersionUID = -56512489478788269L;

	public FileManagerException(Throwable e) {
		super( "No es posible crear el archivo", e );
	}

}
