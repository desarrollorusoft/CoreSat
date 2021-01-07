package ar.com.cognisys.sat.v2.persistencia.controlador.administrador;

import java.sql.Connection;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.interfaz.IAdministradorCuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.CementerioDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaCementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;

public class AdministradorCementerioDTO extends AdministradorDTO implements IAdministradorCuentaDetalle<CuentaCementerioDTO> {

	public AdministradorCementerioDTO(Connection connection) {
		super( connection );
	}
	
	public CementerioDTO recuperarDatos(CuentaCementerioDTO cuenta) throws ErrorRecuperandoDatos {
		CementerioDAO dao = new CementerioDAO( super.connection );
		
		return dao.recuperarDatos( cuenta.getNumero() );
	}
}