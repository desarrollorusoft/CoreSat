package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.util.ArrayList;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;

public class FactoryDDJJRS {

	public static DDJJRS generar() {
		
		DDJJRS ddjj = new DDJJRS();
		ddjj.setListaPadrones(new ArrayList<PadronRS>());
		
		return ddjj;
	}
	
	public static DDJJRS generar(Integer ano, Date fechaActualizacion) {
		
		DDJJRS ddjj = generar();
		ddjj.setAno(ano);
		ddjj.setFechaActualizacion(fechaActualizacion);
		
		return ddjj;
	}
}