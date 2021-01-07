package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeCambioClave;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeReenvioRegistracion;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeRegistracionMobileConfirmacion;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeSolicitudCodigoMobile;
import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.GeneradorClaves;
import ar.com.cognisys.sat.core.modelo.comun.MD5;
import ar.com.cognisys.sat.core.modelo.comun.SegurizadorClaves;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentasContribuyente;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaAbl;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaCementerio;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaMotovehiculo;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.DatosIncompletosException;
import ar.com.cognisys.sat.core.modelo.excepcion.DatosInvalidosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.UsuarioInexistenteException;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentasUsuario;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorGenerico;
import ar.com.cognisys.sat.v2.core.modelo.exception.CuentaInvalidaException;

public class AdministradorContribuyente extends Administrador {

	private static final long serialVersionUID = -4476989290778678115L;
	
	/**
	 * Metodo que se usa en Soporte Usuario - HSAT
	 * @param cuit
	 * @return
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static Usuario ingresoContribuyente(String cuit) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		Usuario usuario = null;
		
		if (ValidadorContribuyente.esCorreoValido(cuit) || ValidadorContribuyente.esCuitValido(cuit) ) {
			
			usuario = buscarUsuario(cuit);
			
			if (usuario == null) {
				throw new UsuarioInexistenteException();
			}
		} else {
			throw new DatosIncompletosException();
		}
		
		return usuario;
	}
	
	private static Usuario buscarUsuario(String cuit) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario daoUsuario = new DaoUsuario( con );

			Integer idUsuario = daoUsuario.recuperarIdUsuario( cuit );

			CuentasUsuario cuentas = buscarCuentas( idUsuario );

			return new DaoUsuario( con ).buscarYRecuperarDatosUsuario( idUsuario, cuentas );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	/**
	 * Metodo que se usa en el HSAT para reseatear Clave
	 * @param u
	 * @param nuevaClave
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void resetearClave(Usuario u, String nuevaClave) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			String hashClave = nuevaClave;
			
			if (u.tieneCuit())
				hashClave = MD5.getMD5(nuevaClave);
			else
				hashClave = SegurizadorClaves.codificar(nuevaClave);
				
			(new DaoUsuario(con)).actualizarClave(u.getIdUsuario(), hashClave);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
		
		informarCambioClave(u.getCorreo(), nuevaClave);
	}
	
	/**
	 * Metodo que se utiliza en HSAT para reiniciar la clave de un usuario
	 * @param idUsuario
	 * @param correo
	 * @throws ExcepcionControladaAlerta
	 */
	public static void reenvioActivacion(Integer idUsuario, String correo) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeReenvioRegistracion(correo, idUsuario) );
	}
	
	public static String generarCodigoActivacion(Integer idUsuario) throws ExcepcionControladaError {
		
		String codigo = GeneradorClaves.getClave(5);
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).generarCodigoActivacion(idUsuario, codigo);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
		
		return codigo;
	}

	/**
	 * Metodo utilizado por Mobile para la activacion del usuario.
	 * 
	 * @param cuit
	 * @param codigo
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void activacionMobile(String cuit, String codigo) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {			
			/* Esto se puede mejorar agregando el codigo al usuario */
			Usuario u = AdministradorUsuario.buscar(cuit);
			
			if (u == null)
				throw new ExcepcionControladaAlerta("La CUILL/CUIT no se encuentra en el sistema", null);
			
			con = AdministradorConexiones.obtenerConexion();
			String c = (new DaoUsuario(con)).recuperarCodigoActivacion(u.getIdUsuario());
			
			if (c == null) {
				throw new ExcepcionControladaAlerta("Usted no tiene un c�digo de activaci�n registrado. Por favor, solicite uno", null);
			
			} else if (!c.trim().equals(codigo.trim())) {
				throw new ExcepcionControladaAlerta("El c�digo de activaci�n ingresado es incorrecto", null);
			} else {
				activar(u.getIdUsuario());
			}
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por mobile para pedir un nuevo codigo de activaci�n
	 * 
	 * @param cuit
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta 
	 */
	public static void solicitarCodigoActivacion(String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {			
			Usuario u = AdministradorUsuario.buscar(cuit);
			if ( u == null )
				throw new UsuarioInexistenteException();
				
			String codigo = GeneradorClaves.getClave(5);
			
			con = AdministradorConexiones.obtenerConexion();
			(new DaoUsuario(con)).generarCodigoActivacion(u.getIdUsuario(), codigo);
			
			informarGeneracionCodigoActivacion(u.getCorreo(), codigo);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	private static void informarGeneracionCodigoActivacion(String correo, String codigo) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeSolicitudCodigoMobile(correo, codigo) );
	}
	
	public static void informarRegistracionMobile(String correo, String codigo) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeRegistracionMobileConfirmacion(correo, codigo) );
	}
	
	/**
	 * Se utiliza el metodo en el WS Mobile
	 * @param cuentaDominio
	 * @param tipoCuenta
	 * @param cuit
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 * @throws ar.com.cognisys.sat.v2.core.modelo.exception.UsuarioInexistenteException 
	 */
	public static void generarNuevaClave(String cuentaDominio, TiposCuentas tipoCuenta, String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		String claveNueva = GeneradorClaves.getClave(6);
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			Usuario u = dao.recuperar( cuit );
			
			if ( u == null )
				throw new UsuarioInexistenteException();
			else if ( !dao.tieneCuenta(u.getIdUsuario(), tipoCuenta, cuentaDominio) )
				throw new CuentaInvalidaException();
			
			informarCambioClave(u.getCorreo(), claveNueva);
			
			dao.actualizarClave(cuit, MD5.getMD5(claveNueva));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}	
	}
	
	/**
	 * @param cuentaDominio
	 * @param tipoCuenta
	 * @param cuit
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 * @throws ar.com.cognisys.sat.v2.core.modelo.exception.UsuarioInexistenteException 
	 */
	public static void generarNuevaClaveSat(String cuentaDominio, TiposCuentas tipoCuenta, String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		String claveNueva = GeneradorClaves.getClave(6);
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			Usuario u = dao.recuperar( cuit );
			
			if ( u == null )
				throw new UsuarioInexistenteException();
			else if ( !dao.tieneCuenta(u.getIdUsuario(), tipoCuenta, cuentaDominio) )
				throw new CuentaInvalidaException();
			
			informarCambioClave(u.getCorreo(), claveNueva);
			
			String hashClave = claveNueva;
			
			if ( u.tieneCuit() )
				hashClave = MD5.getMD5(claveNueva);
			else
				hashClave = SegurizadorClaves.codificar(claveNueva);
			
			dao.actualizarClave(u.getIdUsuario(), hashClave);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}	
	}

	public static void generarNuevaClaveSat(String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		String claveNueva = GeneradorClaves.getClave(6);
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			DaoUsuario dao = new DaoUsuario(con);
			Usuario u = dao.recuperar( cuit );

			if ( u == null )
				throw new ExcepcionControladaAlerta("El dato ingresado es incorrecto");

			informarCambioClave(u.getCorreo(), claveNueva);

			dao.actualizarClave(u.getIdUsuario(), MD5.getMD5(claveNueva));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void generarNuevaClaveSat(String tipoDoc, String nroDoc, String cuit) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		String claveNueva = GeneradorClaves.getClave(6);
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			DaoUsuario dao = new DaoUsuario(con);
			Usuario u = dao.recuperar( cuit );

			if ( u == null )
				throw new UsuarioInexistenteException();
			else if ( !dao.tieneCuenta(u.getIdUsuario(), tipoDoc, nroDoc) )
				throw new CuentaInvalidaException();

			informarCambioClave(u.getCorreo(), claveNueva);

			String hashClave = claveNueva;

			if ( u.tieneCuit() )
				hashClave = MD5.getMD5(claveNueva);
			else
				hashClave = SegurizadorClaves.codificar(claveNueva);

			dao.actualizarClave(u.getIdUsuario(), hashClave);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por Recupero Clave de C.F.
	 * @param cuit
	 * @param correo
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 * @throws CuentaInvalidaException
	 */
	public static void generarNuevaClave(String cuit, String correo) throws ExcepcionControladaError, ExcepcionControladaAlerta, CuentaInvalidaException {
		
		String claveNueva = GeneradorClaves.getClave(6);
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			Usuario u = dao.recuperar( cuit );
			
			if ( u == null )
				throw new UsuarioInexistenteException();
			else if ( !u.tieneCorreo(correo) )
				throw new DatosInvalidosException();
			
			informarCambioClave(u.getCorreo(), claveNueva);
			
			dao.actualizarClave(cuit, MD5.getMD5(claveNueva));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}	
	}
	
	private static void informarCambioClave(String correo, String claveNueva) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeCambioClave(correo, claveNueva) );
	}
	
	/**
	 * Metodo usado para el SAT Web
	 * @param correo
	 * @param telefono1
	 * @param telefono2
	 * @param nombre
	 * @param apellido
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static void actualizarDatosUsuario(Integer idUsuario, String correo, String telefono1, String telefono2, String nombre, 
											  String apellido, Integer nivel) throws ExcepcionControladaAlerta, ExcepcionControladaError {

		ValidadorContribuyente.validarCorreo(correo);
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			
			if ( nivel != null )
				dao.actualizarDatosUsuario(idUsuario, correo, telefono1, telefono2, nombre, apellido, nivel);
			else
				dao.actualizarDatosUsuario(idUsuario, correo, telefono1, telefono2, nombre, apellido);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	@Deprecated
	public static void cambiarClave(Integer id, String claveAnterior, String claveNueva, String claveNuevaRepetida) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		ValidadorGenerico.validarCambioClave(claveAnterior, claveNueva, claveNuevaRepetida);
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).actualizarClave(id, claveNueva);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Este metodo se usa en el Mobile
	 * @param id
	 * @param claveNueva
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static void cambiarClave(Integer id, String claveNueva) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).actualizarClave(id, claveNueva);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void cambiarClave(String cuit, String clave) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		ValidadorGenerico.validarCambioClave(cuit, clave);
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).actualizarClave(cuit, clave);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Este metodo se usa en el WS Mobile y HSAT
	 * @param idUsuario
	 * @throws ExcepcionControladaError
	 */
	public static void activar(Integer idUsuario) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).activarUsuario(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Cuentas
	 * @throws ExcepcionControladaError 
	 */
	public static List<Usuario> recuperarListaUsuarios(String datoCuenta, TiposCuentas tipoCuenta) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperarListaUsuarios(datoCuenta, tipoCuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Usuarios
	 * @throws ExcepcionControladaError 
	 */
	public static void borrarUsuario(Integer idUsuario) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).borrarUsuario(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Usuarios
	 * @throws ExcepcionControladaError 
	 */
	public static CuentasUsuario buscarCuentas(Integer idUsuario) throws ExcepcionControladaError {

        Connection con = null;

        try {
            con = AdministradorConexiones.obtenerConexion();

            CuentasUsuario cuentasUsuario = FactoryCuentasUsuario.generar();
            cuentasUsuario.agregarCuentas(new DaoCuentaAbl(con).recuperarCuentas(idUsuario));
            cuentasUsuario.agregarCuentas(new DaoCuentaAutomotor(con).recuperarCuentas(idUsuario));
            cuentasUsuario.agregarCuentas(new DaoCuentaMotovehiculo(con).recuperarCuentas(idUsuario));
            cuentasUsuario.agregarCuentas(new DaoCuentaCementerio(con).recuperarCuentas(idUsuario));

            return cuentasUsuario;
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
	}
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Usuarios
	 * @throws ExcepcionControladaError 
	 */
	public static Usuario buscarUsuario(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperarUsuario(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Usuarios
	 * Metodo usado en WS Mobile
	 * <br/><br/>
	 * <b>USAR EL METODO "AdministradorUsuario.buscar(cuit)"</b>
	 * 
	 * @throws ExcepcionControladaError 
	 */
	@Deprecated 
	public static Usuario buscarUsuarioPorCuit(String cuit) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperar(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por HSAT - Soporte de Usuarios
	 * Metodo usado en WS Mobile
	 * @throws ExcepcionControladaError 
	 */
	public static Usuario buscarUsuarioPorCorreo(String correo) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperarUsuario(correo);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void actualizarTyC(Usuario usuario) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).aceptaTyC(usuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

    public static List<Cuenta> buscarCuentasPorCuit(String cuit) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentasContribuyente(con)).buscarPorCuit(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
    }
}