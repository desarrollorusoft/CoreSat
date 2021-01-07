package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalleLoader;

public class CuentaCementerioDTO extends CuentaDTO implements ICuentaDetalleLoader<CementerioDTO> {

	private Integer numeroNomenclador;

	public CuentaCementerioDTO(String tipo, String codigo, Integer numero, String descripcion, String alias, boolean aceptaBE) {
		super( tipo, codigo, numero, descripcion, alias, aceptaBE );
	}
	
	@Override public void cargarDetalle(CementerioDTO cuentaDetalle) {
		this.descripcion = cuentaDetalle.generarDescripcion();
		this.numeroNomenclador = cuentaDetalle.getNumeroNomenclador();
	}

	public Integer getNumeroNomenclador() {
		return numeroNomenclador;
	}

	public void setNumeroNomenclador(Integer numeroNomenclador) {
		this.numeroNomenclador = numeroNomenclador;
	}

}
