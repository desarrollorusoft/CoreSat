package ar.com.cognisys.sat.core.modelo.excepcion;

public class DebeCompletarRsException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 9014087972272465068L;

	public DebeCompletarRsException() {
		super("Para asociar la cuenta primero debe completar el Régimen Simplificado", null);
	}

}
