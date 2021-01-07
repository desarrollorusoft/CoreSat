package ar.com.cognisys.sat.v2.core.modelo.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IMensajeView;

public class MensajeView implements IMensajeView {

	private static final long serialVersionUID = 8200998593855762943L;
	private Date fecha;
	private String remitente;
	private String texto;
	private boolean emisor;

	// @formatter:off
	public MensajeView() { }
	// @formatter:on
	
	public MensajeView(Date fecha, String remitente, String texto, boolean emisor) {
		this.fecha = fecha;
		this.remitente = remitente;
		this.texto = texto;
		this.emisor = emisor;
	}

	@Override
	public Date getFecha() {
		return fecha;
	}

	@Override
	public String getRemitente() {
		return remitente;
	}

	@Override
	public String getTexto() {
		return texto;
	}

	@Override
	public boolean isEmisor() {
		return emisor;
	}

}
