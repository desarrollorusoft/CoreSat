package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.Map;

import ar.com.cognisys.sat.core.administrador.AdministradorContribuyente;
import ar.com.cognisys.sat.core.administrador.AdministradorUsuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.exception.CuentaInvalidaException;
import ar.com.cognisys.sat.v2.core.modelo.exception.DatosIncorrectosException;
import ar.com.cognisys.sat.v2.core.modelo.exception.LoginIncorrectoException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.UsuarioExistenteException;
import ar.com.cognisys.sat.v2.core.modelo.exception.UsuarioInexistenteException;
import ar.com.cognisys.sat.v2.core.modelo.exception.WarningSATException;
import ar.com.cognisys.sat.v2.core.modelo.transformers.TransformadorCuentas;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuitView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario.IActivacionView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario.IActualizacionLoginView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario.IPerfilView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario.IRegistroView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.usuario.IRestauracionView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.ILoginView;

public class FacadeUsuarios {

	/**
	 * M�todo utilizado para registrar un nuevo usuario.
	 * Arroja excepci�n cuando ya existe el usuario en el sistema.
	 * 
	 * @param registro
	 * @throws UsuarioExistenteException
	 * @throws PersistenceSATException 
	 * @throws WarningSATException 
	 */
	public void registrar(IRegistroView registro) throws UsuarioExistenteException, PersistenceSATException, WarningSATException {
		
		try {
			AdministradorUsuario.registrar(registro.getCuit(), 
										   registro.getCorreo(), 
										   registro.getClave());
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible registrar el usuario", e );
		} catch (ExcepcionControladaAlerta e) {
			throw new WarningSATException( e.getMessage(), e );
		}
	}
	
	/**
	 * M�todo utilizado para restablecer la clave.
	 * Arroja excepci�n cuando el usuario no existe en el sistema.
	 * 
	 * @param restauracion
	 * @throws UsuarioInexistenteException
	 * @throws PersistenceSATException 
	 * @throws WarningSATException 
	 */
	public void restablecerClave(IRestauracionView restauracion) throws UsuarioInexistenteException, PersistenceSATException, WarningSATException {
		
		try {
			TipoCuentaView tcv = TipoCuentaView.obtener(restauracion.getTipoCuenta());
			TiposCuentas tc = tcv.getBo();
			
			AdministradorContribuyente.generarNuevaClave( restauracion.getCodigoCuenta(), tc, restauracion.getCuit() );
		} catch ( ar.com.cognisys.sat.core.modelo.excepcion.UsuarioInexistenteException e ) {
			throw new UsuarioInexistenteException();
		} catch ( CuentaInvalidaException e ) {
			throw new WarningSATException( "La cuenta indicada es incorrecta" );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible restablecer la clave", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new WarningSATException( e.getMessage(), e );
		}
	}

	/**
	 * M�todo utilizado para buscar los usuarios que ingresaban por correo
	 * y as� poder actualizar su cuit y sus datos.
	 * 
	 * @param login
	 * @return cantidadCuentas
	 * @throws UsuarioInexistenteException
	 * @throws PersistenceSATException
	 */
	public Integer recuperarCantidadCuentas(ILoginView login) throws UsuarioInexistenteException, PersistenceSATException {
		// TODO Se puede mejorar el resultado cambiando lo que devuelve el servicio. Ya se tinen la total por tipo cuenta
		
		try {
			Map<TiposCuentas, Integer> mapa = AdministradorUsuario.buscarCatidadCuentasUsuario(login.getNombreUsuario(), login.getClave());
		
			int cantidad = 0; 
			
			for (Integer i : mapa.values())
				cantidad += i;
		
			return cantidad;
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException(e.getMessage(), e);
		} catch (ExcepcionControladaAlerta e) {
			throw new UsuarioInexistenteException();
		}
	}
	
	/**
	 * M�todo utilizado para actualizar la clave y el cuit 
	 * del Usuario que ingresaba por correo.
	 * 
	 * @param actualizacionLogin
	 * @throws PersistenceSATException
	 */
	public void actualizarDatos(IActualizacionLoginView actualizacionLogin) throws PersistenceSATException {
		
//		try {
//			AdministradorUsuario.actualizarDatosMigracion(actualizacionLogin.getNombreUsuarioAnterior(), 
//														  actualizacionLogin.getNombreUsuario(), 
//														  actualizacionLogin.getClave());
//		} catch (ExcepcionControladaError e) {
//			throw new PersistenceSATException(e.getMessage(), e);
//		}
	}

	/**
	 * M�todo utilizado para actualizar la clave.
	 * 
	 * @param login
	 * @throws PersistenceSATException
	 * @throws DatosIncorrectosException
	 */
	public void actualizarClave(ILoginView login) throws PersistenceSATException, DatosIncorrectosException {
		
		try {
			AdministradorContribuyente.cambiarClave(login.getNombreUsuario(), login.getClave());
		} catch (ExcepcionControladaAlerta e) {
			throw new DatosIncorrectosException(e.getMessage(), e);
			} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException(e.getMessage(), e);
		}
	}

	/**
	 * M�todo utilizado para actualizar el perfil
	 * 
	 * @param perfil
	 * @throws PersistenceSATException
	 * @throws DatosIncorrectosException
	 */
	public void actualizarPerfil(IPerfilView perfil) throws PersistenceSATException, DatosIncorrectosException {
//		try {
//			AdministradorContribuyente.actualizarDatosUsuario(perfil.getCuit(), perfil.getCorreo(), perfil.getTelefono(), perfil.getTelefono2(), "", "");
//		} catch (ExcepcionControladaAlerta e) {
//			throw new DatosIncorrectosException(e.getMessage(), e);
//		} catch (ExcepcionControladaError e) {
//			throw new PersistenceSATException(e.getMessage(), e);
//		}
	}

	/**
	 * Metodo que te permite validar si la contrase�a es correcta
	 * 
	 * @param login
	 * @throws LoginIncorrectoException
	 * @throws PersistenceSATException
	 */
	public void validarClave(ILoginView login) throws LoginIncorrectoException, PersistenceSATException {
		
		try {
			AdministradorUsuario.existeUsuario(login.getNombreUsuario(), login.getClave());
		} catch (ExcepcionControladaAlerta e) {
			throw new LoginIncorrectoException();
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException(e.getMessage(), e);
		}
	}
	
	/**
	 * Metodo para confirmar la activacion
	 * @param confirmacion
	 * @throws WarningSATException
	 * @throws PersistenceSATException
	 */
	public void validarCodigo(IActivacionView confirmacion) throws WarningSATException, PersistenceSATException {
		try {
			AdministradorContribuyente.activacionMobile(confirmacion.getCuit(), confirmacion.getCodigo());
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException(e.getMessage(), e);
		} catch (ExcepcionControladaAlerta e) {
			throw new WarningSATException(e.getMessage(), e);
		}
	}

	public void reenviarCodigoActivacion(ICuitView cuitView) throws WarningSATException, PersistenceSATException {
		
		try {
			AdministradorContribuyente.solicitarCodigoActivacion(cuitView.getCuit());
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException(e.getMessage(), e);
		} catch (ExcepcionControladaAlerta e) {
			throw new WarningSATException(e.getMessage(), e);
		}
	}
}