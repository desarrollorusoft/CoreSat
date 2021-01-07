package ar.com.cognisys.sat.core.modelo.comun.seguimiento;

import java.io.Serializable;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.NavegacionAplicacion;

public class Acceso implements Serializable {

	private static final long serialVersionUID = -2986565779606811061L;
	private NavegacionAplicacion acceso;
	private Date fecha;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceso == null) ? 0 : acceso.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
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
		Acceso other = (Acceso) obj;
		if (acceso != other.acceso)
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		return true;
	}

	public NavegacionAplicacion getAcceso() {
		return acceso;
	}
	
	public void setAcceso(NavegacionAplicacion acceso) {
		this.acceso = acceso;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}