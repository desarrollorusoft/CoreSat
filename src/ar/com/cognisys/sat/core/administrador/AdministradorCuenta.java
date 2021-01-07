package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.dao.cuenta.*;
import ar.com.cognisys.sat.core.modelo.dao.ppc.DaoPPC;
import org.apache.commons.lang.NumberUtils;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudaAdapter;
import ar.com.cognisys.sat.core.modelo.comun.deudas.DeudasCuentaAblAPagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.dao.DAOCuenta;
import ar.com.cognisys.sat.core.modelo.dao.DaoBoletaDePagoABL;
import ar.com.cognisys.sat.core.modelo.dao.DaoCementerio;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentaPileta;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentasContribuyente;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentasUsuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoRecalcularDeuda;
import ar.com.cognisys.sat.core.modelo.dao.DaoRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.dao.DaoUsuario;
import ar.com.cognisys.sat.core.modelo.dao.deuda.DaoDeudaAbl;
import ar.com.cognisys.sat.core.modelo.dao.deuda.DaoDeudaAutomotor;
import ar.com.cognisys.sat.core.modelo.dao.deuda.DaoDeudaComercio;
import ar.com.cognisys.sat.core.modelo.dao.deuda.DaoDeudaMotovehiculo;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentasUsuario;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudasCuentaAbl;
import ar.com.cognisys.sat.core.modelo.validador.CUIT;
import ar.com.cognisys.sat.core.modelo.validador.ValidadorContribuyente;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorCuentas;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import org.apache.commons.lang3.StringUtils;

public class AdministradorCuenta extends Administrador {

	private static final long serialVersionUID = 5026515404282006379L;

	public static void actualizarAlias(Cuenta cuenta, Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			new DAOCuenta(con).actualizarAlias(cuenta, idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}

	}

