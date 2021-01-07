package ar.com.cognisys.sat.core.modelo.excepcion;


public class EmailYaExisteException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;
	
	public EmailYaExisteException() {
		super("El Correo ingresado ya se encuentra registrado", null);
	}
}