package ar.com.cognisys.sat.v2.persistencia.modelo.interfaz;

public interface ICuentaDetalleLoader<DETALLE extends ICuentaDetalle> {

	void cargarDetalle(DETALLE cuentaDetalle);
	
}
