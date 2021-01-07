package ar.com.cognisys.sat.core.modelo.excepcion;

public class PasswordsCortaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 3595158861905377221L;

	public PasswordsCortaException() {
		super("La contraseña es corta, debe contener 3 o más caracteres", null);
	}	
}