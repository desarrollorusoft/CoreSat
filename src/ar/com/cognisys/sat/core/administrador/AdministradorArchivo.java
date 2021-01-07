package ar.com.cognisys.sat.core.administrador;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ar.com.cognisys.filemanager.controlador.FTPFileManager;
import ar.com.cognisys.filemanager.excepciones.FileManagerCommonException;
import ar.com.cognisys.filemanager.factory.FactoryArchivoFTP;
import ar.com.cognisys.filemanager.modelo.ArchivoFTP;
import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.excepcion.ArchivoMuyGrandeException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.FactoryArchivo;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;

public class AdministradorArchivo {

	public static Archivo crearArchivo(FileUploadEvent event) throws ExcepcionControladaAlerta, IOException {
		
		if (event.getFile().getContents().length > 10000000) {
			throw new ArchivoMuyGrandeException();
		}
		
		String filename = FilenameUtils.getName(event.getFile().getFileName());
		String basename = FilenameUtils.getBaseName(filename) + "_" + ( new Date() ).getTime() ;
		String extension = "." + FilenameUtils.getExtension(filename);
		
		File temp = File.createTempFile(basename, extension);
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
	    bw.write(new String(event.getFile().getContents()));
	    bw.close();
		
		return FactoryArchivo.generarInstancia(event.getFile().getFileName(),
											   event.getFile().getContentType(),
											   event.getFile().getContents(),
											   basename,
											   extension,
											   temp);
	}
	
	public static StreamedContent getFileDownload(Archivo archivos) throws IOException, ErrorLecturaPropertiesException {
		
		String path = archivos.getFilePath().replaceAll(" ", "%20");
		
		URL url = new URL( AdministradorProperties.getInstancia().getPropiedad("ENCABEZADO_FTP_PUBLICO_DESCARGAS") + path );
		InputStream is = url.openStream();
		return new DefaultStreamedContent( is, archivos.getTipoContenido(), archivos.getNombre() );
	}
	
	public static StreamedContent getFileDownloadProveedores(Archivo archivo) {
		
		return new DefaultStreamedContent(new ByteArrayInputStream(archivo.getData()), archivo.getTipoContenido(), archivo.getNombre());
	}

	public static void guardarEnFTP(String directorio, String nombreArchivo, InputStream archivo) throws ExcepcionControladaError, ErrorLecturaPropertiesException {
		
		try {
			ArchivoFTP arch = FactoryArchivoFTP.generar( nombreArchivo, archivo );
			
			FTPFileManager.guardarArchivo( AdministradorProperties.getInstancia().getPropiedad( "HOST_FTP" ), 
										   Integer.valueOf( AdministradorProperties.getInstancia().getPropiedad( "PUERTO_FTP" ) ), 
										   AdministradorProperties.getInstancia().getPropiedad( "USUARIO_FTP" ), 
										   AdministradorProperties.getInstancia().getPropiedad( "PASS_FTP" ) , 
										   AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_PUBLICO" ) + directorio, 
										   arch  );
		} catch ( FileManagerCommonException e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
		
	}
}