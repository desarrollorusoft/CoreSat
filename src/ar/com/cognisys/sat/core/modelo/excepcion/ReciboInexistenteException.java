package ar.com.cognisys.sat.core.modelo.excepcion;

public class ReciboInexistenteException extends ExcepcionControladaError {

	private static final long serialVersionUID = -4023995862832195838L;

	public ReciboInexistenteException() {
		super("No se han podido recuperar los datos del recibo seleccionado.", null);
	}
}