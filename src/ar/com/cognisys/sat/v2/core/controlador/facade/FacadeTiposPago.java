package ar.com.cognisys.sat.v2.core.controlador.facade;

import ar.com.cognisys.sat.core.modelo.enums.MediosPago;

public class FacadeTiposPago {

	/**
	 * Método utilizado para recuperar los tipos de pago existentes 
	 * y mostrarlos en el Formulario de Pago.
	 * 
	 * @return tiposPago
	 */
	public String[] recuperarTodos() {
		
		MediosPago[] listado = MediosPago.values();
		String[] pagos = new String[listado.length];
		
		for (int i = 0; i < listado.length; i++)
			pagos[i] = listado[i].getDescripcion();
		
		return pagos;
	}
	
}
