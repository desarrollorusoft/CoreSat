package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IActividadView;

public class RSActividadView implements IActividadView {
	
	private static final long serialVersionUID = -206622135493857273L;
	private String cuit;
	private String correo;
	private String telefono;
	private String celular;
	private String clave;
	
	public RSActividadView(String cuit, String correo, String telefono, String celular, String clave) {
		this.cuit = cuit;
		this.correo = correo;
		this.telefono = telefono;
		this.celular = celular;
		this.clave = clave;
	}

	@Override
	public String getCuit() {
		return cuit;
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

	@Override
	public String getClave() {
		return clave;
	}
}