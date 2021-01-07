package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;

public class FactoryCuentasUsuario {

	public static CuentasUsuario generar() {
		
		CuentasUsuario cu = new CuentasUsuario();
		cu.setListaCuentas(new ArrayList<Cuenta>());
		
		return cu;
	}

	public static CuentasUsuario generar(List<Cuenta> listaCuentas) {
		
		CuentasUsuario cu = generar();
		cu.setListaCuentas(listaCuentas);
		
		return cu;
	}
}