package ar.com.cognisys.sat.core.modelo.comparador;

import ar.com.cognisys.sat.core.modelo.abstracto.Comparador;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;

public class ComparadorItem extends Comparador<Item> {

	public ComparadorItem(Item objetoAnterior, Item objetoNuevo) {
		super(objetoAnterior, objetoNuevo);
	}

	public boolean seModificanDatos() {
		
		if (! this.getObjetoAnterior().getTitulo().equals(getObjetoNuevo().getTitulo())) {
			return true;
		
		} else if (this.getObjetoAnterior().getDescripcion() != null && this.getObjetoNuevo().getDescripcion() == null ||
				   this.getObjetoAnterior().getDescripcion() == null && this.getObjetoNuevo().getDescripcion() != null) {
		
			return true;
			
		} else if (this.getObjetoAnterior().getDescripcion() != null && 
				   (! this.getObjetoAnterior().getDescripcion().equals(getObjetoNuevo().getDescripcion()))) {
			
			return true;
		}
		
		return false;
	}

	public boolean seModificaArchivo() {
		
		return (this.getObjetoAnterior().getArchivo() != null || this.getObjetoAnterior().getArchivo().equals(this.getObjetoNuevo().getArchivo()));
	}
}