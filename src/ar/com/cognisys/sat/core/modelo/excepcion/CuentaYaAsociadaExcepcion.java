package ar.com.cognisys.sat.core.modelo.excepcion;

public class CuentaYaAsociadaExcepcion extends ExcepcionControladaAlerta {
	
	private static final long serialVersionUID = 7611987585947613341L;
	
	public CuentaYaAsociadaExcepcion() {
		super("La cuenta que intenta asociar, ya se encuentra en su lista de cuentas",null);
		
	}



}
