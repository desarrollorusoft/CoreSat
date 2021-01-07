package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalleLoader;

public class CuentaAutomotorDTO extends CuentaDTO implements ICuentaDetalleLoader<AutomotorDTO> {

	public CuentaAutomotorDTO(String tipo, String codigo, Integer numero, String descripcion, String alias, boolean aceptaBE) {
		super(tipo, codigo, numero, descripcion, alias, aceptaBE);
	}
	
	@Override public void cargarDetalle(AutomotorDTO cuentaDetalle) {
		if ( cuentaDetalle != null )
			this.descripcion = cuentaDetalle.generarDescripcion();
	}

}
