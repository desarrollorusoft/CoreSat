package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorComercio;
import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.administrador.AdministradorUsuario;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.DebeCompletarRsException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorCuentas;
import ar.com.cognisys.sat.v2.core.controlador.director.DirectorCuentaAsociadaView;
import ar.com.cognisys.sat.v2.core.modelo.exception.CuentaInexistenteException;
import ar.com.cognisys.sat.v2.core.modelo.exception.CuentaInvalidaException;
import ar.com.cognisys.sat.v2.core.modelo.exception.FaltaCompletarRSException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.view.ClaveValorView;
import ar.com.cognisys.sat.v2.core.modelo.view.CuentaDetalleView;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.CuentaAsociadaViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IUsuarioCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta.ICuentaBusquedaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.cuenta.ICuentaDetalleView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaAsociadaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ICodigoCuentaView;

public class FacadeCuentas {

	/**
	 * Método utilizado para verificar si existe el codigo cuenta de una cuenta
	 * y asociarla al Régimen Simplificado
	 * @param codigoCuenta
	 * @throws CuentaInexistenteException 
	 * @throws PersistenceSATException 
	 */
	public void validarCuentaExistente(ICodigoCuentaView codigoCuenta) throws CuentaInexistenteException, PersistenceSATException {
		
		try {
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(TiposCuentas.ABL, codigoCuenta.getCodigoCuenta());
			
			if (cuenta == null)
				throw new CuentaInexistenteException();
		} catch (ExcepcionControladaError e) {
			throw new PersistenceSATException("Ocurrio un problema al validar la cuenta", e);
		} catch (ExcepcionControladaAlerta e) {
			throw new CuentaInexistenteException();
		}
	}
	
	/**
	 * Método utilizado para recuperar el detalle de una cuenta 
	 * a través del tipo y el código de la cuenta.
	 * 
	 * @param cuenta
	 * @return cuentaDetalle
	 * @throws PersistenceSATException 
	 */
	public ICuentaDetalleView recuperarDetalle(ICuentaView cuentaView) throws PersistenceSATException {
		ICuentaDetalleView cuentaDetalle = null;
		
		try {
			TipoCuentaView tipoCuentaView = TipoCuentaView.obtener( cuentaView.getTipo() );
			TiposCuentas tipoCuenta = tipoCuentaView.getBo();
			
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(tipoCuenta, cuentaView.getCodigo());
			
			cuentaDetalle = this.crearCuentaDetalle( cuenta );
			
		} catch(ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible recuperar el detalle", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible recuperar el detalle", e );
		}
		
