package ar.com.cognisys.sat.core.modelo.comun.recibos.abl;

import ar.com.cognisys.sat.core.modelo.comun.TasaAbl;
import ar.com.cognisys.sat.core.modelo.comun.TasaProteccionCiudadana;

public class InformacionReciboAbl {

	private InformacionPrincipalReciboAbl InformacionPrincipal;
	private TasaAbl tasaAbl;
	private TasaProteccionCiudadana tasaProteccionCiudadana;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((InformacionPrincipal == null) ? 0 : InformacionPrincipal.hashCode());
		result = prime * result + ((tasaAbl == null) ? 0 : tasaAbl.hashCode());
		result = prime * result + ((tasaProteccionCiudadana == null) ? 0 : tasaProteccionCiudadana.hashCode());
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
		InformacionReciboAbl other = (InformacionReciboAbl) obj;
		if (InformacionPrincipal == null) {
			if (other.InformacionPrincipal != null)
				return false;
		} else if (!InformacionPrincipal.equals(other.InformacionPrincipal))
			return false;
		if (tasaAbl == null) {
			if (other.tasaAbl != null)
				return false;
		} else if (!tasaAbl.equals(other.tasaAbl))
			return false;
		if (tasaProteccionCiudadana == null) {
			if (other.tasaProteccionCiudadana != null)
				return false;
		} else if (!tasaProteccionCiudadana.equals(other.tasaProteccionCiudadana))
			return false;
		return true;
	}

	public InformacionPrincipalReciboAbl getInformacionPrincipal() {
		return InformacionPrincipal;
	}

	public void setInformacionPrincipal(
			InformacionPrincipalReciboAbl informacionPrincipal) {
		InformacionPrincipal = informacionPrincipal;
	}

	public TasaAbl getTasaAbl() {
		return tasaAbl;
	}

	public void setTasaAbl(TasaAbl tasaAbl) {
		this.tasaAbl = tasaAbl;
	}

	public TasaProteccionCiudadana getTasaProteccionCiudadana() {
		return tasaProteccionCiudadana;
	}

	public void setTasaProteccionCiudadana(TasaProteccionCiudadana tasaProteccionCiudadana) {
		this.tasaProteccionCiudadana = tasaProteccionCiudadana;
	}
}