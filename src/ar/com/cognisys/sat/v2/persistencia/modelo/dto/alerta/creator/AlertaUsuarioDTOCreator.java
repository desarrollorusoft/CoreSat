package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;

public class AlertaUsuarioDTOCreator extends AlertaDTOCreator<AlertaUsuarioDTOCreator, AlertaUsuarioDTO> {

	@Override
	protected AlertaUsuarioDTO inicializar() {
		return new AlertaUsuarioDTO();
	}
	
	public AlertaUsuarioDTOCreator idUsuario(Integer idUsuario) {
		this.alertaDTO.setIdUsuario( idUsuario );
		
		return this;
	}
	
	public AlertaUsuarioDTOCreator cuit(String cuit) {
		this.alertaDTO.setCuit( cuit );
		
		return this;
	}

	public AlertaUsuarioDTOCreator fecha(Date fecha) {
		this.alertaDTO.setFecha( fecha );

		return this;
	}

	public AlertaUsuarioDTOCreator notificada(boolean notificada) {
		this.alertaDTO.setNotificada(notificada);
		return this;
	}

	@Override
	public AlertaUsuarioDTO create() {
		return super.alertaDTO;
	}
}
