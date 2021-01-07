package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeRegistracionMobileConfirmacion extends MensajeCorreo {

	private String correo;
	private String codigo;
	
	public MensajeRegistracionMobileConfirmacion(String correo, String codigo) {
		this.setCodigo(codigo);
		this.setCorreo(correo);
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
		return "MVL SAT - Registraci�n realizada";
	}

	@Override
	public String getMensaje() {
		return "Ud. ha sido registrado como usuario del Sistema de Autogestion Tributaria de la Municipalidad de Vicente L�pez.\n\n" +
			   "Se le ha generado un c�digo de activaci�n para poder completar el proceso de registraci�n. Este c�digo debe ingresarlo en la aplicaci�n. \n" +
			   "C�digo de activaci�n: " + this.getCodigo() + "\n\n" +
			   "Correo generado autom�ticamente, por favor, no lo responda.";
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