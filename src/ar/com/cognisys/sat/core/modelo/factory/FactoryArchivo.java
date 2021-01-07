package ar.com.cognisys.sat.core.modelo.factory;

import java.io.File;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;

public class FactoryArchivo {

	public static Archivo generarInstancia() {
		
		Archivo archivo = new Archivo();
		
		return archivo;
	}
	
	public static Archivo generarInstancia(String nombre, String tipoContenido, byte[] data, 
										   String tempNombreUrl, String tempExtensionUrl, File temp) {
		
		Archivo archivo = generarInstancia();
		archivo.setNombre(nombre);
		archivo.setTipoContenido(tipoContenido);
		archivo.setData(data);
		archivo.setTempNombreUrl(tempNombreUrl);
		archivo.setTempExtensionUrl(tempExtensionUrl);
		archivo.setTemp(temp);
		
		return archivo;
	}
	
	public static Archivo generarInstancia(Long id, String nombre, String tipoContenido, String ruta) {
		
		Archivo archivo = generarInstancia();
		archivo.setId(id);
		archivo.setNombre(nombre);
		archivo.setTipoContenido(tipoContenido);
		archivo.setFilePath(ruta);		
		
		return archivo;
	}
}