package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeRecibo extends MensajeCorreo {

	private String correo;
	private String pathArchivo;
	private String nombreArchivo;
	
	public MensajeRecibo(String correo, String pathArchivo, String nombreArchivo) {
		this.setCorreo(correo);
		this.setPathArchivo(pathArchivo);
		this.setNombreArchivo(nombreArchivo);
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
		return "MVL - SAT Mobile - Recibo generado";
	}

	@Override
	public String getMensaje() {
		return "Se le envía el recibo generado desde la aplicación Mobile";
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

	public String getPathArchivo() {
		return pathArchivo;
	}

	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
}