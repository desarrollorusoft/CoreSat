package ar.com.cognisys.sat.core.modelo.excepcion;

public class SimulacionIncorrectaException extends ExcepcionControladaError {

	private static final long serialVersionUID = -2066148748926200435L;

	public SimulacionIncorrectaException() {
		super( "Se produjo un error al calcular los planes (error code -1)", null );
	}

}
