package ar.com.cognisys.sat.core.modelo.excepcion;

import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;

public class ExcepcionControladaError extends ExcepcionControlada {

	private static final long serialVersionUID = 724436999875137583L;

	public ExcepcionControladaError(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}