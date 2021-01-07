package ar.com.cognisys.sat.v2.persistencia.controlador.administrador;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.CuentaDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;

public class AdministradorCuentaDTO extends AdministradorDTO {

	public AdministradorCuentaDTO(Connection connection) {
		super( connection );
	}

	public Map<TipoCuentaDTO, List<CuentaDTO>> recuperarCuentasPorUsuario(Integer idUsuario) throws PersistenceSATException {
		return new CuentaDAO( super.connection ).recuperarCuentasPorUsuario( idUsuario );
	}

	public CuentaDTO recuperarCuenta(TipoCuentaDTO tipoCuentaDTO, String nombreUsuario, String codigoCuenta) throws PersistenceSATException {
		return new CuentaDAO( super.connection ).recuperarCuenta( tipoCuentaDTO, nombreUsuario, codigoCuenta );
	}
}