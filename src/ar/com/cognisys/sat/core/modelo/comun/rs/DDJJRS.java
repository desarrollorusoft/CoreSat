package ar.com.cognisys.sat.core.modelo.comun.rs;

import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;

public class DDJJRS {

	private Integer ano;
	private List<PadronRS> listaPadrones;
	private Date fechaActualizacion;
	
	public EstadoDeclaracion getEstado() {
		EstadoDeclaracion estado = EstadoDeclaracion.CONFIRMADA;
		
		for (PadronRS p : this.getListaPadrones())
			if (p.getEstado().getPeso() > estado.getPeso())
				estado = p.getEstado();

		return estado;
	}
	
	public PadronRS recuperar(String numero) {
		for (PadronRS p : this.getListaPadrones())
			if (p.sos( numero ))
				return p;
		
		return null;
	}
	
	public Integer getVersion() {
		int version = 0;
		
		for (PadronRS p : this.getListaPadrones())
			if (p.getVersion() > version)
				version = p.getVersion();
		
		return version;
	}
	
	public boolean estaCompleta() {
		for (PadronRS p : this.getListaPadrones())
			if (p.tieneDeclaracionEnCurso())
				return false;
		
		return true;
	}
	
	public boolean estaPendiente() {
		return this.getEstado().equals( EstadoDeclaracion.PENDIENTE );
	}
	
	public boolean puedeCompletar(PadronRS padron) {
		if (this.getEstado().equals( EstadoDeclaracion.PENDIENTE ))
			return padron.tieneDeclaracionEnCurso();
		else
			return false;		
	}
	
	public boolean puedeRectificar(PadronRS padron) {
		EstadoDeclaracion estadoActual = this.getEstado();
		
		if (estadoActual.equals( EstadoDeclaracion.PENDIENTE ))
			return !padron.tieneDeclaracionEnCurso();
		
		else if (estadoActual.equals( EstadoDeclaracion.CONFIRMADA ))
			return padron.tieneVersionInferiorA( 1 );
		
//		else if (this.getEstado().equals( EstadoDeclaracion.RECHAZADA ))
//			return true;
//		
		else /* NO_APLICA y RECHAZADA */
			return false;
	}
	
	public boolean puedeCancelarRectificativa(PadronRS padron) {
		if (padron.getEstado().equals( EstadoDeclaracion.PENDIENTE )) {
			VersionPadronRS ultima = padron.obtenerUltimaVersion();
					
			return (ultima != null && ultima.getVersion() > 0);
		} else
			return false;
	}
	
	public void iniciarRectificacion(PadronRS padron) {
		
		if (padron.getEstado().equals( EstadoDeclaracion.CONFIRMADA )) {
			padron.setEstado( EstadoDeclaracion.PENDIENTE );
			padron.iniciarNuevaVersion( this.getAno() );
		} else
			padron.iniciarRectificacion( this.getAno() );
	}
	
	public void iniciarRectificativa(PadronRS padron) {
		
	}
	
	public boolean puedeOperar() {
//		return (ano != 2020);
		return true;
	}
	
	public void agregar(PadronRS padron) {
		this.getListaPadrones().add( padron );
	}
	
	public boolean sos(String ano) {
		return this.sos( Integer.parseInt( ano ) );
	}
	
	public boolean sos(int ano) {
		return this.getAno().equals( ano );
	}
	
	public boolean tiene(PadronRS padron) {
		for (PadronRS p : this.getListaPadrones())
			if (p.equals( padron ) )
				return true;
		
		return false;
	}
	
	public void cancelaRectificativa(PadronRS padron, VersionPadronRS version) {
		padron.cancelaVersion(version);
	}
	
	public void confirmada() {
		for (PadronRS padronRS : this.getListaPadrones())
			padronRS.setEstado( EstadoDeclaracion.CONFIRMADA );
	}
	
	public void rechazada() {
		for (PadronRS padronRS : this.getListaPadrones())
			padronRS.setEstado( EstadoDeclaracion.RECHAZADA );
	}
	
	public String getLabelPadron( PadronRS padron ) {
		if (this.getEstado().equals( EstadoDeclaracion.RECHAZADA ))
			return "Excedido";
		else
			return padron.getEstadoLabel();
	}
	
	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<PadronRS> getListaPadrones() {
		return listaPadrones;
	}

	public void setListaPadrones(List<PadronRS> listaPadrones) {
		this.listaPadrones = listaPadrones;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}