package ar.com.cognisys.sat.core.correo;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

public class EnviadorCorreoRunnable implements Runnable {

	private MensajeCorreo mensaje;
	
	public EnviadorCorreoRunnable(MensajeCorreo m) {
		this.mensaje = m;
	}

	@Override
	public void run() {
		try {
			AdministradorMails.enviar(  this.mensaje );
		} catch (ExcepcionControladaAlerta e) {	
			throw new RuntimeException(e);
		}
	}

	public MensajeCorreo getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeCorreo mensaje) {
		this.mensaje = mensaje;
	}

	
}
