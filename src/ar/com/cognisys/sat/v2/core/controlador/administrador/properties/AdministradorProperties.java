package ar.com.cognisys.sat.v2.core.controlador.administrador.properties;

import java.util.Properties;

import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;


public class AdministradorProperties {

	private Properties properties;
	private static AdministradorProperties instancia;
	
	
	public String getPropiedad( String clave ){
		return this.properties.getProperty( clave );
	}
	
	public static AdministradorProperties getInstancia() throws ErrorLecturaPropertiesException {
		
		if( instancia == null ){
			iniciarInstancia();
		}
		
		return instancia;
	}


	private static void iniciarInstancia() throws ErrorLecturaPropertiesException {
		instancia = new AdministradorProperties();
	}



	private AdministradorProperties() throws ErrorLecturaPropertiesException {
		try {
			this.properties = new Properties();
			this.properties.load( this.getClass().getResourceAsStream( "parametros.properties" ) );
		} catch ( Exception e ) {
			throw new ErrorLecturaPropertiesException( e );
		}
	}
	
}
