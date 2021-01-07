package ar.com.cognisys.sat.core.modelo.factory;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;

public class FactoryArchivoConsulta {

	public static ArchivoConsulta generarInstancia() {
		
		ArchivoConsulta archivo = new ArchivoConsulta();
		
		return archivo;
	}
	
	public static ArchivoConsulta generarInstancia(Archivo a, boolean delConsultante) {
		
		ArchivoConsulta archivo = generarInstancia();
		archivo.setArchivo(a);
		archivo.setDelConsultante(delConsultante); 
		
		return archivo;
	}
}