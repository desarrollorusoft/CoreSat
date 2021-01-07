package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.util.Date;

public interface IConsultaCabeceraView extends IConsultaResumenView {

	Date getFecha();
	
	String getCodigoCuenta();
}
