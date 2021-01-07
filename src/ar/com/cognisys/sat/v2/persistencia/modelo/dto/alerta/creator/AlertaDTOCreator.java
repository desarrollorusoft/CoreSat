package ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator;

import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaDTO;

public abstract class AlertaDTOCreator<CREATOR extends AlertaDTOCreator<CREATOR, ALERTA>, ALERTA extends AlertaDTO> {

	protected ALERTA alertaDTO;

	public AlertaDTOCreator() {
		this.alertaDTO = this.inicializar();
	}

	protected abstract ALERTA inicializar();
	
	@SuppressWarnings( "unchecked" )
	public CREATOR id(Integer id) {
		this.alertaDTO.setId( id );
		
		return ( CREATOR ) this;
	}
	
	@SuppressWarnings( "unchecked" )
	public CREATOR titulo(String titulo) {
		this.alertaDTO.setTitulo( titulo );
		
		return ( CREATOR ) this;
	}
	
	@SuppressWarnings( "unchecked" )
	public CREATOR descripcion(String descripcion) {
		this.alertaDTO.setDescripcion( descripcion );
		
		return ( CREATOR ) this;
	}
	
	public abstract ALERTA create();
}
