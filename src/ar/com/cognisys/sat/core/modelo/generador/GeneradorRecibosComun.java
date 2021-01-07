package ar.com.cognisys.sat.core.modelo.generador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public abstract class GeneradorRecibosComun extends GeneradorRecibos {
	
	private Cuenta cuenta;
	private List<Cuota> listaCuotas;
	
	public GeneradorRecibosComun(Cuenta cuenta, String numeroComprobante, Date fechaVencimiento, Float monto,
							List<Cuota> listaCuotas) {
		
		super(numeroComprobante, fechaVencimiento, monto);
		this.setCuenta(cuenta);
		this.setListaCuotas(listaCuotas);
	}
	
	public abstract void generarRecibo() throws ExcepcionControladaError;
	
	@Override
	protected String generarCodigoBarras() {
		return null;
	}
	
	@Override
	protected String generarCodigoBarras(String tasa) {
		
		String cuota = "00";
		String anio = "0000";
		
		if (this.getListaCuotas().size() == 1) {
			
			String periodo = this.getListaCuotas().get(0).getPeriodo();
	
			cuota = periodo.split("-")[1];
			anio = periodo.split("-")[0];
		}
		
		return CodigoDeBarras.generarCodigo48CaracteresCompleto("110", 
																(this.getNumeroComprobante()).substring(this.getNumeroComprobante().toString().length() -8), 
																(new SimpleDateFormat("yyyyddMM")).format(this.getFechaVencimiento()),
																this.getMonto().toString(),
																"00", 
																"00000000",
																tasa, 
																anio.trim(), 
																cuota.trim());
	}
	
	public abstract boolean habilitarSaltoPaginaPDF();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime * result + ((listaCuotas == null) ? 0 : listaCuotas.hashCode());
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
		GeneradorRecibosComun other = (GeneradorRecibosComun) obj;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (listaCuotas == null) {
			if (other.listaCuotas != null)
				return false;
		} else if (!listaCuotas.equals(other.listaCuotas))
			return false;
		return true;
	}

	public List<Cuota> getListaCuotas() {
		return listaCuotas;
	}

	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
}