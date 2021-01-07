package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.excepcion.DatosIncompletosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;

public class AdministradorProblemasIngresos extends Administrador {

	private static final long serialVersionUID = -7418985709957466905L;

	public static boolean estaCreado(String usuario) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (esUsuarioValido(usuario) && !(new DaoUsuario(con)).esCorreoValido(usuario));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static boolean estaActivo(String usuario) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (esUsuarioValido(usuario) && (new DaoUsuario(con)).estaActivo(usuario));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	private static boolean esUsuarioValido(String usuario) throws ExcepcionControladaAlerta {
		
		if (ValidadorContribuyente.esCorreoValido(usuario)) {
			return true;
		} else {
			throw new DatosIncompletosException();
		}
	}
}