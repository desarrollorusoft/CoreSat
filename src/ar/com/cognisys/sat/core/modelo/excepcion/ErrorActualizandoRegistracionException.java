package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorActualizandoRegistracionException extends ExcepcionControladaError {

	private static final long serialVersionUID = -8526256123298632443L;

	public ErrorActualizandoRegistracionException(Throwable cause) {
		super("No se pudo actualizar su informacion. Por favor, intente nuevamente", cause);
	}
}