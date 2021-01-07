package ar.com.cognisys.sat.core.modelo.enums;

public enum MediosPago {

	TARJETAS_CREDITO("Tarjetas de Credito"),
	LINK("Link Pagos"),
	INTERBANKING("InterBanking"),
	PAGOMISCUENTAS("Pago Mis Cuentas"),
	MERCADO_PAGO("Mercado Pago"),
	;
	
	private String descripcion;

	private MediosPago(String descripcion) {
		this.setDescripcion(descripcion);
	}

	public static MediosPago obtener(String name) {
		for ( MediosPago mp : MediosPago.values() )
			if ( mp.name().equals( name ) )
				return mp;

		return null;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}