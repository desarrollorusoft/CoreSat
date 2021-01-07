package ar.com.cognisys.sat.v2.persistencia.modelo.queries.factory;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.CuentaABLQuery;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.CuentaAutomotorQuery;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.CuentaCementerioQuery;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.CuentaComercioQuery;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.CuentaRodadoQuery;
import ar.com.cognisys.sat.v2.persistencia.modelo.queries.interfaz.ICuentaQuery;

public class CuentaQueryFactory {

	private TipoCuentaDTO tipoCuenta;

	public CuentaQueryFactory(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public ICuentaQuery generar() {
		
		switch ( this.tipoCuenta ) {
		case ABL:
			return new CuentaABLQuery();
		case CEMENTERIO:
			return new CuentaCementerioQuery();
		case COMERCIOS:
			return new CuentaComercioQuery();
		case RODADOS:
			return new CuentaRodadoQuery();
		case VEHICULOS:
			return new CuentaAutomotorQuery();
		default:
			break;
		}
		
		return null;
	}

}
