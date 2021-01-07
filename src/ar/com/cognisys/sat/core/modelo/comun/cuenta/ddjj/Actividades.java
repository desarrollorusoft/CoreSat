package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividades;

public class Actividades {

	private ActividadComercial actividadPrincipal;
	private List<ActividadComercial> otrasActividades;
	
	public Actividades generarClon() {
		Actividades clon = null;
		
		if (actividadPrincipal == null)
			clon = FactoryActividades.generar();
		else
			clon = FactoryActividades.generar( actividadPrincipal.generarClon() );
		
		for (ActividadComercial ac : otrasActividades)
			clon.agregar( ac.generarClon() );
		
		return clon;
	}
	
	public void agregar(ActividadComercial ac) {
		this.getOtrasActividades().add( ac );
	}
	
	public boolean hayOtrasActividades() {
		return this.getOtrasActividades() != null && !this.getOtrasActividades().isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actividadPrincipal == null) ? 0 : actividadPrincipal.hashCode());
		result = prime * result + ((otrasActividades == null) ? 0 : otrasActividades.hashCode());
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
		Actividades other = (Actividades) obj;
		if (actividadPrincipal == null) {
			if (other.actividadPrincipal != null)
				return false;
		} else if (!actividadPrincipal.equals(other.actividadPrincipal))
			return false;
		if (otrasActividades == null) {
			if (other.otrasActividades != null)
				return false;
		} else if (!otrasActividades.equals(other.otrasActividades))
			return false;
		return true;
	}

	public ActividadComercial getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(ActividadComercial actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public List<ActividadComercial> getOtrasActividades() {
		return otrasActividades;
	}

	public void setOtrasActividades(List<ActividadComercial> otrasActividades) {
		this.otrasActividades = otrasActividades;
	}
}