	public static DeudaAdapter recuperarDeudas(TiposCuentas tipo, String cuenta) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		switch ( tipo ) {
			case ABL:
				return AdministradorCuenta.recuperarDeudaAbl(cuenta);
			case VEHICULOS:
				return AdministradorCuenta.recuperarDeudaVehiculos(cuenta);
			case RODADOS:
				return AdministradorCuenta.recuperarDeudaRodados(cuenta);
			case CEMENTERIO:
				CuentaCementerio c = (CuentaCementerio) AdministradorCuenta.buscarCuenta(tipo, cuenta);
				return AdministradorCuenta.recuperarDeudasCementerioNormal( c.getNumero(), c.getNumeroNomenclador(), new Date() );
			case COMERCIOS:
				return AdministradorCuenta.recuperarDeudaComerico(cuenta);
			default:
				return null;
		}
	}

	public static TotalCuota recuperarTotalCancelacion(Cuenta cuenta, Date fecha) throws ExcepcionControladaError {
		return recuperarTotalCancelacion(cuenta.getTipoCuenta(), cuenta.getDatoCuenta(), fecha);
	}

	public static TotalCuota recuperarTotalCancelacion(TiposCuentas tc, String datoCuenta, Date fecha) throws ExcepcionControladaError {
		switch ( tc ) {
			case ABL:
				return AdministradorCuenta.recuperarTotalCancelacionAbl(datoCuenta, fecha);
			case VEHICULOS:
				return AdministradorCuenta.recuperarTotalCancelacionVehiculos(datoCuenta, fecha);
			case RODADOS:
				return AdministradorCuenta.recuperarTotalCancelacionRodados(datoCuenta, fecha);
			case COMERCIOS:
				return AdministradorCuenta.recuperarTotalCancelacionComerico(datoCuenta, fecha);
			default:
				return null;
		}
	}

	private static TotalCuota recuperarTotalCancelacionAbl(String cuenta, Date fecha) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaAbl(con)).recuperarTotalCancelacionDeuda(cuenta, fecha);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static TotalCuota recuperarTotalCancelacionVehiculos(String cuenta, Date fecha) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaAutomotor(con)).recuperarTotalCancelacionDeuda(cuenta, fecha);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static TotalCuota recuperarTotalCancelacionRodados(String cuenta, Date fecha) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaMotovehiculo(con)).recuperarTotalCancelacionDeuda(cuenta, fecha);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static TotalCuota recuperarTotalCancelacionComerico(String cuenta, Date fecha) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaComercio(con)).recuperarTotalCancelacionDeuda(cuenta, fecha);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static DeudasCuentaAblAPagar recuperarDeudasAPagar(CuentaABL cuenta, Date fecha) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoBoletaDePagoABL(con)).recupearCuotasAblPorFecha(cuenta.getNumero(), fecha);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static Cuenta buscarCuenta(TiposCuentas tipoCuenta, String cuenta) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			if (TiposCuentas.ABL.sos(tipoCuenta))
				return (new DaoCuentaAbl(con)).buscarCuenta(cuenta);

			else if(TiposCuentas.VEHICULOS.sos(tipoCuenta))
				return (new DaoCuentaAutomotor(con)).buscarCuenta(cuenta);

			else if(TiposCuentas.RODADOS.sos(tipoCuenta))
				return (new DaoCuentaMotovehiculo(con)).buscarCuenta(cuenta);

			else if(TiposCuentas.CEMENTERIO.sos(tipoCuenta))
				return (new DaoCuentaCementerio(con)).buscarCuenta(cuenta);

			else if(TiposCuentas.COMERCIOS.sos(tipoCuenta))
				return (new DaoCuentaComercio(con)).buscarCuenta(cuenta);

			else
				return null;
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static DeudasCuentaAblAPagar recuperarDeudaVencidasABL(CuentaABL cuenta, Date fecha) throws ExcepcionControladaError {

		DeudasCuentaAblAPagar deuda = new DeudasCuentaAblAPagar();
		deuda.setCuotasAPagar( recuperarCuotasVencidas( recuperarDeudasAPagar(cuenta, fecha).getCuotasAPagar() ) );

		return deuda;
	}

	public static List<CuotaApagar> recuperarCuotasVencidas(List<CuotaApagar> listaCompleta) {

		List<CuotaApagar> lista = new ArrayList<CuotaApagar>();

		for (CuotaApagar cuota : listaCompleta) {
			if (cuota.isVencida()) {
				lista.add(cuota);
			}
		}

		return lista;
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static List<CuentaABL> recuperarCuentasABL(Integer inicio, Integer cantidad, String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaAbl( con ) ).obtenerLista(inicio, filtro, cantidad);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static List<CuentaVehiculos> recuperarCuentasVehiculos(Integer inicio, Integer cantidad, String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaAutomotor(con) ).obtenerLista(inicio, filtro, cantidad);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static List<CuentaRodados> recuperarCuentasRodados(Integer inicio, Integer cantidad, String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaMotovehiculo(con) ).obtenerLista(inicio, filtro, cantidad);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static List<CuentaComercios> recuperarCuentasComercio(Integer inicio, Integer cantidad, String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaComercio(con) ).obtenerLista(inicio, filtro, cantidad);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static Integer recuperarCantidadCuentasABL(String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaAbl(con) ).recuperarCantidadCuentas(filtro, 2);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static Integer recuperarCantidadCuentasComercio(String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaComercio(con) ).recuperarCantidadCuentas(filtro, 2);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static Integer recuperarCantidadCuentasVehiculos(String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaAutomotor(con) ).recuperarCantidadCuentas(filtro, 4);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilisado por HSAT - Pantalla de Soporte de Cuentas
	 * @throws ExcepcionControladaError
	 */
	public static Integer recuperarCantidadCuentasRodados(String filtro) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaMotovehiculo(con) ).recuperarCantidadCuentas(filtro, 3);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado por Consultar Deuda y Simulacion de PPC
	 *
	 * @param numeroCuenta
	 * @param numeroCuentanoInteger
	 * @param fechaPago
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static DeudaAdapter recuperarDeudasCementerioNormal(Integer numeroCuenta, Integer numeroCuentanoInteger, Date fechaPago) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCementerio(con).recuperarDeudaNormal(numeroCuenta, numeroCuentanoInteger, fechaPago));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado para Registro
	 *
	 * @param numeroCuenta
	 * @return CuentaCementerio
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static CuentaCementerio recuperarCuenta(Integer numeroCuenta) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaCementerio(con).recuperarCuenta(numeroCuenta));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado para recalcular Deudas de Cementerio
	 *
	 * @param listaCuotas
	 * @param cuenta
	 * @param fechaPago
	 * @param pagoContado
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static Deuda recalcularDeuda(List<Cuota> listaCuotas, CuentaCementerio cuenta,
										Date fechaPago, boolean pagoContado) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoRecalcularDeuda(con).recalcularDeuda(cuenta.getContribuyente().getTipoDocumento(),
					cuenta.getContribuyente().getNumeroDocumento(),
					pagoContado,
					cuenta.getDatoCuenta(),
					cuenta.getNumeroNomenclador(),
					fechaPago,
					listaCuotas));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado por Mobile para recuperar todas las cuentas de un tributo.
	 *
	 * @param idUsuario
	 * @param codigoTiposCuenta
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static List<Cuenta> recuperarCuentasPorTipo(Integer idUsuario, Integer codigoTiposCuenta) throws ExcepcionControladaError {

		TiposCuentas t = TiposCuentas.recuperarTipoCuentaPorCodUsu(codigoTiposCuenta);

		if (t == TiposCuentas.ABL) {
			return recuperarCuentasABL(idUsuario);
		} else if (t == TiposCuentas.VEHICULOS) {
			return recuperarCuentasVehiculos(idUsuario);
		} else if (t == TiposCuentas.RODADOS) {
			return recuperarCuentasRodados(idUsuario);
		} else if (t == TiposCuentas.CEMENTERIO) {
			return recuperarCuentasCementerio(idUsuario);
		} else if (t == TiposCuentas.PILETAS) {
			return recuperarCuentasPiletas(idUsuario);
		}  else if (t == TiposCuentas.COMERCIOS) {
			return recuperarCuentasComercio(idUsuario);
		}

		return new ArrayList<Cuenta>();
	}

	public static List<Cuenta> recuperarCuentasPorTipo(Integer idUsuario, Integer codigoTiposCuenta, int page) throws ExcepcionControladaError {

		TiposCuentas t = TiposCuentas.recuperarTipoCuentaPorCodUsu(codigoTiposCuenta);

		if (t == TiposCuentas.ABL) {
			return recuperarCuentasABL(idUsuario, page);
		} else if (t == TiposCuentas.VEHICULOS) {
			return recuperarCuentasVehiculos(idUsuario, page);
		} else if (t == TiposCuentas.RODADOS) {
			return recuperarCuentasRodados(idUsuario, page);
		} else if (t == TiposCuentas.CEMENTERIO) {
			return recuperarCuentasCementerio(idUsuario, page);
		} else if (t == TiposCuentas.PILETAS) {
			return recuperarCuentasPiletas(idUsuario, page);
		}  else if (t == TiposCuentas.COMERCIOS) {
			return recuperarCuentasComercio(idUsuario, page);
		}

		return new ArrayList<Cuenta>();
	}

	private static List<Cuenta> recuperarCuentasABL(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaAbl(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static List<Cuenta> recuperarCuentasABL(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaAbl(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static List<Cuenta> recuperarCuentasVehiculos(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaAutomotor(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static List<Cuenta> recuperarCuentasVehiculos(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaAutomotor(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static List<Cuenta> recuperarCuentasRodados(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaMotovehiculo(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static List<Cuenta> recuperarCuentasRodados(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaMotovehiculo(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<Cuenta> recuperarCuentasCementerio(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaCementerio(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<Cuenta> recuperarCuentasCementerio(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaCementerio(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<Cuenta> recuperarCuentasPiletas(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaPileta(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	public static List<Cuenta> recuperarCuentasPiletas(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaPileta(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}


	public static List<Cuenta> recuperarCuentasComercio(Integer idUsuario) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaComercio(con)).recuperarCuentas(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static List<Cuenta> recuperarCuentasComercio(Integer idUsuario, int page) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoCuentaComercio(con)).recuperarCuentas(idUsuario, page);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo para recuperar las deudas de una determinada cuenta. Se usa para mobile
	 *
	 * @param cuenta
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static DeudaAdapter recuperarDeudaAbl(String cuenta) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaAbl(con)).recuperarCuotas(cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo para recuperar las deudas de una determinada cuenta. Se usa para mobile
	 *
	 * @param dominio
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static DeudaAdapter recuperarDeudaVehiculos(String dominio) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaAutomotor(con)).recuperarCuotas(dominio);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo para recuperar las deudas de una determinada cuenta. Se usa para mobile
	 *
	 * @param dominio
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static DeudaAdapter recuperarDeudaRodados(String dominio) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaMotovehiculo(con)).recuperarCuotas(dominio);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo para recuperar las deudas de una determinada cuenta.
	 *
	 * @param cuenta
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static DeudaAdapter recuperarDeudaComerico(String cuenta) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoDeudaComercio(con)).recuperarCuotas(cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void cargarContribuyente(Cuenta cuenta) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			Contribuyente c = (new DaoCuentasContribuyente(con)).recuperarContribuyente(cuenta.getTipoCuenta(), cuenta.getNumero());
			cuenta.setContribuyente(c);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado por Mobile para recuperar los Numero de cuenta correspondientes al modelo propio de la MVL.
	 *
	 * @param cuentaDominio
	 * @param tipoCuenta
	 * @return numero de cuenta (C_CUENTA)
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static Integer recuperarNumeroCuentaMVL(String cuentaDominio, TiposCuentas tipoCuenta) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Integer numero = null;

		// Validamos que los datos sean los correctos
		validarCuentaIngresada(cuentaDominio, tipoCuenta);

		if (tipoCuenta == TiposCuentas.ABL || tipoCuenta == TiposCuentas.CEMENTERIO || tipoCuenta == TiposCuentas.COMERCIOS) {
			// Se deja esto por si se quiere cambiar, pero el numero que viene es el que se usa.
			numero = new Integer(cuentaDominio);
		} else {
			Connection con = null;

			try {
				con = AdministradorConexiones.obtenerConexion();

				if (tipoCuenta == TiposCuentas.VEHICULOS) {
					numero = (new DaoCuentasUsuario(con)).recuperarCuentaPorDominioVeh(cuentaDominio);

				} else if (tipoCuenta == TiposCuentas.RODADOS) {
					numero = (new DaoCuentasUsuario(con)).recuperarCuentaPorDominioRod(cuentaDominio);
				}
			} finally {
				AdministradorConexiones.cerrarConnection(con);
			}
		}

		return numero;
	}

	public static boolean validarCuentaIngresada(String cuentaDominio, TiposCuentas tipoCuenta) throws ExcepcionControladaAlerta {

		if (tipoCuenta == TiposCuentas.ABL || tipoCuenta == TiposCuentas.CEMENTERIO || tipoCuenta == TiposCuentas.COMERCIOS) {
			if (StringUtils.isBlank(cuentaDominio))
				throw new ExcepcionControladaAlerta( "Debe ingresar una cuenta" );
			else if (NumberUtils.isNumber(cuentaDominio)) {
				return true;
			} else {
				throw new ExcepcionControladaAlerta("La cuenta es numérica", null);
			}
		} else if (tipoCuenta == TiposCuentas.VEHICULOS) {
			if (StringUtils.isBlank(cuentaDominio))
				throw new ExcepcionControladaAlerta( "Debe ingresar un dominio" );
			else if (cuentaDominio.matches("[a-zA-Z]{3}[0-9]{3}") || cuentaDominio.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2}")) {
				return true;
			} else {
				throw new ExcepcionControladaAlerta("El dominio debe tener el patrón (AAA000 o AA000AA)", null);
			}
		} else if (tipoCuenta == TiposCuentas.RODADOS) {
			if (StringUtils.isBlank(cuentaDominio))
				throw new ExcepcionControladaAlerta( "Debe ingresar un dominio" );
			else if (cuentaDominio.matches("[0-9]{3}[a-zA-Z]{3}") || cuentaDominio.matches("[a-zA-Z]{1}[0-9]{3}[a-zA-Z]{3}")) {
				return true;
			} else {
				throw new ExcepcionControladaAlerta("El dominio debe tener el patrón (000AAA o A000AAA)", null);
			}
		} else if ( tipoCuenta == TiposCuentas.COMERCIOS ){
			if (StringUtils.isBlank(cuentaDominio))
				throw new ExcepcionControladaAlerta( "Debe ingresar un CUIT" );
			else if( validarComercio( cuentaDominio ) )
				return true;
			else
				throw new ExcepcionControladaAlerta( "El CUIT ingresado no es correcto" );
		}

		return true;
	}

	public static boolean validarCuentaPiletaIngresada(String tipoDoc, String nroDoc) throws ExcepcionControladaAlerta {

		if( validarPileta( tipoDoc, nroDoc ) )
			return true;
		else
			throw new ExcepcionControladaAlerta( "El documento ingresado no es correcto" );

	}

	private static boolean validarPileta(String tipoDoc, String nroDoc) throws ExcepcionControladaAlerta {

		TiposDocumento tipo = TiposDocumento.getTipoDocumentoPorNombrePiletas(tipoDoc);
		if (tipo != null)
			return ValidadorContribuyente.sonDatosValidos(nroDoc) && ValidadorContribuyente.esDocumentoValido(nroDoc);
		else
			return false;

	}

	private static boolean validarComercio(String cuentaDominio) {

		if( tieneMascara( cuentaDominio ) )
			return CUIT.validar( cuentaDominio );

		else return CUIT.validar( sacarMascara( cuentaDominio ) );

	}

	private static String sacarMascara(String cuentaDominio) {
		return cuentaDominio.replaceAll( "-", "" ).replaceAll( ".", "" );
	}

	private static boolean tieneMascara(String cuentaDominio) {
		return NumberUtils.isNumber( cuentaDominio );
	}

	/**
	 * Metodo usado por Mobile para asociar una cuenta
	 *
	 * @param idUsuario
	 * @param tipoCuenta
	 * @param cuentaDominio
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void asociarCuenta(Integer idUsuario, TiposCuentas tipoCuenta, String cuentaDominio, String alias) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			(new DaoUsuario(con)).registrarCuentasAsociadas(idUsuario, tipoCuenta.getCodigo_usuario(), cuentaDominio, alias);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void asociarCuentaPileta(Integer idUsuario, String tipoDoc, String nroDoc, String alias) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			(new DaoUsuario(con)).registrarCuentaAsociadaPileta(idUsuario, tipoDoc, nroDoc, alias);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado por Mobile para desasociar una cuenta
	 *
	 * @param idUsuario
	 * @param tipoCuenta
	 * @param cuentaDominio
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void desasociarCuenta(Integer idUsuario, TiposCuentas tipoCuenta, String cuentaDominio) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoUsuario(con)).eliminarCuentaRelacionada(idUsuario, tipoCuenta.getCodigo_usuario(), cuentaDominio);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void desasociarCuentaPileta(Integer idUsuario, String tipoDoc, String nroDoc) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			(new DaoUsuario(con)).eliminarCuentaRelacionadaPileta(idUsuario, tipoDoc, nroDoc);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo usado por Mobile para desasociar varias cuentas
	 *
	 * @param idUsuario
	 * @param tabla
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static void desasociarMultiplesCuentas(Integer idUsuario, HashMap<String, TiposCuentas> tabla) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		for (String cuenta : tabla.keySet()) {
			desasociarCuenta(idUsuario, tabla.get(cuenta), cuenta);
		}
	}

	/**
	 * Metodo utilizado por Mobile, para recuperar todas las cuentas del usuario
	 *
	 * @param idUsuario
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static CuentasUsuario recuperarCuentas(Integer idUsuario) throws ExcepcionControladaError {

		try {
			List<Cuenta> lista = (new AdministradorCuentas()).recuperarCuentasPorUsuario(idUsuario);

			CuentasUsuario cu = FactoryCuentasUsuario.generar(lista);

			return cu;
		} catch (PersistenceSATException e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		}
	}

	/**
	 * Metodo para recalculo de Deuda para Estado de Cuenta
	 * @param deudaLegales
	 * @param pagoContado
	 * @param cuenta
	 * @param fechaPago
	 * @param listaCuotas
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static Deuda recalcularDeudaComun(boolean deudaLegales, boolean pagoContado, Cuenta cuenta, Date fechaPago,
											 List<Cuota> listaCuotas) throws ExcepcionControladaError {
		Deuda deuda = null;
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion(con);

			deuda = (new DaoRecalcularDeuda(con)).recalcularDeuda(cuenta,
					(deudaLegales ? false : pagoContado),
					fechaPago,
					listaCuotas);
			AdministradorConexiones.completarTransaccion(con);
		} catch (Exception e) {
			AdministradorConexiones.cancelarTransaccion(con);
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}

		return deuda;
	}

	public static Deuda recalcularDeudaComercio(Cuenta cuenta, Date fechaPago, List<Cuota> listaCuotas) throws ExcepcionControladaError {
		Deuda deuda = null;
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			AdministradorConexiones.abrirTransaccion(con);

			deuda = (new DaoRecalcularDeuda(con)).recalcularDeudaComercio(cuenta, fechaPago, listaCuotas);

			AdministradorConexiones.completarTransaccion(con);
		} catch (Exception e) {
			AdministradorConexiones.cancelarTransaccion(con);
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}

		return deuda;
	}

	/**
	 * Metodo utilizado para PPC que recalcula deuda y devuelve Comprobante
	 *
	 * @param numeroPlan
	 * @param cuota
	 *
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static String generarComprobantePPC(Integer numeroPlan, Integer cuota) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoPPC(con)).generarComprobante(numeroPlan, cuota);
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo utilizado para PPC que recalcula deuda y devuelve Comprobante
	 *
	 * @param numeroPlan
	 *
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static String generarComprobanteCancelacionPPC(Integer numeroPlan) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoPPC(con)).generarComprobanteCancelacion( numeroPlan );
		} catch (Exception e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	/**
	 * Metodo para recalcular Deuda en Mobile
	 *
	 * @param cuentaDominio
	 * @param cuentaNomCementerio
	 * @param tipoCuenta
	 * @param fecha
	 * @param listaCuotas
	 * @param pagoContado
	 * @param deudaLegales
	 * @return
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static Deuda recalcularDeuda(String cuentaDominio, Integer cuentaNomCementerio, TiposCuentas tipoCuenta, Date fecha, List<Cuota> listaCuotas, boolean pagoContado, boolean deudaLegales) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Integer numeroCuenta = recuperarNumeroCuentaMVL( cuentaDominio, tipoCuenta );
		Contribuyente contribuyente = recuperarContribuyente( numeroCuenta );

		return recalcularDeuda( contribuyente, deudaLegales, pagoContado, cuentaDominio, cuentaNomCementerio, fecha, listaCuotas, tipoCuenta );
	}

	private static Deuda recalcularDeuda(Contribuyente contribuyente, boolean deudaLegales, boolean pagoContado, String datoCuenta, Integer cuentaNomCementerio, Date fecha, List<Cuota> listaCuotas, TiposCuentas tipoCuenta) throws ExcepcionControladaError {

		Connection con = null;
		Deuda deuda = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			AdministradorConexiones.abrirTransaccion( con );

			DaoRecalcularDeuda daoDeuda = new DaoRecalcularDeuda( con );

			// Si tiene deuda en legales, no se permite pago al contado
			if (tipoCuenta == TiposCuentas.CEMENTERIO)
				deuda = daoDeuda.recalcularDeuda(contribuyente.getTipoDocumento(),
						contribuyente.getNumeroDocumento(),
						(deudaLegales ? false : pagoContado),
						datoCuenta,
						cuentaNomCementerio,
						fecha,
						listaCuotas);
			else
				deuda = daoDeuda.recalcularDeuda(contribuyente.getTipoDocumento(),
						contribuyente.getNumeroDocumento(),
						(deudaLegales ? false : pagoContado),
						datoCuenta,
						fecha,
						listaCuotas,
						tipoCuenta);

			AdministradorConexiones.completarTransaccion( con );
		} catch (Exception e) {
			AdministradorConexiones.cancelarTransaccion( con );
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}

		return deuda;

	}

	private static Contribuyente recuperarContribuyente(Integer numeroCuenta) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return new DaoCuentasUsuario( con ).recuperarContribuyente( numeroCuenta );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	/**
	 * Metodo usado por la reimpresion de boletas
	 *
	 * @param cuentaDominio
	 * @param tipoCuenta
	 * @param fecha
	 * @param listaCuotas
	 * @param pagoContado
	 * @param deudaLegales
	 * @return
	 * @throws ExcepcionControladaError
	 * @throws ExcepcionControladaAlerta
	 */
	public static Deuda recalcularDeuda(String cuentaDominio, TiposCuentas tipoCuenta, Date fecha,
										List<Cuota> listaCuotas, boolean pagoContado, boolean deudaLegales) throws ExcepcionControladaError, ExcepcionControladaAlerta {

		Deuda deuda = recalcularDeuda(cuentaDominio, null, tipoCuenta, fecha, listaCuotas, pagoContado, deudaLegales);

		String idCarrito = deuda.getIdCarrito();
		String numeroComprobante = deuda.getNumeroComprobante();

		deuda = FactoryDeudasCuentaAbl.generarIntanciaCompleta(deuda.getListaCuotasAVencer(),
				deuda.getListaCuotasVencidas(),
				deuda.getTotalCoutasAVencer(),
				deuda.getTotalCoutasVencidas(),
				deuda.getTotalCoutasGeneral(),
				deuda.isTieneDeudaLegales());
		deuda.setIdCarrito(idCarrito);
		deuda.setNumeroComprobante(numeroComprobante);

		return deuda;
	}

	@Deprecated
	public static List<CuentaCementerio> recuperarCuentasCementerio(Integer inicio, Integer cantidad, String filtro) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaCementerio(con) ).obtenerLista(inicio, filtro, cantidad);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	@Deprecated
	public static Integer recuperarCantidadCuentasCementerio(String filtro) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoCuentaCementerio(con) ).recuperarCantidadCuentas(filtro, 1);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void cargarImportes(List<Cuenta> listaCuentas) throws ExcepcionControladaError {
		for (Cuenta cuenta : listaCuentas)
			cargarImporte( cuenta );
	}

	public static void cargarImporte(Cuenta cuenta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			switch ( cuenta.getTipoCuenta() ) {
				case ABL:
					new DaoCuentaAbl( con ).cargarTotalesDeuda( (CuentaABL) cuenta );
					break;
				case RODADOS:
					new DaoCuentaMotovehiculo( con ).cargarTotalesDeuda( ( CuentaRodados ) cuenta );
					break;
				case VEHICULOS:
					new DaoCuentaAutomotor( con ).cargarTotalesDeuda( ( CuentaVehiculos ) cuenta );
					break;
				case COMERCIOS:
					new DaoCuentaComercio( con ).cargarTotalesDeuda( ( CuentaComercios ) cuenta );
					break;
				case CEMENTERIO:
					new DaoCuentaCementerio( con ).cargarTotalesDeuda( ( CuentaCementerio ) cuenta );
					break;
				default:
					break;
			}
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static void asociarCuentasABLdeRS(String cuit) throws ExcepcionControladaError {

		List<String> cuentas = buscarCuentasABLdeRS(cuit);
		Integer idUsuario = AdministradorUsuario.recuperarIdUsuario(cuit);
		asociarCuentas(idUsuario, TiposCuentas.ABL, cuentas);
	}

	private static List<String> buscarCuentasABLdeRS(String cuit) throws ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoRegimenSimplificado(con)).recueprarCuentasABL(cuit);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	private static void asociarCuentas(Integer idUsuario, TiposCuentas tc, List<String> cuentas) throws ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			DaoUsuario dao = new DaoUsuario(con);
			for (String cuenta : cuentas)
				if ( !dao.existeCuentaAsociada(idUsuario, cuenta) )
					dao.registrarCuentasAsociadas(idUsuario, tc.getCodigo_usuario(), cuenta, "");
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}

	public static boolean tieneCuenta(Integer idUsuario, TiposCuentas tc, String cuenta, TiposDocumento td) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			if ( tc.sos( TiposCuentas.PILETAS ) )
				return (new DaoUsuario(con)).tieneCuenta(idUsuario, td.getNombrePiletas(), cuenta);
			else
				return (new DaoUsuario(con)).tieneCuenta(idUsuario, tc, cuenta);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}


	public static List<Cuenta> recuperarPadronesComercio(Integer idUsuario) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoComercio(con)).recuperarPadrones(idUsuario);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}
