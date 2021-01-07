package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.rs.validador.ValidadorDDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.validador.ValidadorDDJJRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.validador.ValidadorDDJJRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.validador.ValidadorDDJJRS2020;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;

public class FactoryValidadorDDJJRS {

	public static ValidadorDDJJRS generar(VersionPadronRS version) {
		switch (version.getAno()) {
			case 2018: 	return (new ValidadorDDJJRS2018( version ));
			case 2019: 	return (new ValidadorDDJJRS2019( version ));
			case 2020: 	return (new ValidadorDDJJRS2020( version ));
			default: 	return null;
		}
	}
}