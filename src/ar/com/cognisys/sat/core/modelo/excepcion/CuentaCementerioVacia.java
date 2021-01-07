package ar.com.cognisys.sat.core.modelo.excepcion;

public class CuentaCementerioVacia extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = -229215084048986138L;

	public CuentaCementerioVacia() {
		super("La cuenta no se encuentra disponible", null);
	}	
}