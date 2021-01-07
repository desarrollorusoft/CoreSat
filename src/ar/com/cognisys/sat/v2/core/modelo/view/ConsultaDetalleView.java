package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaDetalleView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IMensajeView;

public class ConsultaDetalleView implements IConsultaDetalleView {

	private static final long serialVersionUID = 3356824369419924898L;
	private String numero;
	private String nombre;
	private String apellido;
	private String cuit;
	private String telefono;
	private String correo;
	private IMensajeView[] mensajes;
	private IRutaNombreView[] archivos;

	@Override
	public String getNumero() {
		return numero;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getApellido() {
		return apellido;
	}

	@Override
	public String getCuit() {
		return cuit;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public String getCorreo() {
		return correo;
	}

	@Override
	public IMensajeView[] getMensajes() {
		return mensajes;
	}

	@Override
	public void setMensajes(IMensajeView[] mensajes) {
		this.mensajes = mensajes;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Override
	public IRutaNombreView[] getArchivos() {
		return archivos;
	}

	@Override
	public void setArchivos(IRutaNombreView[] archivos) {
		this.archivos = archivos;
	}
	
}
