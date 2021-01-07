package ar.com.cognisys.sat.v2.persistencia.controlador.facade;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.consulta.AdministradorConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta.ConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;

public class FacadeConsultaArchivoDTO {

	public List<ConsultaArchivoDTO> recuperar(Integer idConsultaFormulario) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con  = AdministradorConexiones.obtenerConexion();

			return new AdministradorConsultaArchivoDTO( con  ).recuperar( idConsultaFormulario );
		} finally {
			AdministradorConexiones.cerrarConnection( con  );
		}
	}

	public void agregar(IConsultaArchivoDTO dto) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con  = AdministradorConexiones.obtenerConexion();

			new AdministradorConsultaArchivoDTO( con  ).crear( dto );
		} finally {
			AdministradorConexiones.cerrarConnection( con  );
		}
	}
	
	
	public Long insertar( IConsultaArchivoDTO dto ) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con  = AdministradorConexiones.obtenerConexion();
			return ( new AdministradorConsultaArchivoDTO( con  ) ).crearArchivo( dto );
		} finally {
			AdministradorConexiones.cerrarConnection( con  );
		}
	}

	public void elimnar(String aEliminar) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con  = AdministradorConexiones.obtenerConexion();

			new AdministradorConsultaArchivoDTO( con  ).eliminar( aEliminar );
		} finally {
			AdministradorConexiones.cerrarConnection( con  );
		}
	}

}