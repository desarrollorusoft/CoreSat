package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeEnvioConsultaMobile extends MensajeCorreo {

	private String correo;
	private String asunto;
	private String mensajeUsuario;

	public MensajeEnvioConsultaMobile(String correo, String asunto, String mensaje) {
		this.setCorreo(correo);
		this.setAsunto(asunto);
		this.setMensajeUsuario(mensaje);
	}

	@Override
	public Contribuyente getRemitente() {
		return this.getRemitente();
	}
	
	@Override
	public String getDestinatario() {
		return "satcontactos@vicentelopez.gov.ar";
	}

	@Override
	public String getEncabezado() {
		return "Consulta SAT Mobile ("+this.getAsunto()+")";
	}

	@Override
	public String getMensaje() {
		return "El contribuyente ("+this.getCorreo()+") escribió lo siguiente:\n\t" + this.getMensajeUsuario();
	}
	
	@Override
	public List<Contribuyente> getDestinatarios() {
		
		return null;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}