package ar.com.cognisys.sat.core.administrador;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;

public class AdministradorSeguimientos extends Administrador {

	private static final long serialVersionUID = -1919044164256055216L;
	
	/**
	 * Solo para SAT Mobile
	 * 
	 * @param usuario
	 * @param acceso
	 */
	public static void registrarAccesoMobile(String usuario, NavegacionSAT acceso) {
		AdministradorReportes.registrarReportesMobile(acceso, usuario, new Date());
	}
	
	/**
	 * Solo para SAT Web
	 * 
	 * @param usuario
	 * @param acceso
	 */
	public static void registrarAccesoWeb(String usuario, NavegacionSAT acceso) {
		AdministradorReportes.registrarReportesWeb(acceso, usuario, new Date());
	}
}