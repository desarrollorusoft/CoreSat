package ar.com.cognisys.sat.core.modelo.ppc;

public class Periodo {

	private int codTasa;
	private int anio;
	private int cuota;

	public Periodo() { }
	
	public Periodo(int codTasa, int anio, int cuota) {
		this.codTasa = codTasa;
		this.anio = anio;
		this.cuota = cuota;
	}

	public int getCodTasa() {
		return codTasa;
	}

	public int getAnio() {
		return anio;
	}

	public int getCuota() {
		return cuota;
	}
	
	
}
