package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeCambioClave extends MensajeCorreo {

	private String correo;
	private String clave;

	public MensajeCambioClave(String correo, String clave) {
		this.setCorreo(correo);
		this.setClave(clave);
	}

	@Override
	public Contribuyente getRemitente() {
		return null;
	}
	
	@Override
	public String getDestinatario() {
		return this.getCorreo();
	}

	@Override
	public String getEncabezado() {
		return "MVL SAT - Cambio clave";
	}

	@Override
	public String getMensaje() {
		return "Estimado Contribuyente, \n\n" +
			   "Su contraseña ha sido re-generada por nuestro sistema, la misma es: " + this.getClave();
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}