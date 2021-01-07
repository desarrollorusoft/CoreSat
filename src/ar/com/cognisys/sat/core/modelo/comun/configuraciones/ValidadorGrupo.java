package ar.com.cognisys.sat.core.modelo.comun.configuraciones;

import ar.com.cognisys.sat.core.modelo.abstracto.Validador;

public class ValidadorGrupo extends Validador<Grupo> {

	public ValidadorGrupo(Grupo dato) {
		super(dato);
	}

	@Override
	public boolean esCorrecto() {
		
		boolean respuesta = true;
		
		if (this.tieneNulos()) {
			this.setMensajeError("Deben completarse los campos obligatorios");
			respuesta = false;
		
		} else if (this.getDato().getNombre().equals("")) {
			this.setMensajeError("Deben completarse los campos obligatorios");
			respuesta = false;
		
		} else if ((this.getDato().getPadre() == null && this.getDato().getPantalla() == null)) {
			this.setMensajeError("El grupo debe estar relacionado a un padre o pantalla");
			respuesta = false;
		}
		
		return respuesta;
	}

	@Override
	public boolean tieneNulos() {
		return (this.getDato() == null || this.getDato().getNombre() == null || this.getDato().getNombre().equals(""));
	}	
}