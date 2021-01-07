package ar.com.cognisys.sat.core.modelo.factory.consultas;

import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.FormularioConsulta;

public class FactoryFormularioConsultas {

	public static FormularioConsulta generarInstancia(){
		
		FormularioConsulta f = new FormularioConsulta();
		f.setArchivos(new ArrayList<ArchivoConsulta>());
		f.setTelefonoSeleccionado( true );

		return f;
	}

	public static FormularioConsulta generarInstancia(String nombre, String apellido, String correo, String cuit, String telefono, String telefono2) {
		FormularioConsulta f = generarInstancia();
		
		f.setNombre( nombre );
		f.setApellido( apellido );
		f.setCorreo( correo );
		f.setCuit( cuit );
		f.setTelefono( telefono );
		f.setTelefono2( telefono2 );
		
		return f;
	}
	
}
