package ar.com.cognisys.sat.v2.persistencia.controlador.administrador;

import java.sql.Connection;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.interfaz.IAdministradorCuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.AutomotorDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.AutomotorDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaAutomotorDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorCargandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;

public class AdministradorAutomotorDTO extends AdministradorDTO implements IAdministradorCuentaDetalle<CuentaAutomotorDTO> {

	public AdministradorAutomotorDTO(Connection connection) {
		super( connection );
	}
	
	public AutomotorDTO recuperarDatos(CuentaAutomotorDTO cuenta) throws ErrorCargandoDatos, ErrorRecuperandoDatos {
		AutomotorDAO dao = new AutomotorDAO( super.connection );
		
		dao.cargarDatos( cuenta.getCodigo() );
		return dao.recuperarDatosCargados( cuenta.getNumero() );

	}

}
