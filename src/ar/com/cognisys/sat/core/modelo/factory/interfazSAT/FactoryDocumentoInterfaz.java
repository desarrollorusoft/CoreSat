package ar.com.cognisys.sat.core.modelo.factory.interfazSAT;

import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.interfazSAT.DocumentoInterfaz;

public class FactoryDocumentoInterfaz {

	public static DocumentoInterfaz generarInstancia() {
		
		DocumentoInterfaz documento = new DocumentoInterfaz();
		
		return documento;
	}
	
	public static DocumentoInterfaz generarInstancia(Long id, String nombre, String descripcion, Date fechaAlta, 
													 Date fechaActualizacion, String categoria, List<Archivo> listaArchivos) {
		
		DocumentoInterfaz documento = generarInstancia();
		documento.setId(id);
		documento.setNombre(nombre);
		documento.setDescripcion(descripcion);
		documento.setFechaAlta(fechaAlta);
		documento.setFechaActualizacion(fechaActualizacion);
		documento.setCategoria(categoria);
		documento.setListaArchivos(listaArchivos);
		
		return documento;
	}
}