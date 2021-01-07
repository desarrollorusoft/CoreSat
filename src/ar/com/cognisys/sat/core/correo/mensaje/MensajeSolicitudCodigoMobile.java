package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeSolicitudCodigoMobile extends MensajeCorreo {

	private String correo; 
	private String codigo;
	
	public MensajeSolicitudCodigoMobile(String correo, String codigo) {
		this.setCorreo(correo);
		this.setCodigo(codigo);
	}

	@Override
	public Contribuyente getRemitente() {
		return null;
	}

	@Override
	public List<Contribuyente> getDestinatarios() {
		return null;
	}

	@Override
	public String getEncabezado() {
		return "MVL SAT - Solicitud de código para activación";
	}

	@Override
	public String getMensaje() {
		return "Se le ha generado un nuevo código de activación, el mismo debe ingresarlo en la aplicación de su dispositivo móvil, para poder completar el proceso de registración. \n" +
			   "Su nuevo código de activación es: " + this.getCodigo() + "\n\n" +
			   "Correo generado automáticamente, por favor, no lo responda.";
	}

	@Override
	public String getDestinatario() {
		return this.getCorreo();
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}