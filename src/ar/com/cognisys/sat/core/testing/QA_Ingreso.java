package ar.com.cognisys.sat.core.testing;

public class QA_Ingreso {

//	@Test
//	public void probarRecuperacionUsuario() throws ExcepcionControlada {
//		
//		Usuario usuario = AdministradorContribuyente.ingresoContribuyente("jdimodugno@cognisys.com.ar", "cognisys");
//		
//		assertNotNull("El usuario no debe ser nulo", usuario);
//	}
//	
//	@Test
//	public void probarDatosUsuario() throws ExcepcionControlada {
//		
//		Usuario usuario = AdministradorContribuyente.ingresoContribuyente("jdimodugno@cognisys.com.ar", "cognisys");
//		
//		assertEquals("El correo tiene que ser jdimodugno@cognisys.com.ar", "jdimodugno@cognisys.com.ar", usuario.getCorreo());
//		assertEquals("El ID tiene que ser 67", new Integer(67), usuario.getIdUsuario());
//		assertEquals("La clave tiene que ser c90ab32b438850d6ae6b891a698f2a877dddb752bc312962cf9c7ea54782d7e3", "c90ab32b438850d6ae6b891a698f2a877dddb752bc312962cf9c7ea54782d7e3", usuario.getClave().trim());
//		assertEquals("El fax tiene que ser vacio", "", usuario.getTelefono2().trim());
//		assertEquals("El telefono tiene que ser vacio", "", usuario.getTelefono1().trim());
//		assertNotNull("Tiene que tener cuentas", usuario.getCuentas());
//	}
//	
//	@Test
//	public void probarCuentasUsuario() throws ExcepcionControlada {
//		
//		Usuario usuario = AdministradorContribuyente.ingresoContribuyente("jdimodugno@cognisys.com.ar", "cognisys");
//		
//		assertTrue("Tiene que tener cuentas", usuario.getCuentas().getListaCuentas().size() > 0);
//		
//		Cuenta cuenta = usuario.getCuentas().getListaCuentas().get(0);
//		
//		assertNotNull("La cuenta no tiene que estar vacia", cuenta);
//		assertEquals("La cuenta tiene que tener numero", new Integer(110020), cuenta.getNumero());
//		assertEquals("La cuenta tiene que tener descripcion", "MITRE BARTOLOME AV. 3153", cuenta.getDescripcion());
//		assertEquals("La cuenta tiene que tener tipo de cuenta", "ABL", cuenta.getTipoCuenta());
//		assertNotNull("La cuenta tiene que tener contribuyente", cuenta.getContribuyente());
//		assertEquals("La cuenta tiene que tener contribuyente", "JOSE, DE BIASI", cuenta.getContribuyente().getNombreApellido());
//	}
//	
//	@Test
//	public void probarIngresoContribuyente() throws ExcepcionControlada {
//		Usuario usuario = AdministradorContribuyente.ingresoContribuyente("jdimodugno@cognisys.com.ar");
//		assertNotNull("Tiene que existir Usuario", usuario);
//	}
}