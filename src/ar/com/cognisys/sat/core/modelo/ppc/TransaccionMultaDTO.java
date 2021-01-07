package ar.com.cognisys.sat.core.modelo.ppc;

public class TransaccionMultaDTO {

	private long numero;
	private int codSistema;
	private int codTasa;
	private String codActual;

	public TransaccionMultaDTO(long numero, int codSistema, int codTasa, String codActual) {
		this.numero = numero;
		this.codSistema = codSistema;
		this.codTasa = codTasa;
		this.codActual = codActual;
	}

	public boolean isTasa(int codTasa) {
		return this.codTasa == codTasa;
	}

	public long getNumero() {
		return numero;
	}

	public int getCodSistema() {
		return codSistema;
	}

	public int getCodTasa() {
		return codTasa;
	}

	public String getCodActual() {
		return codActual;
	}

	public boolean isActual(String codActual) {
		return this.codActual.equals( codActual );
	}

	public boolean isSistema(int codSistema) {
		return this.codSistema == codSistema;
	}
	
}
