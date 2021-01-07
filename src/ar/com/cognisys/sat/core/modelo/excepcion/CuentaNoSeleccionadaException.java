package ar.com.cognisys.sat.core.modelo.excepcion;

public class CuentaNoSeleccionadaException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 7663006648611423644L;

	public CuentaNoSeleccionadaException() {
		super("Debe seleccionar una cuenta", null);
	}
}