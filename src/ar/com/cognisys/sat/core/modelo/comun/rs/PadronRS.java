package ar.com.cognisys.sat.core.modelo.comun.rs;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryVersionPadronRS;

public class PadronRS {

	private String numero;
	private String razonSocial;
	private List<VersionPadronRS> listaVersiones;
	private VersionPadronRS versionEnCurso;
	private EstadoDeclaracion estado;
	
	public boolean sos(String padron) {
		return this.getNumero().equals( padron );
	}
	
	public Integer getVersion() {
		if (this.getListaVersiones() == null || this.getListaVersiones().isEmpty())
			return 0;
		else
			return this.getListaVersiones().size()-1;
	}
	
	public boolean tieneDeclaracionEnCurso() {
		VersionPadronRS version = this.obtenerUltimaVersion();
		
		return (version == null ? true : !version.isCompleto());
	}
	
	public VersionPadronRS obtenerUltimaVersion() {
		VersionPadronRS version = null;
		
		for (VersionPadronRS v : this.getListaVersiones())
			if (version == null || version.getVersion() < v.getVersion())
				version = v;
		
		return version;
	}
	
	public boolean tieneVersionInferiorA(int versionMaxima) {
		VersionPadronRS version = this.obtenerUltimaVersion();
		
		return (version == null ? false : version.getVersion() < versionMaxima);
	}
	
	public void agregar(VersionPadronRS version) {
		if (this.getListaVersiones().isEmpty())
			this.setListaVersiones(new ArrayList<VersionPadronRS>());
		
		this.getListaVersiones().add( version );
	}
	
	public void agregar(List<VersionPadronRS> versiones) {
		this.setListaVersiones( versiones );
	}
	
	public void iniciarRectificacion(Integer ano) {
		VersionPadronRS version = this.obtenerUltimaVersion();
		
		if (version == null)
			version = FactoryVersionPadronRS.generarVacio(ano); 
			
		version.setCompleto(false);
		setEstado( EstadoDeclaracion.PENDIENTE );
	}
	
	public void iniciarNuevaVersion(Integer ano) {
		VersionPadronRS version = this.obtenerUltimaVersion();
		
		int n = 0;
		if (version != null)
			n = version.getVersion() + 1;
			
		VersionPadronRS nueva = FactoryVersionPadronRS.generarVacio(ano);
		nueva.setVersion( n );
		nueva.setCompleto( false );
		
		this.getListaVersiones().add( nueva );
	}
	
	public void iniciarModificacion() {
		this.setVersionEnCurso( this.obtenerUltimaVersion().generarClon() );
	}
	
	public void prepararGuardado() {
		this.getListaVersiones().remove( this.obtenerUltimaVersion() );
		this.getListaVersiones().add( this.getVersionEnCurso() );
	}
	
	public Integer obtenerAnoDeclaracion() {
		VersionPadronRS version = this.obtenerUltimaVersion();
	
		return (version == null ? null : version.getAno());
	}
	
	public boolean tieneVersiones() {
		return this.getListaVersiones() != null && !this.getListaVersiones().isEmpty();
	}
	
	public void cancelaVersion(VersionPadronRS version) {
		this.getListaVersiones().remove( version );
		this.setEstado( EstadoDeclaracion.CONFIRMADA );
	}
	
	public boolean tieneVersionNueva() {
		VersionPadronRS version = this.obtenerUltimaVersion();
		return ( version != null && !version.tieneID() );
	}
	
	public String getEstadoLabel() {
		return this.tieneDeclaracionEnCurso() ? "Pendiente" : "Completo";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((razonSocial == null) ? 0 : razonSocial.hashCode());
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
		PadronRS other = (PadronRS) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (razonSocial == null) {
			if (other.razonSocial != null)
				return false;
		} else if (!razonSocial.equals(other.razonSocial))
			return false;
		return true;
	}

	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public List<VersionPadronRS> getListaVersiones() {
		return listaVersiones;
	}

	public void setListaVersiones(List<VersionPadronRS> listaVersiones) {
		this.listaVersiones = listaVersiones;
	}

	public VersionPadronRS getVersionEnCurso() {
		return versionEnCurso;
	}

	public void setVersionEnCurso(VersionPadronRS versionEnCurso) {
		this.versionEnCurso = versionEnCurso;
	}

	public EstadoDeclaracion getEstado() {
		return estado;
	}

	public void setEstado(EstadoDeclaracion estado) {
		this.estado = estado;
	}
}