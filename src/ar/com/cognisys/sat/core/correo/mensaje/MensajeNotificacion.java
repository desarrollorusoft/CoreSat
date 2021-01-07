package ar.com.cognisys.sat.core.correo.mensaje;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.v2.persistencia.modelo.notificacion.Notificacion;

import java.util.List;

public class MensajeNotificacion extends MensajeCorreo {

	private String correo;
	private Notificacion notificacion;

	public MensajeNotificacion(String correo, Notificacion notificacion) {
		this.correo = correo;
		this.notificacion = notificacion;
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
		return "Inicio de evento de su Calendario";
	}

	@Override
	public String getMensaje() {
		return "Estimado Contribuyente,\n\n"
			 + "Le enviamos este correo para informarle que el evento " +  this.notificacion.getTitulo() + " " + this.notificacion.getDescripcion() + " ha comenzado el día " + this.notificacion.getFecha()+ ".\n"
	 		 + "El acceso a al sistema lo puede hacer ingresando a https://www.vicentelopez.gov.ar/ingresos-publicos/, o directamente a través de https://www.vicentelopez.gov.ar/SAT/pages/pub/login.xhtml \n"
	 		 + "\n\nCorreo generado automáticamente, por favor, no lo responda.";
	}
	
	@Override
	public String getDestinatario() {
		return this.correo;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}