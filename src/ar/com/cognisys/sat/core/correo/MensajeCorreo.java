package ar.com.cognisys.sat.core.correo;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public abstract class MensajeCorreo {

	public abstract Contribuyente getRemitente();
	public abstract List<Contribuyente> getDestinatarios();
	public abstract String getEncabezado();
	public abstract String getMensaje();
	public abstract String getDestinatario();
	public abstract String getPathArchivo();	
}