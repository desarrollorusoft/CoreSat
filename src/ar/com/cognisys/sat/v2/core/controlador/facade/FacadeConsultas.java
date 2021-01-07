package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorFormularioConsultas;
import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeSeguimientoConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Caracter;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Consulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;
import ar.com.cognisys.sat.core.modelo.comun.consultas.FormularioConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.TipoConsulta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.director.DirectorConsultaCabeceraView;
import ar.com.cognisys.sat.v2.core.controlador.director.DirectorConsultaDetalleView;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.WarningSATException;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.ConsultaCabeceraViewBuilder;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.ConsultaDetalleViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuitView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IUsuarioCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaCabeceraView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaCompletaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaDetalleView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaMensajeView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaResumenView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IMensajeView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.ICuitCorreoView;

public class FacadeConsultas {

	/**
	 * Método utilizado para recuperar las cabeceras de las consultas
	 * asociadas al cuit de un usuario.
	 * 
	 * @param cuit
	 * @return consultasCabecera
	 * @throws PersistenceSATException 
	 */
	public IConsultaCabeceraView[] recuperarPorUsuario(ICuitView cuit) throws PersistenceSATException {
		List<IConsultaCabeceraView> lista = new ArrayList<IConsultaCabeceraView>();
		
		try {
			List<Consulta> listaConsultas = AdministradorFormularioConsultas.buscarConsultasPorCuit( cuit.getCuit() );
			for (Consulta consulta : listaConsultas)
				lista.add( this.construirCabecera( consulta ) );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar la consulta", e );
		}

		return lista.toArray( new IConsultaCabeceraView[] {} );
	}
	
	public IConsultaCabeceraView[] recuperarPorCorreo(ICuitCorreoView cuitCorreo) throws PersistenceSATException {
		
		List<IConsultaCabeceraView> lista = new ArrayList<IConsultaCabeceraView>();
		
		try {
			List<Consulta> listaConsultas = AdministradorFormularioConsultas.buscarConsultasPorCorreo( cuitCorreo.getCorreo() );
			for (Consulta consulta : listaConsultas)
				lista.add( this.construirCabecera( consulta ) );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar la consulta", e );
		}

		return lista.toArray( new IConsultaCabeceraView[] {} );
	}

	/**
	 * Método utilizado para recuperar las cabeceras de las consultas
	 * asociadas a una cuenta y a un usuario.
	 * 
	 * @param usuarioCuenta
	 * @return consultasCabecera
	 * @throws PersistenceSATException 
	 */
	public IConsultaCabeceraView[] recuperarPorUsuarioCuenta(IUsuarioCuentaView usuarioCuenta) throws PersistenceSATException {
		List<IConsultaCabeceraView> lista = new ArrayList<IConsultaCabeceraView>();
		
		try {
			List<Consulta> listaConsultas = AdministradorFormularioConsultas.buscarConsultasPorCuitCuenta( usuarioCuenta.getNombreUsuario(), usuarioCuenta.getCuenta().getCodigo() );
			
			for (Consulta consulta : listaConsultas)
				lista.add( this.construirCabecera( consulta ) );
			
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar la consulta", e );
		}

		return lista.toArray( new IConsultaCabeceraView[] {} );
	}
	
	/**
	 * Método utilizado para recuperar el detalle de la consulta a través
	 * de un número de consulta.
	 * 
	 * @param consultaResumen
	 * @return consultaDetalle
	 * @throws PersistenceSATException 
	 * @throws ErrorLecturaPropertiesException 
	 */
	public IConsultaDetalleView recuperarPorConsulta(IConsultaResumenView consultaResumen) throws PersistenceSATException, ErrorLecturaPropertiesException {
		if ( consultaResumen == null || consultaResumen.getNumero() == null || consultaResumen.getNumero().isEmpty() )
			return null;
		
		IConsultaDetalleView consultaDetalle = null;
		
		try {
			Integer numeroConsulta = Integer.valueOf( consultaResumen.getNumero() );
			Consulta consulta = AdministradorFormularioConsultas.buscarConsulta( numeroConsulta );
			
			consultaDetalle = this.construirDetalle( consulta );
			
		} catch ( NumberFormatException e ) {
			e.printStackTrace();
			return null;
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar la consulta", e );
		}
		
		return consultaDetalle;
	}
	

	/**
	 * Método utilizado para crear una nueva consulta.
	 * 
	 * @param consultaCompleta
	 * @throws PersistenceSATException 
	 */
	public void crear(IConsultaCompletaView consultaCompleta) throws PersistenceSATException {
		try {
			
			Categoria categoria = AdministradorFormularioConsultas.obtenerCategoria( consultaCompleta.getCategoria() );
			TipoConsulta tipoConsulta = AdministradorFormularioConsultas.obtenerTipoConsulta( consultaCompleta.getTipo() );
			Caracter caracter = AdministradorFormularioConsultas.obtenerCaracter( consultaCompleta.getCaracter() );
			
			
			FormularioConsulta formulario = this.crearFormularioConsulta( consultaCompleta, categoria, tipoConsulta, caracter );
			
			AdministradorFormularioConsultas.registrarConsulta( formulario );
			
			String numero = String.valueOf( formulario.getId() );
			consultaCompleta.setNumero( numero );
			
			for (IMensajeView mensaje : consultaCompleta.getMensajes())
				AdministradorFormularioConsultas.enviarNuevaConsulta( this.crearConsultaAsociada( mensaje, numero ) );
			
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible guardar la consulta", e );
		}
		
	}
	
