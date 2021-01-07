package ar.com.cognisys.sat.v2.core.modelo.exception;

public class GetFileDataException extends CoreSATException {

	private static final long serialVersionUID = -1729769026768311881L;

	public GetFileDataException() {
		super( "No se pudo obtener la información del archivo" );
	}

}
