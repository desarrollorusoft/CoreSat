package ar.com.cognisys.sat.v2.persistencia.modelo.excepcion;

import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;

public class ErrorRecuperandoCuentaPorCodigo extends PersistenceSATException {

	private static final long serialVersionUID = -5338587597326494831L;

	public ErrorRecuperandoCuentaPorCodigo(String codigoCuenta, Throwable e) {
		super( "Ocurrio un error al recuperar la cuenta " + codigoCuenta, e );
	}

}
