package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IActividadView;

public class ActividadView implements IActividadView {

	private static final long serialVersionUID = 6835574614106833193L;
	
	private String cuit;
	private String correo;
	private String telefono;
	private String celular;

	public ActividadView() {}
	
	public ActividadView(String cuit, String correo, String telefono) {
		this.cuit = cuit;
		this.correo = correo;
		this.telefono = telefono;
	}
	
	public ActividadView(String cuit, String correo, String telefono, String celular) {
		this.cuit = cuit;
		this.correo = correo;
		this.telefono = telefono;
		this.celular = celular;
	}
	
	@Override public String getCuit() {
		return cuit;
	}

	@Override public String getCorreo() {
		return correo;
	}

	@Override public String getTelefono() {
		return telefono;
	}

	@Override public String getCelular() {
		return celular;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
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
