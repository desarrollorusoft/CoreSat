package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public enum TipoCuentaView {

	AUTOMOTOR( TiposCuentas.VEHICULOS ),
	RODADOS( TiposCuentas.RODADOS ),
	COMERCIO( TiposCuentas.COMERCIOS ),
	CEMENTERIO( TiposCuentas.CEMENTERIO ),
	ABL( TiposCuentas.ABL ),
	;
	
	private TiposCuentas bo;

	private TipoCuentaView(TiposCuentas bo) {
		this.bo = bo;
	}

	public static TipoCuentaView obtener(TiposCuentas bo) {
		for ( TipoCuentaView tcv : values() )
			if ( tcv.sos( bo ) )
				return tcv;

		return null;
	}

	public static TipoCuentaView obtener(String name) {
		for ( TipoCuentaView tcv : values() )
			if ( tcv.sos( name ) )
				return tcv;

		return null;
	}

	private boolean sos(TiposCuentas bo) {
		return this.bo.equals( bo );
	}

	private boolean sos(String name) {
		return this.name().equals( name );
	}
	
	public TiposCuentas getBo() {
		return bo;
	}

}
