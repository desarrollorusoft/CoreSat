package ar.com.cognisys.sat.core.modelo.factory.planDePago;

import ar.com.cognisys.sat.core.modelo.enums.EstadoCuotaAVencer;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class FactoryEstadoCuotaAVencer {

	public static EstadoCuotaAVencer generarInstancia(Integer resultado) throws ExcepcionControladaError {
		
		switch (resultado) {
		case 0:
			return EstadoCuotaAVencer.AL_DIA;
		case 1:
			return EstadoCuotaAVencer.CON_DEUDA_LEGALES;
		case 2:
			return EstadoCuotaAVencer.EN_MORA;
		default:
			throw new ExcepcionControladaError("Estado de cuota a vencer no definido", null);
		}
	}
}