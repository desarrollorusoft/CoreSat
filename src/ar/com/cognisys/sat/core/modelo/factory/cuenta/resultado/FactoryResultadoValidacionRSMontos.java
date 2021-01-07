package ar.com.cognisys.sat.core.modelo.factory.cuenta.resultado;

import java.util.HashMap;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados.ResultadoValidacionRSMontos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados.TipoResultado;

public class FactoryResultadoValidacionRSMontos {

	public static ResultadoValidacionRSMontos generar() {
		
		ResultadoValidacionRSMontos r = new ResultadoValidacionRSMontos();
		r.setMapaResultados(new HashMap<Integer, TipoResultado>());
		
		return r;
	}
	
	public static ResultadoValidacionRSMontos generar(TipoResultado resultadoGeneral) {
		
		ResultadoValidacionRSMontos r = generar();
		r.setResultadoGeneral(resultadoGeneral);
		
		return r;
	}
}