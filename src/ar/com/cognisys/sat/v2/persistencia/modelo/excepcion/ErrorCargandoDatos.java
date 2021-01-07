package ar.com.cognisys.sat.v2.persistencia.modelo.excepcion;

import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;

public class ErrorCargandoDatos extends PersistenceSATException {

	private static final long serialVersionUID = 8761102997060226404L;
	
	public ErrorCargandoDatos(String codigoCuenta, Throwable e) {
		super( "Ocurrio un error al cargar los datos de la cuenta " + codigoCuenta, e );
	}
}
