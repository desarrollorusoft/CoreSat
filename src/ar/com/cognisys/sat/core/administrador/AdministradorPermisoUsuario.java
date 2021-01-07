package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.dao.DaoPermisoUsuario;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorPermisoUsuario {

	public static void registrar(Integer idUsuario, List<Cuenta> listaCuentasAsignadas) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoPermisoUsuario dao = new DaoPermisoUsuario(con);
			
			for (Cuenta cuenta : listaCuentasAsignadas)
				dao.registrar(idUsuario, cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}	
}