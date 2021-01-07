package ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados;

public enum TipoResultado {

	OK_GENERAL(0, "Habilitado"),
	ERROR_GENERAL(2, "Se ha superado el monto permitido"),
	OK_CUENTA(10, "Parón Habilitado"),
	ERROR_CUENTA(11, "El padrón supera el monto permitido"),
	
	ERROR_METROS(1000, "Se superan los metros totales acumulados permitidos"),
	ERROR_UNIDADES(0100, "Se superan las unidades totales acumuladas permitidas"),
	ERROR_METROS_UNIDADES(1100, "Se superan los metros y las unidades permitidas"),
	
	ERROR_SV(10000, "Se supera la cantidad acumulada de Calderas y Motores permitidos")
	;
	
	private Integer codigo;
	private String descripcion;
	
	private TipoResultado(Integer codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public static TipoResultado recuperar(int codigo) {
		
		for (TipoResultado tr : TipoResultado.values())
			if (tr.getCodigo() == codigo)
				return tr;
		
		return null;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}