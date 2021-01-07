package ar.com.cognisys.sat.core.administrador;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.registro.RegistradorReportes;

public class AdministradorReportes extends Administrador {

	private static final long serialVersionUID = 574514565555670259L;

	private static AdministradorReportes instancia = new AdministradorReportes();
	
	public static void registrarReportesMobile(NavegacionSAT acceso, String nombreUsuario, Date fecha) {
		registrar(acceso, nombreUsuario, fecha, Sistema.SAT_Mobile);			
	}

	public static void registrarReportesWeb(NavegacionSAT acceso, String nombreUsuario, Date fecha) {
		registrar(acceso, nombreUsuario, fecha, Sistema.SAT);
	}
	
	private static void registrar(NavegacionSAT acceso, String nombreUsuario, Date fecha, Sistema sistema) {
		new RegistradorReportes().registrarReportes(acceso, nombreUsuario, fecha, sistema);
	}

	public static AdministradorReportes getInstancia() {
		
		if (instancia == null) {
			instancia = new AdministradorReportes();
		}
		
		return instancia;
	}

	public static void setInstancia(AdministradorReportes instancia) {
		AdministradorReportes.instancia = instancia;
	}
}