package ar.com.cognisys.sat.core.modelo.validador;

import ar.com.cognisys.sat.core.modelo.abstracto.Validador;
import ar.com.cognisys.sat.core.modelo.comun.interfazSAT.DocumentoInterfaz;

public class ValidadorDocumentoInterfaz extends Validador<DocumentoInterfaz> {

	public ValidadorDocumentoInterfaz(DocumentoInterfaz dato) {
		super(dato);
	}

	@Override
	public boolean esCorrecto() {
		
		return ( !tieneNulos() && tieneArchivo() );
	}

	@Override
	public boolean tieneNulos() {
		
		if (this.getDato() == null) {
			this.setMensajeError("El objeto no puede ser nulo");
			return true;
		} else if (this.getDato().getNombre() == null && this.getDato().getNombre().equals("")) {
			this.setMensajeError("El NOMBRE es necesario");
			return true;
		}
		
		return false;
	}
	
	private boolean tieneArchivo() {
		
		if (this.getDato().getListaArchivos() == null || this.getDato().getListaArchivos().isEmpty()) {
			this.setMensajeError("Se debe asociar un archivo");
			return false;
		} else {
			return true;
		}
	}
}