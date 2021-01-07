package ar.com.cognisys.sat.v2.core.modelo.transformers;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentasUsuario;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public class TransformadorCuentas {

	public static CuentasUsuario generar(ICuentaView[] cuentasView) {
		// TODO Mejorar esta implementacion
		CuentasUsuario cu = FactoryCuentasUsuario.generar();
		
		for (ICuentaView cv : cuentasView)
			transformar(cv, cu);
		
		return cu;
	}

	private static void transformar(ICuentaView cv, CuentasUsuario cu) {
		TipoCuentaView tcv = TipoCuentaView.obtener( cv.getTipo() );
		TiposCuentas tc = tcv.getBo();
		
		if (tc.equals(TiposCuentas.ABL)) {
			CuentaABL cuenta = FactoryCuenta.generarIntanciaCompletaABL(Integer.parseInt(cv.getCodigo()), cv.getDescripcion(), cv.getAlias());
			cu.agregarCuenta(cuenta);
		} else if (tc.equals(TiposCuentas.COMERCIOS)) {
			CuentaComercios cuenta = FactoryCuenta.generarIntanciaCompletaComercios(Integer.parseInt(cv.getCodigo()), cv.getDescripcion(), cv.getAlias());
			cu.agregarCuenta(cuenta);
		} else if (tc.equals(TiposCuentas.VEHICULOS)) {
			CuentaVehiculos cuenta = FactoryCuenta.generarIntanciaCompletaVehiculos(cv.getCodigo(), cv.getDescripcion(), cv.getAlias());
			cu.agregarCuenta(cuenta);
		} else if (tc.equals(TiposCuentas.RODADOS)) {
			CuentaRodados cuenta = FactoryCuenta.generarIntanciaCompletaRodados(cv.getCodigo(), cv.getDescripcion(), cv.getAlias());
			cu.agregarCuenta(cuenta);
		} else if (tc.equals(TiposCuentas.CEMENTERIO)) {
			CuentaCementerio cuenta = FactoryCuenta.generarIntanciaCompletaCementerio(Integer.parseInt(cv.getCodigo()), cv.getDescripcion(), cv.getAlias());
			cu.agregarCuenta(cuenta);
		}
	}
}