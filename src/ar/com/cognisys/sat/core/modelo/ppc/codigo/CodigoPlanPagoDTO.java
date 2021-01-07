package ar.com.cognisys.sat.core.modelo.ppc.codigo;

public abstract class CodigoPlanPagoDTO {

	private int sistRec;
	private int sistMul;
	private int lcRecargo;

	public CodigoPlanPagoDTO(int sistRec, int sistMul, int lcRecargo) {
		this.sistRec = sistRec;
		this.sistMul = sistMul;
		this.lcRecargo = lcRecargo;
	}

	public int getSistRec() {
		return sistRec;
	}

	public int getSistMul() {
		return sistMul;
	}

	public int getLcRecargo() {
		return lcRecargo;
	}
	
}
