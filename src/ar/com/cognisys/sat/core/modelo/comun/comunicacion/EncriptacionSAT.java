package ar.com.cognisys.sat.core.modelo.comun.comunicacion;

import org.apache.commons.codec.binary.Base64;

public class EncriptacionSAT {

    public String encriptar(String texto) {   
    	return new String(Base64.encodeBase64(texto.getBytes()));
    }
    
    public String desencriptar(String encriptacion) {
    	return new String(Base64.decodeBase64(encriptacion.getBytes()));
    }
    
    public static void main(String[] args) throws Exception {
		EncriptacionSAT es = new EncriptacionSAT();
		
		String dato = "\",\"tipoDocumento\":1,\"numeroDocumento\":13532329,\"correo\":\"\",\"domicilio\":{\"calle\":\"ALSINA ADOLFO\",\"altura\":3841,\"piso\":\"\",\"departamento\":\"\",\"codigoPostal\":1603,\"descripcionCompleta\":\"ALSINA ADOLFO 3841\"}},\"selected\":false},{\"numero\":512793,\"descripcion\":\"OBLIGADO RAFAEL 6458\",\"contribuyente\":{\"nombre\":\"S.A\",\"apellido\":\"INAYEMTUM\",\"tipoDocumento\":5,\"numeroDocumento\":512793,\"correo\":\"\",\"domicilio\":{\"calle\":\"OBLIGADO RAFAEL\",\"altura\":6458,\"piso\":\"\",\"departamento\":\"\",\"codigoPostal\":1606,\"descripcionCompleta\":\"OBLIGADO RAFAEL 6458\"}},\"selected\":false},{\"numero\":110002,\"descripcion\":\"CABRAL SGTO 3076\",\"contribuyente\":{\"nombre\":\"CLAUDIO\",\"apellido\":\"MEHL\",\"tipoDocumento\":5,\"numeroDocumento\":110002,\"correo\":\"\",\"domicilio\":{\"calle\":\"CABRAL SGTO\",\"altura\":3076,\"piso\":\"\",\"departamento\":\"\",\"codigoPostal\":1605},\"telefono\":\"0\",\"telefono2\":\"0\"},\"selected\":false}],\"listaCuentasComercio\":[],\"listaCuentasVehiculos\":[{\"dominio\":\"SHZ785\",\"numero\":90434,\"descripcion\":\"SHZ785 1994\",\"contribuyente\":{\"nombre\":\"FERNANDO JAVIER\",\"apellido\":\"RODRIGUEZ\",\"tipoDocumento\":9,\"numeroDocumento\":90434,\"correo\":\"\",\"domicilio\":{\"calle\":\"VENEZUELA\",\"altura\":4380,\"piso\":\"\",\"departamento\":\"\",\"codigoPostal\":1638},\"telefono\":\"\"},\"selected\":false},{\"dominio\":\"DWR262\",\"numero\":291674,\"descripcion\":\"DWR262 2001\",\"contribuyente\":{\"nombre\":\"SILVIA MARIA\",\"apellido\":\"DE SCHILLER\",\"tipoDocumento\":1,\"numeroDocumento\":4774991,\"correo\":\"\",\"domicilio\":{\"calle\":\"VALLE GRANDE\",\"altura\":1345,\"piso\":\"\",\"departamento\":\"\",\"codigoPostal\":1602},\"telefono\":\"\",\"telefono2\":\"\"},\"selected\":false}],\"listaCuentasRodados\":[{\"dominio\":\"611JMN\",\"numero\":37194,\"descripcion\":\"HONDA 249\",\"contribuyente\":{\"nombre\":\"PABLO DANIEL\",\"apellido\":\"MENDIZABAL\",\"tipoDocumento\":1,\"numeroDocumento\":31662364,\"correo\":\"\",\"domicilio\":{\"calle\":\"MELO CARLOS F.\",\"altura\":2250,\"piso\":\"3\",\"departamento\":\"43\",\"codigoPostal\":1602}},\"selected\":false},{\"dominio\":\"069IOY\",\"numero\":46104,\"descripcion\":\"HONDA 124\",\"contribuyente\":{\"nombre\":\"NICOLAS\",\"apellido\":\"HUTFLUS\",\"tipoDocumento\":7,\"numeroDocumento\":28316594,\"correo\":\"\",\"domicilio\":{\"calle\":\"HIPOLITO YRIGOYEN\",\"altura\":4368,\"codigoPostal\":1602}},\"selected\":false},{\"dominio\":\"147IUW\",\"numero\":47285,\"descripcion\":\"BETAMOTOR 110\",\"contribuyente\":{\"nombre\":\"JOAQUIN OCTAVIO\",\"apellido\":\"CALARCO\",\"tipoDocumento\":7,\"numeroDocumento\":36294235,\"correo\":\"\",\"domicilio\":{\"calle\":\"BERNARDO DE YRIGOYEN\",\"altura\":190,\"codigoPostal\":1603},\"telefono\":\"1554638235\"},\"selected\":false}]},\"correo\":\"satmvl@vicentelopez.gov.ar\",\"telefono1\":\"                    \",\"telefono2\":\"                    \",\"clave\":\"339efeab70b4cc6e2755ec57d2290484ef2363f955c16df3900ad44382227429                \",\"newsLetter\":true,\"activo\":false,\"nombreUsuario\":\"satmvl@vicentelopez.gov.ar\",\"listaPerfiles\":[{\"nombre\":\"Contribuyente\",\"listaPermisos\":[{\"nombre\":\"Contribuyente\"}]}],\"fechaUltimoIngreso\":\"Aug 14, 2015 12:04:30 PM\",\"seguimiento\":{\"listaAccesos\":[]}}";
		
		System.out.println(dato);
		
		String encriptacion = es.encriptar(dato);
		
		System.out.println(encriptacion);
		
		System.out.println(es.desencriptar(encriptacion));
	}
}