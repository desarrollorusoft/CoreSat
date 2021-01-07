package ar.com.cognisys.sat.v2.core.modelo.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta.IConsultaCabeceraView;

public class ConsultaCabeceraView implements IConsultaCabeceraView {

	private static final long serialVersionUID = -8603774602665593961L;
	private String numero;
	private String tipo;
	private String estado;
	private String categoria;
	private Date fecha;
	private String codigoCuenta;

	@Override
	public String getNumero() {
		return numero;
	}

	@Override
	public String getTipo() {
		return tipo;
	}

	@Override
	public String getEstado() {
		return estado;
	}

	@Override
	public String getCategoria() {
		return categoria;
	}

	@Override
	public Date getFecha() {
		return fecha;
	}

	@Override
	public String getCodigoCuenta() {
		return codigoCuenta;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}

}
