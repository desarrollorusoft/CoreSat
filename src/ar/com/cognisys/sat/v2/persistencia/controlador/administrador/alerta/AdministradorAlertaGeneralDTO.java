package ar.com.cognisys.sat.v2.persistencia.controlador.administrador.alerta;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta.AlertaPublicitariaDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta.AlertaVencimientoDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AdministradorAlertaGeneralDTO extends AdministradorDTO {

	public AdministradorAlertaGeneralDTO(Connection con) {
		super( con );
	}

	public List<IAlertaGeneralDTO> recuperar() throws ErrorEnBaseException {
		List<IAlertaGeneralDTO> lista = new ArrayList<IAlertaGeneralDTO>();
		
		lista.addAll( new AlertaPublicitariaDAO( this.connection ).recuperar() );
		lista.addAll( new AlertaVencimientoDAO( this.connection ).recuperar() );
		
		return lista;
	}

}
