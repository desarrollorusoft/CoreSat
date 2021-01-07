package ar.com.cognisys.sat.core.modelo.factory.carrito;

import ar.com.cognisys.sat.core.modelo.comun.carrito.UsuarioCarrito;

public class FactoryUsuarioCarrito {

	public static UsuarioCarrito getInstance(String firstName,
										String lastName,
										String docId,
										String docNo,
										String ivaId,
										String email,
										String courtTitleId,
										String occupationId,
										String phoneNo,
										String mobileNo,
										String faxNo,
										String birthDay,
										String countryId,
										String addToEmailList) {
		UsuarioCarrito usuarioCarrito = new UsuarioCarrito();
		
		usuarioCarrito.setFirstName(firstName);
		usuarioCarrito.setLastName(lastName);
		usuarioCarrito.setDocId(docId);
        usuarioCarrito.setDocNo(docNo);
        usuarioCarrito.setIvaId(ivaId);
        usuarioCarrito.setEmail(email);
        usuarioCarrito.setCourtTitleId(courtTitleId);
        usuarioCarrito.setOccupationId(occupationId);
        usuarioCarrito.setPhoneNo(phoneNo);
        usuarioCarrito.setMobileNo(mobileNo);
        usuarioCarrito.setFaxNo(faxNo);
        usuarioCarrito.setBirthDay(birthDay);
        usuarioCarrito.setCountryId(countryId);
        usuarioCarrito.setAddToEmailList(addToEmailList);
		
		return usuarioCarrito;
	}
}