package ar.com.cognisys.sat.core.modelo.excepcion;

public class Cuota2SinSeleecionarException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -3614512302678119496L;

	public Cuota2SinSeleecionarException() {
		super("Solo esta permitido abonar la pimer cuota a vencer, o abonar todas las cuotas a vencer juntas", null);
	}	
}