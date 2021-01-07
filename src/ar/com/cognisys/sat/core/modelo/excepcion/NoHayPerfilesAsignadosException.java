package ar.com.cognisys.sat.core.modelo.excepcion;

public class NoHayPerfilesAsignadosException extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 276252358628515930L;

	public NoHayPerfilesAsignadosException() {
		super("Debe asignar al menos un perfil", null);
	}
}