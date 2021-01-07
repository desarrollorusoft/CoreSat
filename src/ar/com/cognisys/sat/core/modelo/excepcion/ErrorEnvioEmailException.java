package ar.com.cognisys.sat.core.modelo.excepcion;


public class ErrorEnvioEmailException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -4501080998039064793L;

	public ErrorEnvioEmailException(Exception e) {
		super("No se pudo enviar el correo correspondiente", e);
	}
}
