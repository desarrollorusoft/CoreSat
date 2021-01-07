package ar.com.cognisys.sat.core.modelo.excepcion;

public class ExcepcionEliminarUltimaCuenta extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;

	public ExcepcionEliminarUltimaCuenta() {
		super("Su usuario debe poseer al menos una cuenta asociada.", null);

	}

}
