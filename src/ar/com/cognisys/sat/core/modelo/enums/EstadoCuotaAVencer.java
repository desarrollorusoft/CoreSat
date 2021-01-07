package ar.com.cognisys.sat.core.modelo.enums;

public enum EstadoCuotaAVencer {
	
	AL_DIA("Este es el resumen de su deuda, para simular el armado de un Plan de Pago en cuotas, hacer click en ",true),
	EN_MORA("Ud. posee deuda en Mora.",true),
	CON_DEUDA_LEGALES("Usted posee deuda en Legales. Para poder realizar un Plan de Pago en Cuotas debe presentarse en la Municipalidad de Vicente Lopez, Oficina de Apremios.",false);
	
	private String mensaje;
	private Boolean puedeContinuar;

	private EstadoCuotaAVencer(String mensaje, Boolean puedeContinuar) {
		this.setMensaje(mensaje);
		this.setPuedeContinuar(puedeContinuar);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getPuedeContinuar() {
		return puedeContinuar;
	}

	public void setPuedeContinuar(Boolean puedeContinuar) {
		this.puedeContinuar = puedeContinuar;
	}
}
