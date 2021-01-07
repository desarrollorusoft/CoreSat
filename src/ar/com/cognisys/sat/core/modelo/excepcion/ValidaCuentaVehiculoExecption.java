package ar.com.cognisys.sat.core.modelo.excepcion;

public class ValidaCuentaVehiculoExecption extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -8988752173718657424L;

	public ValidaCuentaVehiculoExecption() {
		super("No se recuperaron datos para el Dominio ingresado.", null);
	}
}