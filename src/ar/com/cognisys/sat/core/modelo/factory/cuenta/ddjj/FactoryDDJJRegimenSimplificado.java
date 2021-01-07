package ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj;

import java.util.ArrayList;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJSV;

public class FactoryDDJJRegimenSimplificado {

	public static DDJJRegimenSimplificado generar() {
		
		DDJJRegimenSimplificado ddjjrs = new DDJJRegimenSimplificado();
		ddjjrs.setListaCarteleria(new ArrayList<DDJJCarteleria>());
		ddjjrs.setOcupacion( new ArrayList<DDJJOEP>() );
		ddjjrs.setFechaInicio(new Date());
		ddjjrs.setVez(0);
		
		return ddjjrs;
	}
	
	public static DDJJRegimenSimplificado generar(Float monto) {
		
		DDJJRegimenSimplificado ddjjrs = generar();
		ddjjrs.setGanancia(monto);
		
		return ddjjrs;
	}
	
	public static DDJJRegimenSimplificado generar(boolean confirmado, Float ganancia, DDJJSV sv, Actividades actividades,
												  Integer cantidadPersonas, Date fechaHabilitacion) {
		
		DDJJRegimenSimplificado ddjjrs = generar();
		ddjjrs.setEnCurso(!confirmado);
		ddjjrs.setGanancia(ganancia);
		ddjjrs.setServiciosValios(sv);
		ddjjrs.setActividades(actividades);
		ddjjrs.setCantidadPersonas(cantidadPersonas);
		ddjjrs.setFechaHabilitacion(fechaHabilitacion);
		
		return ddjjrs;
	}
	
	public static DDJJRegimenSimplificado generar(boolean confirmado, Float ganancia, DDJJSV sv, Actividades actividades,
					  							  Integer cantidadPersonas, Date fechaHabilitacion, String cuentaABL) {
		
		DDJJRegimenSimplificado ddjjrs = generar(confirmado, ganancia, sv, actividades, cantidadPersonas, fechaHabilitacion);
		ddjjrs.setCuentaABL(cuentaABL != null && !cuentaABL.equals("") ? Integer.parseInt(cuentaABL) : 0);
		
		return ddjjrs;
	}
	
	public static DDJJRegimenSimplificado generar(boolean confirmado, Float ganancia, DDJJSV sv, Actividades actividades,
					  							  Integer cantidadPersonas, Date fechaHabilitacion, Date fechaInicio,
					  							  Integer ano, Date fechaConfirmacion, Integer cuentaABL, Integer vez) {
		
		DDJJRegimenSimplificado ddjjrs = generar(confirmado, ganancia, sv, actividades, cantidadPersonas, fechaHabilitacion);
		ddjjrs.setFechaInicio(fechaInicio);
		ddjjrs.setAno(ano);
		ddjjrs.setFechaConfirmacion(fechaConfirmacion);
		ddjjrs.setCuentaABL(cuentaABL);
		ddjjrs.setVez(vez);
		
		return ddjjrs;
	}
}