package ar.com.cognisys.sat.v2.core.controlador.administrador;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.filemanager.controlador.FTPFileManager;
import ar.com.cognisys.filemanager.excepciones.FileManagerCommonException;
import ar.com.cognisys.filemanager.modelo.ArchivoFTP;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.adapter.ConsultaArchivoRecoveredAdapter;
import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivo;
import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivoRecovered;
import ar.com.cognisys.sat.v2.core.modelo.exception.CoreSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.persistencia.controlador.facade.FacadeConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.adapter.ConsultaArchivoDTOAdapter;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta.ConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

public class AdministradorConsultaArchivo {

	public void crear(IConsultaArchivo consultaArchivo) throws ExcepcionControladaError, ErrorLecturaPropertiesException {
		
		
		ArchivoFTP a = new ArchivoFTP();
		a.setArchivo( new ByteArrayInputStream( consultaArchivo.getData() ) );
		a.setNombre( consultaArchivo.getNombreGenerado() );
		
		try {
			FTPFileManager.guardarArchivo( AdministradorProperties.getInstancia().getPropiedad( "HOST_FTP" ), 
										   Integer.valueOf( AdministradorProperties.getInstancia().getPropiedad( "PUERTO_FTP" ) ), 
										   AdministradorProperties.getInstancia().getPropiedad( "USUARIO_FTP" ), 
										   AdministradorProperties.getInstancia().getPropiedad( "PASS_FTP" ) , 
										   AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_PUBLICO" ) + consultaArchivo.getDirectorio(), 
										   a );
			
			IConsultaArchivoDTO dto = new ConsultaArchivoDTOAdapter( consultaArchivo );
			
			new FacadeConsultaArchivoDTO().agregar( dto );
		} catch ( FileManagerCommonException e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
		
	}
	
	public Long crearArchivo(IConsultaArchivo consultaArchivo) throws ExcepcionControladaError, ErrorLecturaPropertiesException {
		
		
		ArchivoFTP a = new ArchivoFTP();
		a.setArchivo( new ByteArrayInputStream( consultaArchivo.getData() ) );
		a.setNombre( consultaArchivo.getNombreGenerado() );
		
		try {
			FTPFileManager.guardarArchivo( AdministradorProperties.getInstancia().getPropiedad( "HOST_FTP" ), 
										   Integer.valueOf( AdministradorProperties.getInstancia().getPropiedad( "PUERTO_FTP" ) ), 
										   AdministradorProperties.getInstancia().getPropiedad( "USUARIO_FTP" ), 
										   AdministradorProperties.getInstancia().getPropiedad( "PASS_FTP" ) , 
										   AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_PUBLICO" ) + consultaArchivo.getDirectorio(), 
										   a );
			
			IConsultaArchivoDTO dto = new ConsultaArchivoDTOAdapter( consultaArchivo );
			
			return new FacadeConsultaArchivoDTO().insertar( dto );
		} catch ( FileManagerCommonException e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
		
	}

	public void eliminar( IRutaNombreView aEliminar ) throws ExcepcionControladaError{
		try {
			
			String directorio = extraerDirectorio( aEliminar.getRuta() );
			
			FTPFileManager.eliminarArchivo( AdministradorProperties.getInstancia().getPropiedad( "HOST_FTP" ), 
										   Integer.valueOf( AdministradorProperties.getInstancia().getPropiedad( "PUERTO_FTP" ) ), 
										   AdministradorProperties.getInstancia().getPropiedad( "USUARIO_FTP" ), 
										   AdministradorProperties.getInstancia().getPropiedad( "PASS_FTP" ) , 
										   "public_html/" + directorio );
			
			
			new FacadeConsultaArchivoDTO().elimnar( directorio );
		} catch ( FileManagerCommonException e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} catch ( CoreSATException e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		}
	}
	
	private String extraerDirectorio(String ruta) throws ErrorLecturaPropertiesException {
		
		String encabezado = AdministradorProperties.getInstancia().getPropiedad( "ENCABEZADO_FTP_PUBLICO_DESCARGAS" );
		
		return ruta.substring( encabezado.length() );
	}

	@Deprecated
	public List<IConsultaArchivoRecovered> recuperar(Integer numeroConsulta) throws ExcepcionControladaError {
		
		List<IConsultaArchivoRecovered> lista = new ArrayList<IConsultaArchivoRecovered>();
		
		List<ConsultaArchivoDTO> listaDTO = new FacadeConsultaArchivoDTO().recuperar( numeroConsulta );
		for (ConsultaArchivoDTO dto : listaDTO)
			lista.add( new ConsultaArchivoRecoveredAdapter( dto ) );
			
		return lista;
	}
}
