package ar.com.cognisys.sat.core.modelo.excepcion;

public class ArchivoMuyGrandeException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -6582982416485358400L;

	public ArchivoMuyGrandeException() {
		super("El archivo seleccionado excede el limite de carga (1O MB)", null);
	}
}
