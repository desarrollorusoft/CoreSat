package ar.com.cognisys.sat.core.modelo.excepcion;


public class CuentaAAsociarExistente extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;

	public CuentaAAsociarExistente() {
		super("La Cuenta/CUIT/Dominio que intenta registrar se encuentra en uso.", null);
	}

}
