package ar.com.cognisys.sat.core.modelo.excepcion;

public class InvalidaDatoCuentaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -1720123125812987561L;

	public InvalidaDatoCuentaException(String cuenta) {
		super( "No se recuperaron datos para " + cuenta );
	}
}