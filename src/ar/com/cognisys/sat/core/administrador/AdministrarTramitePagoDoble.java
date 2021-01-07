package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.tramite.TramitePagoMultiple;
import ar.com.cognisys.sat.core.modelo.dao.DaoTramitePagoDoble;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministrarTramitePagoDoble extends Administrador {

	private static final long serialVersionUID = 9184417001143981821L;

	public static List<TramitePagoMultiple> buscarTodos() throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoTramitePagoDoble(con)).recuperarTodos();
		} finally {
			AdministradorConexiones.cerrarConnection(con); 
		}
	}	
}