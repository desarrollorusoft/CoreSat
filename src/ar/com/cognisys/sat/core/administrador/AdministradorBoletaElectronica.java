package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoBoletaElectronica;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorBoletaElectronica {

	public static void activarBE(Cuenta cuenta, Integer idUsuario) throws ExcepcionControladaError {
		cuenta.setAceptaBE(true);
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoBoletaElectronica dao = new DaoBoletaElectronica(con);
			
			if ( !dao.existe(cuenta) )
				dao.activar(cuenta, idUsuario);
			
		} catch(ExcepcionControladaError e) {
			cuenta.setAceptaBE(true);
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void desactivarBE(Cuenta cuenta, Integer idUsuario) throws ExcepcionControladaError {
		cuenta.setAceptaBE(false);
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoBoletaElectronica(con)).desactivar(cuenta, idUsuario);
		} catch(ExcepcionControladaError e) {
			cuenta.setAceptaBE(true);
			throw e;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void cargarCuentas(Integer idUsuario, List<Cuenta> listaCuentasAsignadas) throws ExcepcionControladaError {
		
		for (Cuenta cuenta : listaCuentasAsignadas)
			if (cuenta.isAceptaBE())
				activarBE(cuenta, idUsuario);
	}
}