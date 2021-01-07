package ar.com.cognisys.sat.v2.persistencia.controlador.factory;

import java.sql.Connection;

import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.AdministradorAutomotorDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.AdministradorCementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.interfaz.IAdministradorCuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;

public class AdministradorDescripcionGeneratorFactory {

	private TipoCuentaDTO tipoCuenta;

	public AdministradorDescripcionGeneratorFactory(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public IAdministradorCuentaDetalle<?> generar(Connection connection) {
		
		switch ( this.tipoCuenta ) {
		case VEHICULOS:
			return new AdministradorAutomotorDTO( connection );
		case CEMENTERIO:
			return new AdministradorCementerioDTO( connection );
		default:
			break;
		}
		
		return null;
	}

}
