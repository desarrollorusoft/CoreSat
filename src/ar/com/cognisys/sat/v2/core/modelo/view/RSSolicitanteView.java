package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.ISolicitanteView;

public class RSSolicitanteView implements ISolicitanteView {
	
	private static final long serialVersionUID = 139046216852472893L;
	private String nombre;
	private String apellido;
	private String caracter;
	private String telefono;
	private String correo;
	private String celular;

	public RSSolicitanteView(String nombre, String apellido, String caracter, String correo, String telefono, String celular) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.caracter = caracter;
		this.correo = correo;
		this.telefono = telefono;
		this.celular = celular;
	}

	@Override
	public String getCorreo() {
		return this.correo;
	}

	@Override
	public String getTelefono() {
		return this.telefono;
	}

	@Override
	public String getCelular() {
		return this.celular;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getApellido() {
		return this.apellido;
	}

	@Override
	public String getCaracter() {
		return this.caracter;
	}

	@Override
	public String getNombreCompleto() {
		return null;
	}
}