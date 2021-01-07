package ar.com.cognisys.sat.v2.persistencia.controlador.facade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.generics.assistant.list.ListAssistant;
import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.AdministradorCuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.factory.AdministradorDescripcionGeneratorFactory;
import ar.com.cognisys.sat.v2.persistencia.controlador.interfaz.IAdministradorCuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.TipoCuentaDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorCargandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalleLoader;

public class FacadeCuentasDTO {



	public List<CuentaDTO> recuperarCuentasPorUsuario(Integer idUsuario) throws PersistenceSATException, ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			Map<TipoCuentaDTO, List<CuentaDTO>> mapa = null;

			AdministradorCuentaDTO adminCuenta = new AdministradorCuentaDTO( con );
			mapa = adminCuenta.recuperarCuentasPorUsuario( idUsuario );

			for ( TipoCuentaDTO tipoCuentaDTO : mapa.keySet() )
				if ( tipoCuentaDTO.isNecesitaDatos() )
					this.cargarDatos( tipoCuentaDTO, mapa.get( tipoCuentaDTO ), con);

			return this.unificarListas( mapa );

		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public CuentaDTO recuperarCuenta(String tipoCuenta, String nombreUsuario, String codigoCuenta) throws PersistenceSATException, ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			TipoCuentaDTO tipoCuentaDTO = TipoCuentaDTO.obtenerPorNameBO( tipoCuenta );

			AdministradorCuentaDTO adminCuenta = new AdministradorCuentaDTO( con );
			CuentaDTO cuenta = adminCuenta.recuperarCuenta( tipoCuentaDTO, nombreUsuario, codigoCuenta );

			if ( tipoCuentaDTO.isNecesitaDatos() )
				this.cargarDatos( tipoCuentaDTO, this.generarLista( cuenta ), con );

			return cuenta;

		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	private List<CuentaDTO> generarLista(CuentaDTO cuenta) {
		List<CuentaDTO> lista = new ArrayList<CuentaDTO>();
		lista.add( cuenta );
		return lista;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private void cargarDatos(TipoCuentaDTO tipoCuenta, List<CuentaDTO> lista, Connection con) throws ErrorCargandoDatos, ErrorRecuperandoDatos {

		AdministradorDescripcionGeneratorFactory factory = new AdministradorDescripcionGeneratorFactory( tipoCuenta );
		IAdministradorCuentaDetalle<?> adminDetalle = factory.generar( con );

		if ( adminDetalle == null )
			return;

		List<ICuentaDetalleLoader> listaDetalle = ListAssistant.filter( ICuentaDetalleLoader.class, lista );
		for ( ICuentaDetalleLoader cuentaDetalleLoader : listaDetalle )
			cuentaDetalleLoader.cargarDetalle( this.obtenerDetalle( adminDetalle, cuentaDetalleLoader ) );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private ICuentaDetalle obtenerDetalle(IAdministradorCuentaDetalle adminDetalle, ICuentaDetalleLoader cuentaDetalleLoader) throws ErrorCargandoDatos, ErrorRecuperandoDatos {
		return adminDetalle == null ? null : adminDetalle.recuperarDatos( cuentaDetalleLoader );
	}

	private List<CuentaDTO> unificarListas(Map<TipoCuentaDTO, List<CuentaDTO>> mapa) {
		List<CuentaDTO> listaCompleta = new ArrayList<CuentaDTO>();
		for ( List<CuentaDTO> listaCuentasDTO : mapa.values() )
			listaCompleta.addAll( listaCuentasDTO );

		return listaCompleta;
	}
}