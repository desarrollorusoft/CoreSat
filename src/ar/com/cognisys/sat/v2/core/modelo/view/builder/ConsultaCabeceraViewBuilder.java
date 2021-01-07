package ar.com.cognisys.sat.v2.core.modelo.view.builder;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Consulta;
import ar.com.cognisys.sat.v2.core.modelo.view.ConsultaCabeceraView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaCabeceraView;

public class ConsultaCabeceraViewBuilder {

	private Consulta consulta;
	
	protected ConsultaCabeceraView consultaCabecera;

	public ConsultaCabeceraViewBuilder(Consulta consulta) {
		this.consulta = consulta;
	}

	public void inicializar() {
		this.consultaCabecera = new ConsultaCabeceraView();
	}

	public void cargarDatos() {
		this.consultaCabecera.setCategoria( this.consulta.getCategoria().getNombre() );
		this.consultaCabecera.setTipo( this.consulta.getTipoConsulta() );
		this.consultaCabecera.setEstado( this.consulta.getEstado().getDescripcion() );
		this.consultaCabecera.setCodigoCuenta( this.consulta.getDato() );
		this.consultaCabecera.setFecha( this.consulta.getFechaActualizacion() );
		this.consultaCabecera.setNumero( String.valueOf( this.consulta.getId() ) );
		
	}

	public IConsultaCabeceraView getConsultaCabecera() {
		return this.consultaCabecera;
	}
}
