package ar.com.cognisys.sat.core.modelo.excepcion;


public class ConectandoException extends ExcepcionControladaError {

	private static final long serialVersionUID = 1790045804107065073L;

	public ConectandoException(Exception e) {
		super("Se perdio la conexion con el sistema", e);
	}
}
