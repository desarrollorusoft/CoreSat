package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorGenerarBarrasException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -106945422759383390L;

	public ErrorGenerarBarrasException() {
		super("No se pudo generar el codigo de barras", null);
	}

}
