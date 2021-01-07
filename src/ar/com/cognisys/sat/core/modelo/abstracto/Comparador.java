package ar.com.cognisys.sat.core.modelo.abstracto;

public abstract class Comparador<E> {

	private E objetoAnterior;
	private E objetoNuevo;
	
	public Comparador(E objetoAnterior, E objetoNuevo) {
		
		this.setObjetoAnterior(objetoAnterior);
		this.setObjetoNuevo(objetoNuevo);
	}
	
	public boolean seModifico() {
		return (!this.getObjetoNuevo().equals(this.getObjetoAnterior()));
	}
	
	public E getObjetoAnterior() {
		return objetoAnterior;
	}

	public void setObjetoAnterior(E objetoAnterior) {
		this.objetoAnterior = objetoAnterior;
	}

	public E getObjetoNuevo() {
		return objetoNuevo;
	}

	public void setObjetoNuevo(E objetoNuevo) {
		this.objetoNuevo = objetoNuevo;
	}
}