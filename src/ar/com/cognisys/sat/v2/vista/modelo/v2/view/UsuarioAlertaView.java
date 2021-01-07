package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IAlertaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IUsuarioAlertaView;

public class UsuarioAlertaView implements IUsuarioAlertaView {
	
	private static final long serialVersionUID = 918540428712576904L;
	private String nombreUsuario;
	private AlertaView alerta;
	
	@Override
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	@Override
	public IAlertaView getAlerta() {
		return alerta;
	}

	public void setAlerta(AlertaView alerta) {
		this.alerta = alerta;
	}
}