package ar.com.cognisys.sat.v2.persistencia.controlador.administrador.alerta;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.generics.persistencia.AdministradorDTO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.alerta.AlertaUsuarioDAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.notificacion.NotificacionDao;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.notificacion.NotificacionDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.notificacion.Notificacion;

public class AdministradorAlertaParticularDTO extends AdministradorDTO {

	public AdministradorAlertaParticularDTO(Connection con) {
		super( con );
	}

	public List<AlertaUsuarioDTO> recuperar(String cuit) throws ErrorEnBaseException {
		return new AlertaUsuarioDAO( this.connection ).recuperar( cuit );
	}
	public List<AlertaUsuarioDTO> recuperarDelDia() throws ErrorEnBaseException {
		return new AlertaUsuarioDAO( this.connection ).recuperarDelDia( );
	}

	public void crear(AlertaUsuarioDTO alertaDTO) throws ErrorEnBaseException {
		AlertaUsuarioDAO dao = new AlertaUsuarioDAO( this.connection );
		
		dao.crearAlerta( alertaDTO );
		dao.crearAlertaUsuario( alertaDTO );
	}

	public void eliminar(AlertaUsuarioDTO alertaDTO) throws ErrorEnBaseException {
		AlertaUsuarioDAO dao = new AlertaUsuarioDAO( this.connection );
        NotificacionDao daoNot = new NotificacionDao(this.connection);
		AlertaUsuarioDTO alertaPersistidaDTO = dao.recuperar( alertaDTO.getCuit(), alertaDTO.getTitulo(), alertaDTO.getDescripcion(), alertaDTO.getFecha() );

        daoNot.eliminarPorAlerta(alertaPersistidaDTO.getId());
		dao.eliminarAlertaUsuario( alertaPersistidaDTO );
		dao.eliminarAlerta( alertaPersistidaDTO );
		
	}

    public void actualizarDelDia() throws ErrorEnBaseException {
        new AlertaUsuarioDAO( this.connection ).actualizarDelDia();
    }
}
