package ar.com.cognisys.sat.core.modelo.factory.estadisticas;

import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralCompleto;

public class FactoryEstadisticaGeneralCompleta {
	
	public static EstadisticasGeneralCompleto generarInstancia() {
		return new EstadisticasGeneralCompleto();
	}
	
	public static EstadisticasGeneralCompleto generarInstancia(String medio, String fecha, String tributo, 
															   Long cantidadWeb, Long cantidadMobile, Long total) {
		EstadisticasGeneralCompleto e = generarInstancia();
		e.setMedio(medio);
		e.setFecha(fecha);
		e.setTributo(tributo);
		e.setCantidadWeb(cantidadWeb);
		e.setCantidadMobile(cantidadMobile);
		e.setTotal(total);
		return e;
	}
	
	public static EstadisticasGeneralCompleto generarInstancia(Integer id, String medio, String fecha, String tributo,
															   Long cantidadWeb, Long cantidadMobile, Long total) {
		
		EstadisticasGeneralCompleto e = generarInstancia(medio,fecha,tributo,cantidadWeb,cantidadMobile,total);
		e.setId(id);
		return e;
	}
}