package ar.com.cognisys.sat.core.modelo.excepcion;

public class DatosInvalidosValidacionDominioException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 4641356351501289878L;

	public DatosInvalidosValidacionDominioException() {
		super("El dominio ingresado es incorrecto", null);	
	}
}