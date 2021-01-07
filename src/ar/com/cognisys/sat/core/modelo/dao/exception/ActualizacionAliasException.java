package ar.com.cognisys.sat.core.modelo.dao.exception;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class ActualizacionAliasException extends ExcepcionControladaError {

    public ActualizacionAliasException(Throwable cause) {
        super("No es posible actualizar el alias", cause);
    }
}
