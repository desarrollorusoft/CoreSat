package ar.com.cognisys.sat.core.modelo.comun.rs;

public enum EstadoDeclaracion {
	
	BAJA(5),
	NO_APLICA(4),
	RECHAZADA(3),
	PENDIENTE(2),
	CONFIRMADA(1),
	;
	
	private int peso;

	private EstadoDeclaracion(int peso) {
		this.peso = peso;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
}