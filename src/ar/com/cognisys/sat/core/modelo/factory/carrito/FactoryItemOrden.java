package ar.com.cognisys.sat.core.modelo.factory.carrito;

import ar.com.cognisys.sat.core.modelo.comun.carrito.ItemOrden;

public class FactoryItemOrden {

	public static ItemOrden getInstance(String id,
										String descripcion,
										String units,
										String price,
										String unitPrice,
										String addressId,
										String recieverId,
										String deliveryDate,
										String availableDays) {
		ItemOrden item = new ItemOrden();
		item.setId(id);
		item.setDescrip(descripcion);
		item.setUnits(units);
		item.setPrice(price);
		item.setUnitPrice(unitPrice);
		item.setAddressId(addressId);
		item.setReceiverId(recieverId);
		item.setDeliveryDate(deliveryDate);
		item.setAvailableDays(availableDays);
		
		return item;
	}
}