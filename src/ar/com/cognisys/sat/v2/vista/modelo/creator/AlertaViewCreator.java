package ar.com.cognisys.sat.v2.vista.modelo.creator;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.v2.view.AlertaView;

public class AlertaViewCreator {

	private AlertaView alertaView;
	
	public AlertaViewCreator() {
		this.alertaView = new AlertaView();
	}
	
	public AlertaViewCreator title(String title) {
		this.alertaView.setTitle( title );
		
		return this;
	}
	
	public AlertaViewCreator clave(String clave) {
		this.alertaView.setClave( clave );
		
		return this;
	}
	
	public AlertaViewCreator start(Date start) {
		this.alertaView.setStart( start );
		
		return this;
	}
	
	public AlertaViewCreator end(Date end) {
		this.alertaView.setEnd( end );
		
		return this;
	}
	
	public AlertaViewCreator redireccion(String redireccion) {
		this.alertaView.setRedireccion( redireccion );
		
		return this;
	}
	
	public AlertaView create() {
		return this.alertaView;
	}
}
