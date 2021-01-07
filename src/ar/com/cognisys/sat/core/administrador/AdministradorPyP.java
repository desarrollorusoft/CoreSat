package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;
import ar.com.cognisys.sat.core.modelo.dao.DaoCarteleria;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorPyP {

	public static Map<Integer, List<TipoCartel>> recuperarCarteleria() throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoCarteleria(con)).recuperar();
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}