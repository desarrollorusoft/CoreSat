package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalle;

public class AutomotorDTO implements ICuentaDetalle {

	private String catVehiculo;
	private String codigoMarca;
	private String marca;
	private String modelo;
	private String modeloMarca;
	private String tipoVehiculo;
	private String valuacion;

	public AutomotorDTO(String catVehiculo, String codigoMarca, String marca, String modelo, String modeloMarca, String tipoVehiculo, String valuacion) {
		this.catVehiculo = catVehiculo;
		this.codigoMarca = codigoMarca;
		this.marca = marca;
		this.modelo = modelo;
		this.modeloMarca = modeloMarca;
		this.tipoVehiculo = tipoVehiculo;
		this.valuacion = valuacion;
	}
	
	@Override
	public String generarDescripcion() {
		return this.marca + " " + this.modelo;
	}

	public String getCatVehiculo() {
		return catVehiculo;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public String getModeloMarca() {
		return modeloMarca;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public String getValuacion() {
		return valuacion;
	}

}
