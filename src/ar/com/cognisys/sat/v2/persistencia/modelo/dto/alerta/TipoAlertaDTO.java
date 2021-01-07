package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta;

public enum TipoAlertaDTO {

	// @formatter:off
	USUARIO( false ),
	PUBLICITARIA( true ),
	VENCIMIENTO( true ),
	;
	// @formatter:on
	
	private boolean enCanasta;

	private TipoAlertaDTO(boolean enCanasta) {
		this.enCanasta = enCanasta;
	}

	public boolean isEnCanasta() {
		return enCanasta;
	}
	
}
