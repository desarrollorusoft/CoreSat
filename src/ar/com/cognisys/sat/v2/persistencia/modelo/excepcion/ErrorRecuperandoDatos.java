package ar.com.cognisys.sat.v2.persistencia.modelo.excepcion;

import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;

public class ErrorRecuperandoDatos extends PersistenceSATException {

	private static final long serialVersionUID = 3117362669470629599L;

	public ErrorRecuperandoDatos(Integer numeroCuenta, Throwable e) {
		super( "Ocurrio un error al recuperar los datos de la cuenta " + String.valueOf( numeroCuenta ), e );
	}
}
