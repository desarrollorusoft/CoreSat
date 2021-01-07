package ar.com.cognisys.sat.v2.core.modelo.adapter;

import ar.com.cognisys.sat.v2.core.modelo.bo.IConsultaArchivoRecovered;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta.ConsultaArchivoDTO;

public class ConsultaArchivoRecoveredAdapter implements IConsultaArchivoRecovered {

	private ConsultaArchivoDTO dto;

	public ConsultaArchivoRecoveredAdapter(ConsultaArchivoDTO dto) {
		this.dto = dto;
	}

	@Override
	public String getTipo() {
		return this.dto.getTipoContenido();
	}

	@Override
	public String getNombre() {
		return this.dto.getNombre();
	}

	@Override
	public String getRuta() {
		return this.dto.getRuta();
	}


}
