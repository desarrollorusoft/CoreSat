package ar.com.cognisys.sat.core.modelo.comun.configuraciones;

import ar.com.cognisys.sat.core.modelo.abstracto.Comparador;

public class ComparadorGrupo extends Comparador<Grupo> {

	public ComparadorGrupo(Grupo objetoAnterior, Grupo objetoNuevo) {
		super(objetoAnterior, objetoNuevo);
	}

	public boolean seModificaronDatos() {
		
		return (!this.getObjetoAnterior().getNombre().equals(this.getObjetoNuevo().getNombre()));
	}
	
	public boolean seModificoLaRelacion() {
		
		return (!this.getObjetoAnterior().getPadre().equals(this.getObjetoNuevo().getPadre()));
	}
}