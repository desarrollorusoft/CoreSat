package ar.com.cognisys.sat.v2.persistencia.controlador.interfaz;

import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorCargandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalle;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalleLoader;

public interface IAdministradorCuentaDetalle<LOADER extends ICuentaDetalleLoader<?>> {

	ICuentaDetalle recuperarDatos(LOADER cuenta) throws ErrorCargandoDatos, ErrorRecuperandoDatos;
}
