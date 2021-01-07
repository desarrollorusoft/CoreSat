package ar.com.cognisys.sat.core.modelo.excepcion;

public class UsuarioSinCuentaAutomotor extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;
	
	public UsuarioSinCuentaAutomotor() {
		super("El Usuario no posee cuentas de Automotores asociadas",null);
		
	}
}
