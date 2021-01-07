package ar.com.cognisys.sat.core.modelo.excepcion;

public class MailVacioException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 6504232192675412581L;

	public MailVacioException() {
		super("Usted no posee un mail registrado. Por favor, presione el botón \"VOLVER\" y dirijase a \"¿PRIMERA VEZ?\" ", null);
	}
}