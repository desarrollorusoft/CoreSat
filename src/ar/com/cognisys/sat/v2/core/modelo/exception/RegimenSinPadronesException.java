package ar.com.cognisys.sat.v2.core.modelo.exception;

public class RegimenSinPadronesException extends CoreSATException {

	private static final long serialVersionUID = 6451240495842432162L;

	public RegimenSinPadronesException() {
		super( "El cuit ingresado no posee padrones asociados" );
	}
	
}
