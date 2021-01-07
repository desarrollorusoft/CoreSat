package ar.com.cognisys.sat.core.modelo.factory.recibo;

import ar.com.cognisys.sat.core.modelo.comun.TasaAbl;
import ar.com.cognisys.sat.core.modelo.comun.TasaProteccionCiudadana;
import ar.com.cognisys.sat.core.modelo.comun.recibos.abl.InformacionPrincipalReciboAbl;
import ar.com.cognisys.sat.core.modelo.comun.recibos.abl.InformacionReciboAbl;

public class FactoryInformacionReciboAbl {

	public static InformacionReciboAbl generarInstanciaVacia() {
		
		InformacionReciboAbl info = new InformacionReciboAbl();
		
		return info;
	}
	
	public static InformacionReciboAbl generarInstanciaCompleta(InformacionPrincipalReciboAbl informacionPrincipal,
																TasaAbl tasaAbl, 
																TasaProteccionCiudadana tasaProteccionCiudadana) {
		
		InformacionReciboAbl info = generarInstanciaVacia();
		info.setInformacionPrincipal(informacionPrincipal);
		info.setTasaAbl(tasaAbl);
		info.setTasaProteccionCiudadana(tasaProteccionCiudadana);
		
		return info;
	}
}