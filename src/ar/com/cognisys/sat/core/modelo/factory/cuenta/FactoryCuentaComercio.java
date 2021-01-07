package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJ;

public class FactoryCuentaComercio {

	public static CuentaComercios generar() {
		
		CuentaComercios cc = new CuentaComercios();
		cc.setListaDeclaraciones(new ArrayList<DDJJ>());
		
		return cc;
	}
	
	/**
	 * Se utiliza para cuando no se tiene una DDJJ. Se crea una vacia
	 * @param cuenta
	 * @param razonSocial
	 * @return
	 */
	public static CuentaComercios generar(Integer cuenta, String razonSocial) {
		
		CuentaComercios cc = generar();
		cc.setNumero(cuenta);
		cc.setRazonSocial(razonSocial);
		
		return cc;
	}

	/**
	 * Se utiliza para cuando ya se tiene una DDJJ
	 * @param cuenta
	 * @param razonSocial
	 * @param ddjj
	 * @return
	 */
	public static CuentaComercios generar(Integer cuenta, String razonSocial, DDJJ ddjj) {
		
		CuentaComercios cc = generar();
		cc.setNumero(cuenta);
		cc.setRazonSocial(razonSocial);
		cc.getListaDeclaraciones().add(ddjj);
		
		return cc;
	}
}