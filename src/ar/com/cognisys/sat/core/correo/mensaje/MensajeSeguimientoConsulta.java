package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import org.apache.commons.codec.binary.Base64;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeSeguimientoConsulta extends MensajeCorreo {

	private String correo;
	
	public MensajeSeguimientoConsulta(String correo) {
		this.correo = correo;
	}
	
	public String getEncabezado() {
		
		return "Seguimiento Consulta";
	}

	@Override public Contribuyente getRemitente() {
		return null;
	}

	@Override public List<Contribuyente> getDestinatarios() {
		return null;
	}

	@Override public String getMensaje() {
		String mensaje =
				"<div style=\"\n" +
						"font-family: sans-serif;\n" +
						"color: rgb(70, 70, 70);\n" +
						"font-size: 16px;\n" +
						"max-width: 55rem;\n" +
						"padding: 1rem;\">\n" +
						"<p>Estimado/a Contribuyente</p>\n" +
						"<p>Su consulta fue recibida, en breve será respondida.</p>\n" +
						"  <div>\n" +
						"      <a href=\"https://www.vicentelopez.gov.ar/SAT/SAT/consulta/consultas-usuario/"+ new String(Base64.encodeBase64(this.correo.getBytes())) +"\" style=\"text-decoration:none;\"><button style=\"background: #2962FF; color: #fff; width: 9rem;height: 2rem; border-radius: 3px; border-color: #2962FF; font-weight: 700\">Ver Respuesta</button></a>\n" +
						"      <a href=\"https://www.vicentelopez.gov.ar/SAT/SAT/consulta/consultas-usuario/\" style=\"text-decoration:none;\"><button style=\"background: #fff; color: #2962FF; width: 9rem;height: 2rem; border-radius: 3px; border-color: #2962FF; font-weight: 700\">Historial</button></a>\n" +
						"      <a href=\"https://www.vicentelopez.gov.ar/ingresos-publicos/info/preguntasFrecuentes\" style=\"text-decoration:none;\"><button style=\"background: #fff; color: #2962FF; width: 9rem;height: 2rem; border-radius: 3px; border-color: #2962FF; font-weight: 700\">Más Información</button></a>\n" +
						"  </div>\n" +
						"\n" +
						"  <br>\n" +
						"  <p>Secretaría de Ingresos Públicos <br/> Municipalidad de Vicente López</p>\n" +
						"  <hr style=\"width: 34.5rem; margin-left: 0\">\n" +
						"  <p style=\"font-size: 0.65rem\">Este mail se generó automáticamente. Por favor, no responder.<br/>\n" +
						"      La Municipalidad garantiza el cumplimiento de las previsiones contempladas en la Ley 25.326 - Ley de protección de los <br/>\n" +
						"      datos personales.\n" +
						"  </p>\n" +
						" \n" +
						"</div>";
		
		return mensaje;
	}

	@Override public String getDestinatario() {
		return this.correo;
	}

	@Override public String getPathArchivo() {
		return null;
	}

}

	
