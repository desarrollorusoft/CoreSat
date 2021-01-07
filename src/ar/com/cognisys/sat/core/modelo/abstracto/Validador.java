package ar.com.cognisys.sat.core.modelo.abstracto;

public abstract class Validador<E> {
	
	private E dato;
	private boolean ok;
	private String mensajeError;
	
	public Validador(E dato) {
		this.setDato(dato);
	}
	
	public abstract boolean esCorrecto();

	public abstract boolean tieneNulos();
	
	public void concatenarMensajeError(String mensajeNuevo) {
		
		if (this.getMensajeError() == null) {
			this.setMensajeError(mensajeNuevo);
		} else {
			this.setMensajeError( this.getMensajeError() + "\n" + mensajeNuevo );
		}
		
		this.setOk(false);
	}
	
	public E getDato() {
		return dato;
	}

	public void setDato(E dato) {
		this.dato = dato;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}