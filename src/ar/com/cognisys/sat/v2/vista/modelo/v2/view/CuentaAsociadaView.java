package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaAsociadaView;

public class CuentaAsociadaView implements ICuentaAsociadaView {

	private static final long serialVersionUID = -1501564617633590126L;

	private String tipo;
	private String codigo;
	private String descripcion;
	private String alias;
	private String importe;

	@Override public String getTipo() {
		return tipo;
	}

	@Override public String getCodigo() {
		return codigo;
	}

	@Override public String getDescripcion() {
		return descripcion;
	}

	@Override public String getAlias() {
		return alias;
	}

	@Override public String getImporte() {
		return importe;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

}
