package ar.com.cognisys.sat.core.modelo.factory.carrito;

import ar.com.cognisys.sat.core.modelo.comun.carrito.Direccion;

public class FactoryDireccion {

	public static Direccion getInstance(String addressId,
										String street,
										String no,
										String level,
										String division,
										String phoneNo,
										String countryId,
										String stateId,
										String stateDescrip,
										String city,
										String postalCode,
										String comment,
										String addressNick) {
		
		Direccion address = new Direccion();
		
        address.setAddressId(addressId);
        address.setStreet(street);
        address.setNo(no);
        address.setLevel(level);
        address.setDivision(division);
        address.setPhoneNo(phoneNo);
        address.setCountryId(countryId);
        address.setStateId(stateId);
        address.setStateDescrip(stateDescrip);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setComment(comment);
        address.setAddressNick(addressNick);
		
        return address;
	}
}