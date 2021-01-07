package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

public enum TipoOEP {

	TOLDO, POSTE, 
	;

	public static TipoOEP recuperar(String name) {
		
		for (TipoOEP tipo : values())
			if (tipo.name().equals(name))
				return tipo;
		
		return null;
	}
	
	public boolean sos(TipoOEP otro) {
		return otro == this;
	}
}