package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class URLsPago implements Serializable{
	
	private static final long serialVersionUID = -411225281073844656L;
	
	private Properties propiedades;
	
	public URLsPago() {
		try {
			// se crea una instancia a la clase Properties
			propiedades = new Properties();
			
			// se leen el archivo .properties
			propiedades.load(getClass().getResourceAsStream("urls.properties"));
		} catch (IOException ex) {
		}
	}
	
	public String getURL_INTERBANKING() {
		return propiedades.getProperty("URL_INTERBANKING");
	}

	public String getURL_PMC() {
		return propiedades.getProperty("URL_PMC");
	}

	public String getURL_TARJETA_CREDITO() {
		return propiedades.getProperty("URL_TARJETA_CREDITO");
	}
}