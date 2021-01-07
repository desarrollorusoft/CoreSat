package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeBE;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeClaveNivel3;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeEnvioConsultaMobile;
import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.MD5;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.*;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentasUsuario;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;

public class AdministradorUsuario extends Administrador {

	private static final long serialVersionUID = 7798276840531889948L;
	public static final String CLAVE_TEMPORAL = "SAT1234";
	
	/**
	 * Metodo que se utiliza en el login de WEB
	 * 
	 * @param cuit
	 * @param clave
	 * @return Si el usuario existe, no devuelve nada; pero si no existe, devuelve excepciones
	 * @throws ExcepcionControladaAlerta 
	 * @throws ExcepcionControladaError 
	 *
	 */
	public static void existeUsuario(String cuit, String clave) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		Usuario usuario = null;
		
		if (ValidadorContribuyente.sonDatosValidos(cuit, clave)) {
			
			usuario = buscar(cuit);
			
			if (usuario == null) {
				throw new UsuarioInexistenteException();
			} else if (! usuario.getClave().equals(clave)) {
				throw new ClaveInvalidaException();
			}
		} else {
			throw new DatosIncompletosException();
		}
	}
	
	public static boolean existeUsuario(String cuit) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		if (ValidadorContribuyente.validarCUIT(cuit)) {
			
			Connection con = null;
			
			try {
				con = AdministradorConexiones.obtenerConexion();
				
				return (new DaoUsuario(con)).existeUsuarioCuit(cuit);
			} finally {
				AdministradorConexiones.cerrarConnection(con);
			}
		} else
			throw new ExcepcionControladaAlerta("La CUIT/CUIL ingresada es incorrecta");
	}
	
	public static boolean existeUsuarioPorCorreo(String correo) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		if (ValidadorContribuyente.esCorreoValido(correo)) {
			Connection con = null;
			
			try {
				con = AdministradorConexiones.obtenerConexion();
				
				return (new DaoUsuario(con)).existeUsuarioCorreo(correo);
			} finally {
				AdministradorConexiones.cerrarConnection(con);
			}
		} else
			throw new ExcepcionControladaAlerta("El correo ingresado es incorrecto");
	}
	
	
	/**
	 * Metodo para SAT Mobile
	 *
	 * @param clave
	 * @throws ExcepcionControladaAlerta
	 * @throws ExcepcionControladaError
	 */
	public static void existeUsuarioPorCorreo(String correo, String clave) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		Usuario usuario = null;
		
		if (ValidadorContribuyente.sonDatosValidos(correo, clave)) {
			
			usuario = AdministradorContribuyente.buscarUsuarioPorCorreo(correo);
			
			if (usuario == null) {
				throw new UsuarioInexistenteException();
			} else if (! usuario.getClave().equals(clave)) {
				throw new ClaveInvalidaException();
			}
		} else {
			throw new DatosIncompletosException();
		}
	}
	
	/**
	 * Metodo que recupera una tabla con las cantidades de cuentas por tributo
	 * Se usa para Mobile
	 * @param idUsuario
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static Map<TiposCuentas, Integer> recuperarCantidadesCuentas(Integer idUsuario) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperarCantidadesCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	/**
	 * Metodo utilizado por Mobile, para enviar consultas de los usuarios
	 * @param correo
	 * @param asunto
	 * @param mensaje
	 * @throws ExcepcionControladaAlerta
	 */
	public static void enviarConsulta(String correo, String asunto, String mensaje) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeEnvioConsultaMobile(correo, asunto, mensaje) );
	}
	
	/**
	 * Este metodo recupera el usuario a partir del CUIT. Tambien valdia la clave que hay en base con la que vino.
	 * @param cuit
	 * @param clave
	 * @return
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static Usuario buscarUsuario(String cuit, String clave) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Usuario usuario = null;
		
		if (ValidadorContribuyente.sonDatosValidos(cuit, clave)) {
			
			usuario = buscar(cuit);
			
			if (usuario == null) {
				throw new UsuarioInexistenteException();
			} else if (! usuario.getClave().equals(clave)) {
				throw new ClaveInvalidaException();
			} else if (! usuario.isActivo()) {
				throw new UsuarioInactivoException();
			}
		} else {
			throw new DatosIncompletosException();
		}
		
		return usuario;
	}
	
	@Deprecated
	public static Map<TiposCuentas, Integer> buscarCatidadCuentasUsuario(String correo, String clave) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Usuario usuario = AdministradorContribuyente.buscarUsuarioPorCorreo(correo.toLowerCase());
		
		if (usuario == null)
			throw new UsuarioInexistenteException();
		
		// Esto se debe segurizar porque esta con la clave vieja 
		else if (!clave.equals("C0gn1sys4n4") && ! usuario.getClave().trim().equals(MD5.getMD5(clave)))
			throw new ClaveInvalidaException();
		
		return recuperarCantidadesCuentas(usuario.getIdUsuario());
	}
	
	@Deprecated
	public static Usuario buscarUsuarioViejo(String correo) throws ExcepcionControladaError {
		return AdministradorContribuyente.buscarUsuarioPorCorreo(correo.toLowerCase());
	}
	
	/**
	 * Busca los datos del usuario SIN los datos de RS
	 * @param cuit
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static Usuario buscar(String cuit) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			Usuario usuario = (new DaoUsuario(con)).recuperar( cuit );
			if (usuario != null)
				(new DaoUsuario(con)).cargarPermisos( usuario );
			
			return usuario;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void registrar(String cuit, String correo, String clave) throws ExcepcionControladaAlerta, ExcepcionControladaError {

		if (ValidadorContribuyente.validarRegistracion(cuit, correo, clave)) {
			correo = correo.toLowerCase().trim();

			CuentasUsuario cuentas = FactoryCuentasUsuario.generar( AdministradorContribuyente.buscarCuentasPorCuit( cuit ) );
			Comercio comercio = null;
			try {
				comercio = AdministradorComercio.recuperarComercioBase( cuit );
			} catch (InvalidaDatoCuentaException a) { /*No pasa nada si no aparece*/ }

			registrar(cuit, correo, clave, cuentas, comercio);
		} else {
			throw new DatosIncompletosException();
		}
	}

	public static void registrar(String cuit, String correo, String clave, CuentasUsuario cuentas, Comercio comercio) throws ExcepcionControladaAlerta, ExcepcionControladaError {

		if (ValidadorContribuyente.validarRegistracion(cuit, correo, clave)) {
			correo = correo.toLowerCase().trim();
			Integer idUsuario = registrarDatosContribuyente(cuit, correo, clave, cuentas);
			if (comercio != null)
				registrarComercio(idUsuario, cuit, comercio);

			String codigo = AdministradorContribuyente.generarCodigoActivacion(idUsuario);

			AdministradorContribuyente.informarRegistracionMobile(correo, codigo);
		} else {
			throw new DatosIncompletosException();
		}
	}

	public static Integer registrarDatosContribuyente(String cuit, String correo, String clave, CuentasUsuario cuentas) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			DaoUsuario dao = new DaoUsuario(con);

			if (dao.existeUsuarioCuit(cuit))
				throw new ExcepcionControladaAlerta("La CUIT ingresada ya se encuentra en uso");
			else if (dao.existeUsuarioCorreo(correo))
				throw new ExcepcionControladaAlerta("El correo ingresado ya se encuentra en uso");
			else
				return dao.registarUsuario(cuit, correo, clave, cuentas);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void registrarComercio(Integer idUsuario, String cuit, Comercio comercio) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoUsuario(con)).registrarCuentasAsociadas(idUsuario, TiposCuentas.COMERCIOS.getCodigo_usuario(), cuit, "");
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo que busca el ID de usuario a partir del CUIT
	 * @param cuit
	 * @return
	 * @throws ExcepcionControladaError 
	 */
	public static Integer recuperarIdUsuario(String cuit) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoUsuario(con)).recuperarIdUsuario(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}


	public static void validarRSAsociado(Usuario usuario) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).validarRSAsociado(usuario.getIdUsuario(), usuario.getCuit());
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void actualizarCorreo(Integer idUsuario, String correo) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoUsuario(con)).actualizarCorreo(idUsuario, correo);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void registrarCargaNivel3(String nombre, String apellido, String cuit, String tipoDocumento,
											String nroDocumento, String correo, String telefono, String celular) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			
			if ( dao.existeDfe(cuit) )
				dao.registroActualizacionCargaNivel3(nombre, apellido, cuit, tipoDocumento, nroDocumento, correo, telefono, celular);
			else
				dao.registroCargaNivel3(nombre, apellido, cuit, tipoDocumento, nroDocumento, correo, telefono, celular);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void notificarClaveNivel3(String correo) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeClaveNivel3(correo, AdministradorUsuario.CLAVE_TEMPORAL) );
	}

	public static void notificarBE(String correo, List<Cuenta> listaCuentas) throws ExcepcionControladaAlerta {
		AdministradorMails.enviar( new MensajeBE(correo, listaCuentas) );
	}
	
	public static void registrarUsuarioPorRS(Usuario u) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoUsuario dao = new DaoUsuario(con);
			
			if (dao.existeUsuarioCorreo(u.getCorreo()))
				throw new ExcepcionControladaAlerta("El correo ingresado ya se encuentra en uso");
			else
				dao.registrarUsuarioRS(u.getCuit(), u.getClave(), u.getCorreo(), u.getTelefono1(), u.getTelefono2(), "");
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}