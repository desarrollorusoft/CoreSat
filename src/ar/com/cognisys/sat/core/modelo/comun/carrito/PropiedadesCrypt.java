package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class PropiedadesCrypt implements Serializable{
	
	private static final long serialVersionUID = -411225281073844656L;
	
	private Properties propiedades;
	
	public PropiedadesCrypt() {
		try {
			// se crea una instancia a la clase Properties
			propiedades = new Properties();
			
			// se leen el archivo .properties
			propiedades.load(getClass().getResourceAsStream("crypt.properties"));
		} catch (IOException ex) {
		}
	}
	
	public String getClaveEncriptacionXml() {
		return propiedades.getProperty("clave_encriptacion_xml");
	}
}