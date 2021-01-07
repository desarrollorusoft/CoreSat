package ar.com.cognisys.sat.core.modelo.excepcion;

public class InconsistenciaCorreoExcepcion extends ExcepcionControladaAlerta {
	
	private static final long serialVersionUID = -7816921192083510886L;

	public InconsistenciaCorreoExcepcion() {
		super("Falta el correo");
	}
}