	private ConsultaAsociada crearConsultaAsociada(IMensajeView mensaje, String numero) {
		ConsultaAsociada consultaAsociada = new ConsultaAsociada();
		
		consultaAsociada.setDelConsultante( true );
		Integer numeroConsulta = Integer.valueOf( numero );
		consultaAsociada.setIdConsultaAsociada( numeroConsulta );
		
		consultaAsociada.setFechaConsulta( mensaje.getFecha() );
		consultaAsociada.setNuevaConsulta( mensaje.getTexto() );
		consultaAsociada.setUsuario( mensaje.getRemitente() );
		
		return consultaAsociada;
	}

	private FormularioConsulta crearFormularioConsulta(IConsultaCompletaView consultaCompleta, Categoria categoria, TipoConsulta tipoConsulta, Caracter caracter) throws ExcepcionControladaError {
		
		FormularioConsulta formularioConsulta = new FormularioConsulta();
		
		formularioConsulta.setIdCategoria( categoria.getId() );
		formularioConsulta.setIdCaracter( caracter.getId() );
		formularioConsulta.setIdTipoConsulta( tipoConsulta.getId() );
		formularioConsulta.setIdDato( categoria.getDato().getId() );
		formularioConsulta.setDato( consultaCompleta.getCodigoCuenta() );
		
		formularioConsulta.setCuit( consultaCompleta.getCuit() );
		formularioConsulta.setTelefono( consultaCompleta.getTelefono() );
		formularioConsulta.setTelefono2( consultaCompleta.getTelefono2() );
		formularioConsulta.setCorreo( consultaCompleta.getCorreo() );
		formularioConsulta.setNombre( consultaCompleta.getNombre() );
		formularioConsulta.setApellido( consultaCompleta.getApellido() );
		
		
//		List<ArchivoConsulta> listaArchivos = new ArrayList<ArchivoConsulta>();
//		
//		if (consultaCompleta.getArchivos() != null) {
//			for ( IArchivoView archivoView : consultaCompleta.getArchivos() )
//				listaArchivos.add( this.crearArchivoConsulta( archivoView ) );
//		}
//
//		formularioConsulta.setArchivos( listaArchivos );
		
		return formularioConsulta;
	}

//	private ArchivoConsulta crearArchivoConsulta(IArchivoView archivoView) throws ExcepcionControladaError {
//
//		Archivo archivo = new Archivo();
//		
//		archivo.setTipoContenido( archivoView.getTipo() );
//		archivo.setNombre( archivoView.getNombre() );
//		try {
//			archivo.setData( archivoView.getData().getBytes( CharEncoding.UTF_8 ) );
//		} catch ( UnsupportedEncodingException e ) {
//			throw new ExcepcionControladaError( "No se puede obtener el archivo subido", e );
//		}
//		
//		ArchivoConsulta archivoConsulta = new ArchivoConsulta();
//		
//		archivoConsulta.setArchivo( archivo );
//		archivoConsulta.setDelConsultante( true );
//		
//		return archivoConsulta;
//	}

	/**
	 * Método utilizado para agregar un mensaje en una consulta específica.
	 * 
	 * @param consultaMensaje
	 * @throws PersistenceSATException 
	 */
	public void agregarMensaje(IConsultaMensajeView consultaMensaje) throws PersistenceSATException {
		
		try {
			ConsultaAsociada consultaAsociada = this.crearConsultaAsociada( consultaMensaje );
			
			AdministradorFormularioConsultas.enviarNuevaConsulta( consultaAsociada );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible agregar el mensaje nuevo", e );
		}
		
	}

	private ConsultaAsociada crearConsultaAsociada(IConsultaMensajeView consultaMensaje) {
		ConsultaAsociada consultaAsociada = new ConsultaAsociada();
		
		consultaAsociada.setDelConsultante( true );
		Integer numeroConsulta = Integer.valueOf( consultaMensaje.getNumeroConsulta() );
		consultaAsociada.setIdConsultaAsociada( numeroConsulta );
		
		IMensajeView mensaje = consultaMensaje.getMensaje();
		consultaAsociada.setFechaConsulta( mensaje.getFecha() );
		consultaAsociada.setNuevaConsulta( mensaje.getTexto() );
		consultaAsociada.setUsuario( mensaje.getRemitente() );
		
		return consultaAsociada;
	}

	private IConsultaCabeceraView construirCabecera(Consulta consulta) {
		ConsultaCabeceraViewBuilder builder = new ConsultaCabeceraViewBuilder( consulta );
		DirectorConsultaCabeceraView director = new DirectorConsultaCabeceraView( builder );
		
		director.construir();
		
		return director.getConsultaCabecera();
	}
	
	private IConsultaDetalleView construirDetalle(Consulta consulta) throws ErrorLecturaPropertiesException {

		ConsultaDetalleViewBuilder builder = new ConsultaDetalleViewBuilder( consulta );

		DirectorConsultaDetalleView director = new DirectorConsultaDetalleView( builder );
		director.construir();
		
		return director.getConsultaDetalle();
	}

	public void crearInformando(IConsultaCompletaView consulta) throws PersistenceSATException, WarningSATException {
		this.crear( consulta );
		
		try {
			AdministradorMails.enviar( new MensajeSeguimientoConsulta( consulta.getCorreo() ) );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new WarningSATException( e.getMessage() );
		}
	}

}