		return cuentaDetalle;
	}

	/**
	 * Método utlizado para vincular una cuenta al usuario correspondiente
	 * 
	 * @param usuarioCuenta
	 * @throws PersistenceSATException 
	 */
	public void vincular(IUsuarioCuentaView usuarioCuenta) throws PersistenceSATException {
		try {
			Usuario usuario =  AdministradorUsuario.buscar(usuarioCuenta.getNombreUsuario());
			TipoCuentaView tcv = TipoCuentaView.obtener( usuarioCuenta.getCuenta().getTipo() );
			
			TiposCuentas tipoCuenta = tcv.getBo();
			String cuentaDominio = usuarioCuenta.getCuenta().getCodigo();
			String alias = usuarioCuenta.getCuenta().getAlias();
			
			AdministradorCuenta.asociarCuenta(usuario.getIdUsuario(), tipoCuenta, cuentaDominio, alias);
		} catch(ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible vincular la cuenta", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible vincular la cuenta", e );
		}
	}

	/**
	 * Método utlizado para desvincular una cuenta al usuario correspondiente
	 * 
	 * @param usuarioCuenta
	 * @throws PersistenceSATException 
	 */
	public void desvincular(IUsuarioCuentaView usuarioCuenta) throws PersistenceSATException {
		try {
			Usuario usuario =  AdministradorUsuario.buscar(usuarioCuenta.getNombreUsuario());
			TipoCuentaView tcv = TipoCuentaView.obtener( usuarioCuenta.getCuenta().getTipo() );
			
			TiposCuentas tipoCuenta = tcv.getBo();
			String cuentaDominio = usuarioCuenta.getCuenta().getCodigo();
			
			AdministradorCuenta.desasociarCuenta(usuario.getIdUsuario(), tipoCuenta, cuentaDominio);
		} catch(ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible vincular la cuenta", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible vincular la cuenta", e );
		}		
	}

	/**
	 * Método utilizado para recuperar la cuenta asociada.
	 * 
	 * @param cuenta
	 * @return cuentaAsociada
	 * @throws PersistenceSATException 
	 */
	public ICuentaAsociadaView recuperarCuentaAsociada(IUsuarioCuentaView usuarioCuentaView) throws PersistenceSATException {
		
		ICuentaAsociadaView cuentaAsociada = null;
		try {
			TipoCuentaView tcv = TipoCuentaView.obtener( usuarioCuentaView.getCuenta().getTipo() );
			TiposCuentas tipoCuenta = tcv.getBo();

			Cuenta cuenta = new AdministradorCuentas().recuperarCuenta( tipoCuenta, usuarioCuentaView.getNombreUsuario(), usuarioCuentaView.getCuenta().getCodigo() );
			
			AdministradorCuenta.cargarImporte( cuenta );
			
			CuentaAsociadaViewBuilder builder = new CuentaAsociadaViewBuilder( cuenta );
			DirectorCuentaAsociadaView director = new DirectorCuentaAsociadaView( builder );
			
			director.construir();
			
			cuentaAsociada = director.getCuentaAsociada();
			
		} catch(ExcepcionControladaError e) {
			throw new PersistenceSATException( "No es posible completar la busqueda", e );
		}
		
		return cuentaAsociada;
	}
	
	private ICuentaDetalleView crearCuentaDetalle(Cuenta cuenta) {
		CuentaDetalleView cuentaDetalle = new CuentaDetalleView();
		
		cuentaDetalle.setContribuyente( cuenta.obtenerContribuyente() );
		
		cuentaDetalle.setDatosExtra( this.obtenerDatosExtra( cuenta ) );
		
		return cuentaDetalle;
	}

	private IClaveValorView[] obtenerDatosExtra(Cuenta cuenta) {
		List<IClaveValorView> lista = new ArrayList<IClaveValorView>();
		
		switch ( cuenta.getTipoCuenta() ) {
		case ABL:
			lista.add( new ClaveValorView( "Número Documento", String.valueOf( ( ( CuentaABL ) cuenta ).getContribuyente().getNumeroDocumento() ) ) );
			lista.add( new ClaveValorView( "Nombre", String.valueOf( ( ( CuentaABL ) cuenta ).getContribuyente().getNombre() ) ) );
			lista.add( new ClaveValorView( "Apellido", String.valueOf( ( ( CuentaABL ) cuenta ).getContribuyente().getApellido() ) ) );
			lista.add( new ClaveValorView( "Teléfono", ( ( CuentaABL ) cuenta ).getContribuyente().getTelefono() ) );
			lista.add( new ClaveValorView( "Domicilio", String.valueOf( ( ( CuentaABL ) cuenta ).getContribuyente().getDomicilio() ) ) );
			break;
		case VEHICULOS:
			lista.add( new ClaveValorView( "Marca", ( ( CuentaVehiculos ) cuenta ).getMarca() ) );
			lista.add( new ClaveValorView( "Modelo", ( ( CuentaVehiculos ) cuenta ).getModelo() ) );
			break;
		case RODADOS:
			lista.add( new ClaveValorView( "Marca", ( ( CuentaRodados ) cuenta ).getMarca() ) );
			lista.add( new ClaveValorView( "Modelo", ( ( CuentaRodados ) cuenta ).getModelo() ) );
			lista.add( new ClaveValorView( "Cilindrada", ( ( CuentaRodados ) cuenta ).getCilindrada() ) );
			break;
		case CEMENTERIO:
			lista.add( new ClaveValorView( "Número Nomenclador", String.valueOf( ( ( CuentaCementerio ) cuenta ).getNumeroNomenclador() ) ) );
			break;
		case COMERCIOS:
			lista.add( new ClaveValorView( "Razón Social", ( ( CuentaComercios ) cuenta ).getRazonSocial() ) );
//			lista.add( new ClaveValorView( "Fecha Habilitación", this.parsearFecha( ( ( CuentaComercios ) cuenta ).getFechaHabilitacion() ) ) );
			break;
		default:
			break;
		}
		
		
		return lista.toArray( new IClaveValorView[] { } );
	}

	private String parsearFecha(Date date) {
		SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
		return df.format( date );
	}

}
