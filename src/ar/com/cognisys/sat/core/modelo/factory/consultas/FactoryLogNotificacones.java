package ar.com.cognisys.sat.core.modelo.factory.consultas;

import java.sql.Timestamp;

import ar.com.cognisys.sat.core.modelo.comun.consultas.LogNotificaciones;


public class FactoryLogNotificacones {

	public static LogNotificaciones generarInstancia() {
		return new LogNotificaciones();
	}

	public static LogNotificaciones generarInstancia(String asunto, Timestamp fecha, String correo, String usuario) {
		LogNotificaciones log = generarInstancia();
		log.setAsunto(asunto);
		log.setCorreo(correo);
		log.setFecha(fecha.toString());
		log.setNombreUsuario( usuario );
		
		return log;
	}

}
