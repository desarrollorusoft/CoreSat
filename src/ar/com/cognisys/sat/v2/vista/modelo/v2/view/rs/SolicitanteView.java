package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ISolicitanteView;

public class SolicitanteView implements ISolicitanteView {

	private static final long serialVersionUID = 6671051332981166495L;
	private String nombre;
	private String apellido;
	private String caracter;
	private String correo;
	private String telefono;
	private String celular;

	public SolicitanteView() {}
	
	public SolicitanteView(String correo, String telefono) {
		this.correo = correo;
		this.telefono = telefono;
	}
	
	public SolicitanteView(String nombre, String apellido, String caracter, String correo, String telefono, String celular) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.caracter = caracter;
		this.correo = correo;
		this.telefono = telefono;
		this.celular = celular;
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
	public String getCaracter() {
		return caracter;
	}

	@Override
	public String getCorreo() {
		return correo;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public String getCelular() {
		return celular;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}
