package ar.com.cognisys.sat.core.modelo.excepcion;

public class ContribuyenteInexistenteException extends ExcepcionControladaError {

	private static final long serialVersionUID = 7746898321011996881L;

	public ContribuyenteInexistenteException() {
		super("No existe un usuario registrado con los datos ingresados", null);
	}	
}