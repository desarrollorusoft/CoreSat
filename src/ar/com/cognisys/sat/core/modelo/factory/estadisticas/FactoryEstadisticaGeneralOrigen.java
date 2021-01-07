package ar.com.cognisys.sat.core.modelo.factory.estadisticas;

import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralOrigen;

public class FactoryEstadisticaGeneralOrigen {
	
	public static EstadisticasGeneralOrigen generarInstancia() {
		return new EstadisticasGeneralOrigen();
	}
	
	public static EstadisticasGeneralOrigen generarInstancia(Long pmc, String fecha, Long recibo, 
															 Long interbanking, Long tarjetaCredito) {
		EstadisticasGeneralOrigen e = generarInstancia();
		e.setPmc(pmc);
		e.setFecha(fecha);
		e.setRecibo(recibo);
		e.setInterbanking(interbanking);
		e.setTarjetaCredito(tarjetaCredito);
		return e;
	}
	
	public static EstadisticasGeneralOrigen generarInstancia(Integer id, Long pmc, String fecha, Long recibo, 
															 Long interbanking, Long tarjetaCredito) {
		EstadisticasGeneralOrigen e = generarInstancia(pmc, fecha, recibo, interbanking, tarjetaCredito);
		e.setId(id);
		return e;
	}

	
}