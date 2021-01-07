package ar.com.cognisys.sat.v2.persistencia.modelo.excepcion;

import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;

public class ErrorRecuperandoCuentasPorUsuario extends PersistenceSATException {

	private static final long serialVersionUID = -2937932933025801453L;

	public ErrorRecuperandoCuentasPorUsuario(Integer idUsuario, Throwable cause) {
		super( "Ocurrio un error al recuperar las cuentas para el usuario " + String.valueOf( idUsuario ), cause );
	}

}
