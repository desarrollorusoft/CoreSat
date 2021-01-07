package ar.com.cognisys.sat.v2.persistencia.controlador.administrador.consulta;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.consulta.ConsultaArchivoDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta.ConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;

public class AdministradorConsultaArchivoDTO extends AdministradorDTO {

	public AdministradorConsultaArchivoDTO(Connection connection) {
		super( connection );
	}
	
	public List<ConsultaArchivoDTO> recuperar(Integer idConsultaFormulario) throws ErrorEnBaseException {
		return new ConsultaArchivoDAO( this.connection ).recuperar( idConsultaFormulario );
	}

	public void crear(IConsultaArchivoDTO dto) throws ErrorEnBaseException {
		new ConsultaArchivoDAO( this.connection ).crear( dto );
	}

	public void eliminar(String ruta) throws ErrorEnBaseException {
		new ConsultaArchivoDAO( this.connection ).eliminar( ruta );
	}

	public Long crearArchivo(IConsultaArchivoDTO dto) throws ErrorEnBaseException {
		return ( new ConsultaArchivoDAO( this.connection ) ).crearArchivo( dto );
	}


}
