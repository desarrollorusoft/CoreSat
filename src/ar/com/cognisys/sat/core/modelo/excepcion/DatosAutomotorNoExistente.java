package ar.com.cognisys.sat.core.modelo.excepcion;

public class DatosAutomotorNoExistente extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;

	public DatosAutomotorNoExistente () {
		super("No existen datos de automotores", null);
	}
}
