package ar.com.cognisys.sat.core.modelo.excepcion;

public class DatosInvalidosException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 5820573339938429002L;

	public DatosInvalidosException() {
		super("Los datos ingresados son incorrectos", null);
	}
}