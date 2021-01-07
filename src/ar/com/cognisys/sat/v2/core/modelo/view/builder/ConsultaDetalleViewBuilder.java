package ar.com.cognisys.sat.v2.core.modelo.view.builder;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Consulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;
import ar.com.cognisys.sat.core.modelo.factory.consultas.FactoryArchivoRutaNombre;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.core.modelo.view.ConsultaDetalleView;
import ar.com.cognisys.sat.v2.core.modelo.view.MensajeView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaDetalleView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IMensajeView;

public class ConsultaDetalleViewBuilder {

	private Consulta consulta;
	
	protected ConsultaDetalleView consultaDetalle;

	public ConsultaDetalleViewBuilder(Consulta consulta) {
		this.consulta = consulta;
	}

	public void inicializar() {
		this.consultaDetalle = new ConsultaDetalleView();
	}

	public void cargarDatos() {
		this.consultaDetalle.setNombre( this.consulta.getNombre() );
		this.consultaDetalle.setApellido( this.consulta.getApellido() );
		this.consultaDetalle.setCorreo( this.consulta.getCorreo() );
		this.consultaDetalle.setTelefono( this.consulta.getTelefono() );
		this.consultaDetalle.setCuit( this.consulta.getCuit() );
		this.consultaDetalle.setNumero( String.valueOf( this.consulta.getId() ) );
	}

	public void agregarMensajes() {
		List<IMensajeView> listaMensajes = new ArrayList<IMensajeView>();
		for ( ConsultaAsociada consultaAsociada : this.consulta.getListaConsultasAsociadas() )
			listaMensajes.add( this.crearMensaje( consultaAsociada ) );
		
		this.consultaDetalle.setMensajes( listaMensajes.toArray( new IMensajeView[] { } ) );
			
	}

//	public void agregarNombresArchivo() {
//		List<IArchivoView> listaArchivos = new ArrayList<IArchivoView>();
//		for ( ArchivoConsulta archivoConsulta : this.consulta.getArchivos() )
//			listaArchivos.add( new ArchivoViewAdapter( archivoConsulta.getArchivo() ) );
//
//		this.consultaDetalle.setArchivos( listaArchivos.toArray( new IArchivoView[] { } ) );
//	}
//	
	private IMensajeView crearMensaje(ConsultaAsociada consultaAsociada) {
		return new MensajeView( 
				consultaAsociada.getFechaConsulta(), 
				consultaAsociada.getUsuario(), 
				consultaAsociada.getNuevaConsulta(), 
				consultaAsociada.isDelConsultante() );
	}


	public void construirArchivos() throws ErrorLecturaPropertiesException {
		

		List<IRutaNombreView> lista = new ArrayList<IRutaNombreView>();
		
		String encabezado = AdministradorProperties.getInstancia().getPropiedad( "ENCABEZADO_FTP_PUBLICO_DESCARGAS" );
		
		for(IRutaNombreView i : this.consulta.getListaArchivos() ){
			
			lista.add( FactoryArchivoRutaNombre.generar( i.getNombre() , encabezado + i.getRuta() ) );
		}
		
		this.getConsultaDetalle().setArchivos( lista.toArray( new IRutaNombreView[] {} ) );
	}

	public IConsultaDetalleView getConsultaDetalle() {
		return this.consultaDetalle;
	}


}
