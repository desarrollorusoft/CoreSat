package ar.com.cognisys.sat.v2.persistencia.modelo.dto.factory;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaAutomotorDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaCementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;

public class FactoryCuentaDTO {

	private TipoCuentaDTO tipoCuenta;

	public FactoryCuentaDTO(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public CuentaDTO generar(String tipoCuenta, String codigoCuenta, Integer numeroCuenta, String descripcion, String alias, boolean aceptaBE) {
		
		switch ( this.tipoCuenta ) {
		case CEMENTERIO:
			return new CuentaCementerioDTO(tipoCuenta, codigoCuenta, numeroCuenta, descripcion, alias, aceptaBE);
		case VEHICULOS:
			return new CuentaAutomotorDTO(tipoCuenta, codigoCuenta, numeroCuenta, descripcion, alias, aceptaBE); 
		default:
			return new CuentaDTO(tipoCuenta, codigoCuenta, numeroCuenta, descripcion, alias, aceptaBE);
		}
	}

}
