package ar.com.cognisys.sat.core.modelo.excepcion;


public class EmailInvalidoException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -4501080998039064793L;

	public EmailInvalidoException() {
		super("El email ingresado es incorrecto", null);
	}
}
