package ar.com.cognisys.sat.core.modelo.excepcion;

public class DatosInvalidosValidacionException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 4227745840919185405L;

	public DatosInvalidosValidacionException() {
		super("La cuenta ingresada es incorrecta", null);	
	}
}