package ar.com.cognisys.sat.v2.core.modelo.exception;

public class MontoExcedidoException extends CoreSATException {

	private static final long serialVersionUID = 6451240495842432162L;

	public MontoExcedidoException(String montoPermitido, String montoDeclarado) {
		super( "El monto total declarado ( $ "+montoDeclarado+" ) supera el máximo permitido ( $ "+montoPermitido+" ) para el Régimen Simplificado" );
	}
}