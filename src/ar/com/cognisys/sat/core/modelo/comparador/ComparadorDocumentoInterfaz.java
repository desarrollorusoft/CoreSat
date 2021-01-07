package ar.com.cognisys.sat.core.modelo.comparador;

import ar.com.cognisys.sat.core.modelo.abstracto.Comparador;
import ar.com.cognisys.sat.core.modelo.comun.interfazSAT.DocumentoInterfaz;

public class ComparadorDocumentoInterfaz extends Comparador<DocumentoInterfaz> {

	public ComparadorDocumentoInterfaz(DocumentoInterfaz objetoAnterior, DocumentoInterfaz objetoNuevo) {
		super(objetoAnterior, objetoNuevo);
	}
	
	public boolean seModificoDocumento() {
		
		return !((this.getObjetoNuevo().getNombre().equals(getObjetoAnterior().getNombre())) ||
				 (this.getObjetoNuevo().getDescripcion().equals(getObjetoAnterior().getDescripcion())) ||
				 (this.getObjetoNuevo().getCategoria().equals(getObjetoAnterior().getCategoria())));
	}
	
	public boolean seModificoArchivo() {
		
		if (this.getObjetoAnterior().getListaArchivos().size() != this.getObjetoNuevo().getListaArchivos().size()) {
			return true;
		}
		
		for (int i = 0; i < this.getObjetoAnterior().getListaArchivos().size(); i++) {
			
			if (!((this.getObjetoNuevo().getListaArchivos().get(i).getNombre().equals(getObjetoAnterior().getListaArchivos().get(i))) ||
				  (this.getObjetoNuevo().getListaArchivos().get(i).getTipoContenido().equals(getObjetoAnterior().getListaArchivos().get(i))) ||
				  (this.getObjetoNuevo().getListaArchivos().get(i).getData().equals(getObjetoAnterior().getListaArchivos().get(i))))) {
				
				return true;
			}
		}
		
		return false;
	}
}