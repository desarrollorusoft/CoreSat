package ar.com.cognisys.sat.core.modelo.generador;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class GeneradorBoletaPago {

	private String numeroComprobante; 
	private Date fechaVencimiento;
	private Float monto;
	
	public GeneradorBoletaPago(String numeroComprobante, Date fechaVencimiento, Float monto) {
		this.setNumeroComprobante(numeroComprobante);
		this.setFechaVencimiento(fechaVencimiento);
		this.setMonto(monto);
	}

	public static String formatearDinero(float resultado) {
		
		return "$ " + String.format("%.2f", resultado);
	}
	
	public static String formatearFecha(Date fecha) {
		
		return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
	}
	
	public static String formatearFechaBarras(Date fecha) {
		
		return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	}
	
	public static String formatearFechaHora(Date fecha) {
		
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fecha);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result + ((numeroComprobante == null) ? 0 : numeroComprobante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneradorBoletaPago other = (GeneradorBoletaPago) obj;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		if (monto == null) {
			if (other.monto != null)
				return false;
		} else if (!monto.equals(other.monto))
			return false;
		if (numeroComprobante == null) {
			if (other.numeroComprobante != null)
				return false;
		} else if (!numeroComprobante.equals(other.numeroComprobante))
			return false;
		return true;
	}

	public String getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}
}