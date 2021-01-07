package ar.com.cognisys.sat.core.modelo.ppc;

public class CodigoActualDTO {

	private int op;
	private String codActual;

	public CodigoActualDTO(String codActual) {
		this.codActual = codActual;
	}
	
	public CodigoActualDTO(int op, String codActual) {
		this.op = op;
		this.codActual = codActual;
	}

	public int getOp() {
		return op;
	}

	public String getCodActual() {
		return codActual;
	}

}
