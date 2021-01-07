package ar.com.cognisys.sat.core.administrador;

import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeBE;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeClaveNivel3;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeEnvioConsultaMobile;
import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.MD5;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.BajaUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.MotivoBajaUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoBajaUsuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.*;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class AdministradorBajaUsuario extends Administrador {

	private static final long serialVersionUID = 7798276840531889948L;

	public static List<MotivoBajaUsuario> recuperarTodos() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoBajaUsuario(con)).recuperarTodos();
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void registrarMotivos(Usuario usuario, List<MotivoBajaUsuario> motivos) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoBajaUsuario(con)).registrarBaja( usuario.getIdUsuario(), motivos );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<BajaUsuario> recuperarBajas() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoBajaUsuario(con)).recuperarBajas();
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}