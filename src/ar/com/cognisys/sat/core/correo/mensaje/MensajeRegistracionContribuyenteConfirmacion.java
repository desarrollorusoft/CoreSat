package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.GeneradorClaves;
import ar.com.cognisys.sat.core.propiedades.Propiedades;

public class MensajeRegistracionContribuyenteConfirmacion extends MensajeCorreo {

	private String correo;
	private String clave;
	private Integer id;

	public MensajeRegistracionContribuyenteConfirmacion(String correo, String clave, Integer id) {
		
		this.setCorreo(correo);
		this.setClave(clave);
		this.setId(id);
	}

	@Override
	public Contribuyente getRemitente() {
		return this.getRemitente();
	}
	
	@Override
	public String getDestinatario() {
		return this.getCorreo();
	}

	@Override
	public String getEncabezado() {
		return "MVL SAT - Registración realizada";
	}

	@Override
	public String getMensaje() {
		return "Ud. ha sido registrado como usuario del Sistema de Autogestion Tributaria de la Municipalidad de Vicente López.\n\n" +
			   "Para acceder al Sistema de Autogestion Tributaria dirijase a \n" +
			   this.getUrlActivacion() + "\n\n" +
			   "Correo generado automáticamente, por favor, no lo responda.";
	}
	
	@Override
	public List<Contribuyente> getDestinatarios() {
		
		return null;
	}

	private String getUrlActivacion() {
		return (new Propiedades()).getURL_Activacion_Usuario() + GeneradorClaves.getClave(20) + this.getId();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}