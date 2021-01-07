package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeProblemasConBase {

	private String mensaje;
	
	public MensajeProblemasConBase(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public List<String> getDestinatariosReportes() {
		
		List<String> listaDestinatariosReportes = new ArrayList<String>();
		listaDestinatariosReportes.add("fgarnero@cognisys.com.ar");
//		listaDestinatarios.add("mbenz@cognisys.com.ar");
//		listaDestinatarios.add("dcosta@cognisys.com.ar");
		
		return listaDestinatariosReportes;
	}

	public String getEncabezado() {
		
		return "MVL - Error al registrar seguimiento en MySQL Reportes ";
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Contribuyente getRemitente() {
		return null;
	}

	public List<Contribuyente> getDestinatarios() {
		return null;
	}

	public String getDestinatario() {
		return null;
	}
}

	
	