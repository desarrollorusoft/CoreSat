package ar.com.cognisys.sat.core.modelo.comun.rs.versiones;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJSV;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividades;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJSV;

public class VersionPadronRS2018 extends VersionPadronRS {

	private Actividades actividades;
	private List<DDJJCarteleria> listaCarteleria;
	private List<DDJJOEP> listaOEP;
	private DDJJSV serviciosVarios;
	
	public VersionPadronRS2018() {
		this.setListaCarteleria(new ArrayList<DDJJCarteleria>());
		this.setListaOEP(new ArrayList<DDJJOEP>());
	}
	
	@Override
	protected void cargarDatos(VersionPadronRS version) {
		
		VersionPadronRS2018 clon = (VersionPadronRS2018) version;
		
		if (this.getActividades() == null)
			clon.setActividades( FactoryActividades.generar() );
		else
			clon.setActividades( this.getActividades().generarClon() );
		
		if (this.getServiciosVarios() == null)
			clon.setServiciosVarios( FactoryDDJJSV.generar() );
		else
			clon.setServiciosVarios( this.getServiciosVarios().generarClon() );
		
		List<DDJJCarteleria> listaCarteles = new ArrayList<DDJJCarteleria>();
		for (DDJJCarteleria carteleria : this.getListaCarteleria())
			listaCarteles.add( carteleria.generarClon() );			
		
		clon.setListaCarteleria( listaCarteles );
		
		List<DDJJOEP> listaOEP = new ArrayList<DDJJOEP>();
		for (DDJJOEP oep : this.getListaOEP())
			listaOEP.add( oep.generarClon() );	
		
		clon.setListaOEP( listaOEP );
	}
	
	public float getCarteleria(int categoria) {
		float suma = 0f;
		
		for (DDJJCarteleria carteleria : this.getListaCarteleria())
			if (carteleria.sos(categoria))
				suma += carteleria.getMetros();
		
		return suma;
	}
	
	public float getOEPMetrosToldos() {
		float suma = 0f;
		
		for (DDJJOEP oep : this.getListaOEP())
			if (oep.sos(TipoOEP.TOLDO))
				suma += oep.getValor();
		
		return suma;
	}

	public float getOEPUnidadesPostes() {
		float suma = 0f;
		
		for (DDJJOEP oep : this.getListaOEP())
			if (oep.sos(TipoOEP.POSTE))
				suma += oep.getValor();
		
		return suma;
	}
	
	@Override
	public int getAno() {
		return 2018;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actividades == null) ? 0 : actividades.hashCode());
		result = prime * result + ((listaCarteleria == null) ? 0 : listaCarteleria.hashCode());
		result = prime * result + ((listaOEP == null) ? 0 : listaOEP.hashCode());
		result = prime * result + ((serviciosVarios == null) ? 0 : serviciosVarios.hashCode());
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
		VersionPadronRS2018 other = (VersionPadronRS2018) obj;
		if (actividades == null) {
			if (other.actividades != null)
				return false;
		} else if (!actividades.equals(other.actividades))
			return false;
		if (listaCarteleria == null) {
			if (other.listaCarteleria != null)
				return false;
		} else if (!listaCarteleria.equals(other.listaCarteleria))
			return false;
		if (listaOEP == null) {
			if (other.listaOEP != null)
				return false;
		} else if (!listaOEP.equals(other.listaOEP))
			return false;
		if (serviciosVarios == null) {
			if (other.serviciosVarios != null)
				return false;
		} else if (!serviciosVarios.equals(other.serviciosVarios))
			return false;
		return true;
	}

	public Actividades getActividades() {
		return actividades;
	}

	public void setActividades(Actividades actividades) {
		this.actividades = actividades;
	}

	public List<DDJJCarteleria> getListaCarteleria() {
		return listaCarteleria;
	}

	public void setListaCarteleria(List<DDJJCarteleria> listaCarteleria) {
		this.listaCarteleria = listaCarteleria;
	}

	public List<DDJJOEP> getListaOEP() {
		return listaOEP;
	}

	public void setListaOEP(List<DDJJOEP> listaOEP) {
		this.listaOEP = listaOEP;
	}

	public DDJJSV getServiciosVarios() {
		return serviciosVarios;
	}

	public void setServiciosVarios(DDJJSV serviciosVarios) {
		this.serviciosVarios = serviciosVarios;
	}
}