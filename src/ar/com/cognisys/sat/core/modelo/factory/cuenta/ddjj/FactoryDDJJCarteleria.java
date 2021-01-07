package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;

public class FactoryDDJJCarteleria {

	private FactoryDDJJCarteleria() { }
	
	public static DDJJCarteleria generar() {
		return new DDJJCarteleria();
	}
	
	public static DDJJCarteleria generar(TipoCartel tipoCartel, Float metros) {
		
		DDJJCarteleria cartel = generar();
		cartel.setTipo(tipoCartel);
		cartel.setMetros(metros);
		
		return cartel;
	}	
	
	public static DDJJCarteleria generar(TipoCartel tipoCartel, Float metros, String descripcion) {
		
		DDJJCarteleria cartel = generar(tipoCartel, metros);
		cartel.setDescripcion(descripcion);
		
		return cartel;
	}
	
	public static DDJJCarteleria generar(TipoCartel tipoCartel, Float metros, String descripcion, String urlImagen) {
		
		DDJJCarteleria cartel = generar(tipoCartel, metros, descripcion);
		cartel.setUrlImagen(urlImagen);
		
		return cartel;
	}
}