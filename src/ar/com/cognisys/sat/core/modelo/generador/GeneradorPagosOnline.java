package ar.com.cognisys.sat.core.modelo.generador;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.PaquetePago;

public abstract class GeneradorPagosOnline extends GeneradorBoletaPago {

	private String cuenta;
	private String tasa;
	private String retorno;
	private String titular;
	
	public GeneradorPagosOnline(String cuenta, String numeroComprobante, Date fechaVencimiento, Float monto,
								String tasa, String retorno) {
		
		super(numeroComprobante, fechaVencimiento, monto);
		this.setCuenta(cuenta);
		this.setTasa(tasa);
		this.setRetorno(retorno);
	}

	public abstract MediosPago getMedioPago();
	
	public abstract PaquetePago emitirPago(Sistema sistema) throws ExcepcionControladaError;
	
	protected static String ajustarMonto(Float monto) {
		return (new String(monto.toString().replace(".", ",")));
	}


	protected String escapeTilde(String data) {
		return data.replace("'", " ");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime * result + ((retorno == null) ? 0 : retorno.hashCode());
		result = prime * result + ((tasa == null) ? 0 : tasa.hashCode());
		result = prime * result + ((titular == null) ? 0 : titular.hashCode());
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
		GeneradorPagosOnline other = (GeneradorPagosOnline) obj;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (retorno == null) {
			if (other.retorno != null)
				return false;
		} else if (!retorno.equals(other.retorno))
			return false;
		if (tasa == null) {
			if (other.tasa != null)
				return false;
		} else if (!tasa.equals(other.tasa))
			return false;
		if (titular == null) {
			if (other.titular != null)
				return false;
		} else if (!titular.equals(other.titular))
			return false;
		return true;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
}