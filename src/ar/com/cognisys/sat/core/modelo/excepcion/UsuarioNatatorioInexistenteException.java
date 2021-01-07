package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioNatatorioInexistenteException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 6590291877454433165L;

	public UsuarioNatatorioInexistenteException() {
		super("El Documento y/o Nro. Documento ingresados son incorrectos o no existen en el sistema", null);
	}
}
