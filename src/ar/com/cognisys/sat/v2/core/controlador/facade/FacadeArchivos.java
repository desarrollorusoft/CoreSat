package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorConsultaArchivo;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.bo.ConsultaArchivo;
import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivo;
import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivoRecovered;
import ar.com.cognisys.sat.v2.core.modelo.exception.CoreSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorEliminadoDeArchivosException;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.core.modelo.exception.GetFileDataException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IArchivoView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaArchivoView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.INumeroConsultaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.RutaNombreView;

public class FacadeArchivos {

	/**
	 * Método utilizado para recuperar el archivo a través del nombre y la consulta.
	 * 
	 * @param archivoConsultaView
	 * @return archivo
	 * @throws PersistenceSATException 
	 */
	@Deprecated
	public IRutaNombreView[] recuperar(INumeroConsultaView numeroConsultaView) throws PersistenceSATException {
		
		if ( numeroConsultaView == null || numeroConsultaView.getNumeroConsulta() == null )
			return null;
		
		List<IRutaNombreView> lista = new ArrayList<IRutaNombreView>();
		
		try {
			Integer numeroConsulta = Integer.valueOf( numeroConsultaView.getNumeroConsulta() );
			
			List<IConsultaArchivoRecovered> listaBO = new AdministradorConsultaArchivo().recuperar( numeroConsulta );
			for (IConsultaArchivoRecovered consultaArchivo : listaBO)
				lista.add( new RutaNombreView( consultaArchivo.getNombre(), consultaArchivo.getRuta() ) );
				
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar los archivos", e );
		}
		
		return lista.toArray( new IRutaNombreView[] {} );
		
	}

	/**
	 * Método utilizado para cargar un archivo en una consulta.
	 * 
	 * @param consultaArchivo
	 * @throws PersistenceSATException 
	 * @throws CoreSATException 
	 */
	public IRutaNombreView cargar(IConsultaArchivoView consultaArchivoView) throws PersistenceSATException, CoreSATException {
		
		if ( this.hayParametrosVacios( consultaArchivoView ) )
			return null;
		
		IConsultaArchivo consultaArchivo = this.crearConsultaArchivo( consultaArchivoView );

		try {
			new AdministradorConsultaArchivo().crear( consultaArchivo );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible crear el archivo", e );
		}
		
		return new RutaNombreView( consultaArchivo.getNombre(),crearRutaCompleta( consultaArchivo ) );
	}

	private String crearRutaCompleta(IConsultaArchivo consultaArchivo) throws ErrorLecturaPropertiesException {
		
		return AdministradorProperties.getInstancia().getPropiedad( "ENCABEZADO_FTP_PUBLICO_DESCARGAS" ) + consultaArchivo.getRuta() ;
	}

	private IConsultaArchivo crearConsultaArchivo(IConsultaArchivoView view) throws CoreSATException {
		ConsultaArchivo consultaArchivo = new ConsultaArchivo();
		
		consultaArchivo.setNumeroConsulta( Integer.valueOf( view.getNumeroConsulta() ) );
		consultaArchivo.setNombre( view.getArchivo().getNombre() );
		consultaArchivo.setTipo( view.getArchivo().getTipo() );
		
		consultaArchivo.setDirectorio( ObtenerDirectorio( consultaArchivo.getNumeroConsulta() ) );
		
		byte[] data;
		
		try {
			data = Base64.decodeBase64( view.getArchivo().getData().getBytes( CharEncoding.UTF_8 ) );
		} catch ( UnsupportedEncodingException e ) {
			throw new GetFileDataException();
		}
		
		consultaArchivo.setData( data );
		
		consultaArchivo.generarNombre();
		
		return consultaArchivo;
	}

	private String ObtenerDirectorio( Integer numero ) throws CoreSATException {

		String path = AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_CONSULTA_FTP" );
		path+= "/" + numero.toString() + "/";
		return path;
	}

	public void eliminar(IRutaNombreView rutaNombre) throws CoreSATException {
		try {
			new AdministradorConsultaArchivo().eliminar( rutaNombre );
		} catch ( ExcepcionControladaError e ) {
			throw new ErrorEliminadoDeArchivosException("Ocurrió un error al eliminar el archivo", e);
		}
	}
	private boolean hayParametrosVacios(IConsultaArchivoView consultaArchivo) {
		if ( consultaArchivo == null )
			return false;
		
		if ( consultaArchivo.getArchivo() == null 
				|| consultaArchivo.getNumeroConsulta() == null
				|| consultaArchivo.getNumeroConsulta().isEmpty() )
			return false;

		IArchivoView archivo = consultaArchivo.getArchivo();
		
		return  archivo.getData() == null
				|| archivo.getNombre() == null
				|| archivo.getNombre().isEmpty();
	}


}
