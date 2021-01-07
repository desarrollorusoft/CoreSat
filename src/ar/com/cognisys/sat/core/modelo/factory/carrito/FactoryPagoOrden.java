package ar.com.cognisys.sat.core.modelo.factory.carrito;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.carrito.Destinatario;
import ar.com.cognisys.sat.core.modelo.comun.carrito.Direccion;
import ar.com.cognisys.sat.core.modelo.comun.carrito.Orden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.PagoOrden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.UsuarioCarrito;

public class FactoryPagoOrden {

	public static PagoOrden getInstance(Orden orden,
										UsuarioCarrito usuarioCarrito,
										List<Direccion> direcciones,
										List<Destinatario> destinatarios) {
		PagoOrden pago = new PagoOrden();
		
		pago.setOrder(orden);
		pago.setUser(usuarioCarrito);
		pago.setAddresses(direcciones);
		pago.setReceivers(destinatarios);
		
		return pago;
	}
}