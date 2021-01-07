package ar.com.cognisys.sat.core.modelo.excepcion;

public class ErrorEmailDistinto extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;
	
	public ErrorEmailDistinto() {
		super("Los email no son identicos.",null);
	}

}
