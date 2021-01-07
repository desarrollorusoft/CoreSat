package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeClaveNivel3 extends MensajeCorreo {

	private String correo;
	private String clave;
	
	public MensajeClaveNivel3(String correo, String clave) {
		this.correo = correo;
		this.clave = clave;
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
		return "Clave de Acceso - Nivel 3";
	}

	@Override
	public String getMensaje() {
		return "Estimado Contribuyente,\n\n"
			 + "Le enviamos este correo para informarle que ha sido dado de alta en el SAT y podrá operar desde este momento con la siguiente clave: "+clave+"\n\n"
	 		 + "El acceso a al sistema lo puede hacer ingresando a https://www.vicentelopez.gov.ar/ingresos-publicos/, o directamente a través de https://www.vicentelopez.gov.ar/SAT/pages/pub/login.xhtml \n"
	 		 + "Le recordamos que la clave mencionada anteriormente, es una clave temporal, por lo cual, le pedimos que la cambie a la brevedad." + "\n\n" +
			   "Correo generado automáticamente, por favor, no lo responda.";
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