package ar.com.cognisys.sat.core.modelo.ppc;

public class PorcentajeRecMul {

	private float liPorcRec;
	private float liPorcMul;

	public PorcentajeRecMul(float liPorcRec, float liPorcMul) {
		this.liPorcRec = liPorcRec;
		this.liPorcMul = liPorcMul;
	}
	
	public boolean isLiPorcRec(float liPorcRec) {
		return this.liPorcRec == liPorcRec;
	}
	
	public boolean isLiPorcMul(float liPorcMul) {
		return this.liPorcMul == liPorcMul;
	}

	public float getLiPorcRec() {
		return liPorcRec;
	}

	public float getLiPorcMul() {
		return liPorcMul;
	}

}
