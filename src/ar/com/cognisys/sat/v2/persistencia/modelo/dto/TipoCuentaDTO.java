package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public enum TipoCuentaDTO {

	// @formatter:off
	ABL( TiposCuentas.ABL ), 
	RODADOS( TiposCuentas.RODADOS ), 
	VEHICULOS( TiposCuentas.VEHICULOS ), 
	COMERCIOS( TiposCuentas.COMERCIOS ),
	CEMENTERIO( TiposCuentas.CEMENTERIO ),
	PILETAS( TiposCuentas.PILETAS );
	// @formatter:on

	private TiposCuentas bo;

	private TipoCuentaDTO(TiposCuentas bo) {
		this.bo = bo;
	}

	public static TipoCuentaDTO obtener(String name) {
		for ( TipoCuentaDTO dto : values() )
			if ( dto.sos( name ) )
				return dto;

		return null;
	}
	
	public static TipoCuentaDTO obtenerPorNameBO(String boName) {
		for ( TipoCuentaDTO dto : values() )
			if ( dto.getBo().sos( boName ) )
				return dto;

		return null;
	}

	public boolean isNecesitaDatos() {
		return this.sos( TipoCuentaDTO.VEHICULOS ) || this.sos( TipoCuentaDTO.CEMENTERIO );
	}

	private boolean sos(TipoCuentaDTO other) {
		return this.equals( other );
	}

	private boolean sos(String name) {
		return this.name().equals( name );
	}

	public TiposCuentas getBo() {
		return bo;
	}

}
