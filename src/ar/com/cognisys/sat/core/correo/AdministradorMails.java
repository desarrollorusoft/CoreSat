package ar.com.cognisys.sat.core.correo;

import ar.com.cognisys.sat.core.correo.mensaje.MensajeProblemasConBase;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

public class AdministradorMails {
	
	public static void enviar(MensajeCorreo mensaje) throws ExcepcionControladaAlerta {
//		try {
			EnviadorCorreo.getInstance().enviarMensajePublico(mensaje);
//		} catch (ExcepcionControladaAlerta e) {
//			EnviadorCorreoCognisys.getInstance().enviarMensajePublico(mensaje);
//		}
	}

	public static void enviar(MensajeProblemasConBase mensaje) throws ExcepcionControladaAlerta {
		EnviadorCorreo.getInstance().enviarMensajeReporte(mensaje);
	}
}