package ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs;

import java.io.Serializable;

@Deprecated public interface IRegimenView extends Serializable {

	IActividadView getDatosActividad();
	
	ISolicitanteView getDatosSolicitante();
}