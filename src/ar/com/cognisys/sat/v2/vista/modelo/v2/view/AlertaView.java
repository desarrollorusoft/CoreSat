package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IAlertaView;

public class AlertaView implements IAlertaView {

	private static final long serialVersionUID = -5858327428435370898L;
	private Date start;
	private Date end;
	private String title;
	private String clave;
	private String redireccion;

	@Override
	public Date getStart() {
		return start;
	}

	@Override
	public Date getEnd() {
		return end;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getClave() {
		return clave;
	}

	@Override
	public String getRedireccion() {
		return redireccion;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public void setRedireccion(String redireccion) {
		this.redireccion = redireccion;
	}
}