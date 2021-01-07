package ar.com.cognisys.sat.v2.core.modelo.exception;

public class RegimenExcedenteException extends CoreSATException {

	private static final long serialVersionUID = -842703599201853529L;

	public RegimenExcedenteException() {
		super( "Sus ingresos exceden lo permitido para el Régimen Simplificado" );
	}
	
}
