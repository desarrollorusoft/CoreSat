package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorRecuperacionDatosException extends ExcepcionControladaError {

	private static final long serialVersionUID = -873619063456148971L;

	public ErrorRecuperacionDatosException(Throwable cause) {
		super("No se pudieron recuperar sus datos", cause);
	}
	
	public ErrorRecuperacionDatosException(String proceso, Throwable cause) {
		super(proceso, cause);
	}
}