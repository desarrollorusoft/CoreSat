package ar.com.cognisys.sat.core.modelo.comun.tramite.pagoMultiple;

import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;

public class PagoMultiple extends ObjetoPersistible {

	private Float importe;
	private Date fecha;
	private Long numeroComprobante;
	private String lugarPago;
	private List<Archivo> listaArchivos;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((listaArchivos == null) ? 0 : listaArchivos.hashCode());
		result = prime * result + ((lugarPago == null) ? 0 : lugarPago.hashCode());
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
		PagoMultiple other = (PagoMultiple) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (listaArchivos == null) {
			if (other.listaArchivos != null)
				return false;
		} else if (!listaArchivos.equals(other.listaArchivos))
			return false;
		if (lugarPago == null) {
			if (other.lugarPago != null)
				return false;
		} else if (!lugarPago.equals(other.lugarPago))
			return false;
		if (numeroComprobante == null) {
			if (other.numeroComprobante != null)
				return false;
		} else if (!numeroComprobante.equals(other.numeroComprobante))
			return false;
		return true;
	}

	public Float getImporte() {
		return importe;
	}
	
	public void setImporte(Float importe) {
		this.importe = importe;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Long getNumeroComprobante() {
		return numeroComprobante;
	}
	
	public void setNumeroComprobante(Long numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}
	
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}
	
	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public String getLugarPago() {
		return lugarPago;
	}

	public void setLugarPago(String lugarPago) {
		this.lugarPago = lugarPago;
	}
}