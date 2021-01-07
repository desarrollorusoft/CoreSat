package ar.com.cognisys.sat.core.modelo.excepcion;

public class DatosIncompletosException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 22873298243270304L;

	public DatosIncompletosException() {
		super("Debe completar todos los datos para continuar", null);
	}
}
