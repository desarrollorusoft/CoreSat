package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class CuentaRodados extends CuentaAutomotor {

	private static final long serialVersionUID = -7409024696696556461L;
	public static String tipoCuenta = "RODADOS";
	private String marca;
	private String cilindrada;
	private String modelo;

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
		return this.getMarca() + " " + this.getCilindrada() + " (" + this.getModelo() + ")";
	}
	
	@Override
	public TiposCuentas getTipoCuenta() {
		return TiposCuentas.RODADOS;
	}
	
	@Override
	public boolean isVehiculo() {
		return false;
	}
	
	@Override
	public Integer getSistema() {
		return 4;
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
		result = prime * result + ((cilindrada == null) ? 0 : cilindrada.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
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
		CuentaRodados other = (CuentaRodados) obj;
		if (cilindrada == null) {
			if (other.cilindrada != null)
				return false;
		} else if (!cilindrada.equals(other.cilindrada))
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
		return true;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}