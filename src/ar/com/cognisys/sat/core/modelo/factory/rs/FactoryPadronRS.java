package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.rs.EstadoDeclaracion;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;

public class FactoryPadronRS {

	public static PadronRS generar() {
		
		PadronRS padron = new PadronRS();
		padron.setListaVersiones(new ArrayList<VersionPadronRS>());
		
		return padron;
	}
	
	public static PadronRS generar(String cuenta, String razonSocial, EstadoDeclaracion estado) {
		
		PadronRS padron = generar();
		padron.setNumero(cuenta);
		padron.setRazonSocial(razonSocial);
		padron.setEstado(estado);
		
		return padron;
	}
}