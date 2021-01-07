package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class CuentaVehiculos extends CuentaAutomotor {

	private static final long serialVersionUID = -7409024696696556461L;
	public static String tipoCuenta = "VEHICULOS";
	private String valuacion;
	private String codigoMarca;
	private String tipoVehiculo;
	private String modelo;
	private String marca;
	private String modeloMarca;
	private String catVehiculo;
	
	@Override
	public boolean esCorrecto(String validacion) {
		return true;
	}
	
	@Override
	public String obtenerCodigo() {
		String dominio = super.getDominio();
		if ( dominio != null )
			dominio = dominio.trim();
		
		return dominio;
	}

	@Override
	public String obtenerTipo() {
		return this.getTipoCuenta().getNombre();
	}
	
	@Override
	public String getLeyenda() {
		return this.getMarca() + " " + this.getModeloMarca() + " (" + this.getModelo() + ")";
	}
	
	@Override
	public String getDatoCuenta() {
		return this.getDominio();
	}
	
	@Override
	public boolean correspondePPC() {
		return true;
	}

	@Override
	public boolean correspondeDDJJ() {
		return false;
	}
	
	@Override
	public int compareTo(Cuenta o) {
		return this.getNumero().compareTo(o.getNumero());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((catVehiculo == null) ? 0 : catVehiculo.hashCode());
		result = prime * result + ((codigoMarca == null) ? 0 : codigoMarca.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((modeloMarca == null) ? 0 : modeloMarca.hashCode());
		result = prime * result + ((tipoVehiculo == null) ? 0 : tipoVehiculo.hashCode());
		result = prime * result + ((valuacion == null) ? 0 : valuacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaVehiculos other = (CuentaVehiculos) obj;
		if (catVehiculo == null) {
			if (other.catVehiculo != null)
				return false;
		} else if (!catVehiculo.equals(other.catVehiculo))
			return false;
		if (codigoMarca == null) {
			if (other.codigoMarca != null)
				return false;
		} else if (!codigoMarca.equals(other.codigoMarca))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (modeloMarca == null) {
			if (other.modeloMarca != null)
				return false;
		} else if (!modeloMarca.equals(other.modeloMarca))
			return false;
		if (tipoVehiculo == null) {
			if (other.tipoVehiculo != null)
				return false;
		} else if (!tipoVehiculo.equals(other.tipoVehiculo))
			return false;
		if (valuacion == null) {
			if (other.valuacion != null)
				return false;
		} else if (!valuacion.equals(other.valuacion))
			return false;
		return true;
	}

	@Override
	public TiposCuentas getTipoCuenta() {
		return TiposCuentas.VEHICULOS;
	}
	
	@Override
	public boolean isVehiculo() {
		return true;
	}
	
	@Override
	public Integer getSistema() {
		return 31;
	}

	public String getValuacion() {
		return valuacion;
	}

	public void setValuacion(String valuacion) {
		this.valuacion = valuacion;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModeloMarca() {
		return modeloMarca;
	}

	public void setModeloMarca(String modeloMarca) {
		this.modeloMarca = modeloMarca;
	}

	public String getCatVehiculo() {
		return catVehiculo;
	}

	public void setCatVehiculo(String catVehiculo) {
		this.catVehiculo = catVehiculo;
	}
}