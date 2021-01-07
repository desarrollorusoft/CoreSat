package ar.com.cognisys.sat.core.modelo.excepcion;

public class CuentaSinPlanDePagosExcepcion extends ExcepcionControladaAlerta {

	private static final long serialVersionUID = 1L;

	public CuentaSinPlanDePagosExcepcion() {
		super("La cuenta no posee Plan de Pagos", null);
	}
}
