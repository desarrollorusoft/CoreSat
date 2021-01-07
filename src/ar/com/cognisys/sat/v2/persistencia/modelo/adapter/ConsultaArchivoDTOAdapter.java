package ar.com.cognisys.sat.v2.persistencia.modelo.adapter;

import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivo;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;

public class ConsultaArchivoDTOAdapter implements IConsultaArchivoDTO {

	private IConsultaArchivo consultaArchivo;

	public ConsultaArchivoDTOAdapter(IConsultaArchivo consultaArchivo) {
		this.consultaArchivo = consultaArchivo;
	}

	@Override
	public Integer getIdConsultaFormulario() {
		return this.consultaArchivo.getNumeroConsulta();
	}

	@Override
	public String getNombre() {
		return this.consultaArchivo.getNombre();
	}

	@Override
	public String getTipoContenido() {
		return this.consultaArchivo.getTipo();
	}

	@Override
	public Integer getArchivoConsultante() {
		return 1;
	}

	@Override
	public String getRuta() {
		return this.consultaArchivo.getRuta();
	}

}
