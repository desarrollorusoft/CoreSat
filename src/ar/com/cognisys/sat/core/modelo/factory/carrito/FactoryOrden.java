package ar.com.cognisys.sat.core.modelo.factory.carrito;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.carrito.ItemOrden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.Orden;

public class FactoryOrden {

	public static Orden getInstance(String id,
									String currencyId,
									String subTotal,
									String shipping,
									String discount,
									String taxes,
									String total,
									String payments,
									String deliverable,
									String oneShipment,
									String deliveryDate,
									String shipToMultAddresses,
									String addressId,
									String receiverId,
									String availableDays,
									String expirationHours,
									List<ItemOrden> items) {
		Orden orden = new Orden();
		
		orden.setId(id);
		orden.setCurrencyId(currencyId);
		orden.setSubTotal(subTotal);
		orden.setShipping(shipping);
		orden.setDiscount(discount);
		orden.setTaxes(taxes);
		orden.setTotal(total);
		orden.setPayments(payments);
		orden.setDeliverable(deliverable);
		orden.setOneShipment(oneShipment);
		orden.setDeliveryDate(deliveryDate);
		orden.setShipToMultAddresses(shipToMultAddresses);
		orden.setAddressId(addressId);
		orden.setReceiverId(receiverId);
		orden.setAvailableDays(availableDays);
		orden.setExpirationHours(expirationHours);
		orden.setItems(items);
		
		return orden;
	}
}