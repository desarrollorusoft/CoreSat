package ar.com.cognisys.sat.v2.persistencia.controlador.facade;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.alerta.AdministradorAlertaGeneralDTO;
import ar.com.cognisys.sat.v2.persistencia.controlador.administrador.alerta.AdministradorAlertaParticularDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class FacadeAlertasDTO {



	public List<IAlertaGeneralDTO> recuperar() throws ExcepcionControladaError {
        Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return new AdministradorAlertaGeneralDTO( con ).recuperar();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public List<AlertaUsuarioDTO> recuperar(String cuit) throws ExcepcionControladaError {
        Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return new AdministradorAlertaParticularDTO( con ).recuperar( cuit );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	public List<AlertaUsuarioDTO> recuperarDelDia() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new AdministradorAlertaParticularDTO( con ).recuperarDelDia();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public void agregar(AlertaUsuarioDTO alertaDTO) throws ExcepcionControladaError {
        Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			new AdministradorAlertaParticularDTO( con ).crear( alertaDTO );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public void borrar(AlertaUsuarioDTO alertaDTO) throws ExcepcionControladaError {
        Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			new AdministradorAlertaParticularDTO( con ).eliminar( alertaDTO );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

    public void notificarDelDia() throws ExcepcionControladaError {
        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();

            new AdministradorAlertaParticularDTO( con ).actualizarDelDia();
        } finally {
            AdministradorConexiones.cerrarConnection( con );
        }
    }
}