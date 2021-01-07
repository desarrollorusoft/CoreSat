package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;
import ar.com.cognisys.sat.core.modelo.dao.DaoCaracterSAT;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorCaracterSAT {

	public static List<CaracterSAT> obtener() throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			// TODO Mejorar esto!
			return (new DaoCaracterSAT(con)).recuperarCaracteresRS();
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}