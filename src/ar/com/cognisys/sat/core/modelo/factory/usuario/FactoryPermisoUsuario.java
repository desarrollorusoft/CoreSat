package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.permiso.PermisoSAT;
import ar.com.cognisys.sat.core.modelo.comun.permiso.PermisoUsuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class FactoryPermisoUsuario {

	public static PermisoUsuario generarInstancia() {
		
		PermisoUsuario pu = new PermisoUsuario();
		
		return pu;
	}
	
	public static PermisoUsuario generarInstancia(String permiso, String datoCuenta, Integer tipoCuenta) {
		
		PermisoUsuario pu = generarInstancia();
		pu.setPermiso( PermisoSAT.valueOf( permiso ) );
		pu.setNumeroCuenta( datoCuenta );
		pu.setTipoCuenta( TiposCuentas.recuperarTipoCuentaPorCodUsu(tipoCuenta) );
		
		return pu;
	}
}