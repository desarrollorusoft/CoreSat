package ar.com.cognisys.sat.core.modelo.validador;

import ar.com.cognisys.sat.core.modelo.abstracto.Validador;
import ar.com.cognisys.sat.core.modelo.comun.configuraciones.Item;

public class ValidadorGuiaTramite extends Validador<Item> {

	public ValidadorGuiaTramite(Item dato) {
		super(dato);
	}

	@Override
	public boolean esCorrecto() {
		
		if (this.tieneNulos()) {
			this.setMensajeError("Debe completar los campos obligatorios");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean tieneNulos() {
		return (this.getDato().getTitulo() == null || this.getDato().getDescripcion() == null);
	}
}