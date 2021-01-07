package ar.com.cognisys.sat.core.modelo.comun.recibos.automotores;

public class DatosReciboAutomotores {
	
	public DatosReciboAutomotores(String titular, String domicilio,
			String dominio, String marca, String modelo, String importe_anual,
			String baseImponible, String codigo, String tipo, String anio,
			String categoria, String cuota, String codigoBanelco) {
		this.titular = titular;
		this.domicilio = domicilio;
		this.dominio = dominio;
		this.marca = marca;
		this.modelo = modelo;
		if (importe_anual != null) {
			this.importe_anual = importe_anual;
		} else {
			this.importe_anual = "0";
		}
		this.baseImponible = baseImponible;
		this.codigo = codigo;
		this.tipo = tipo;
		this.anio = anio;
		this.categoria = categoria;
		this.cuota = cuota;
		this.codigoBanelco = codigoBanelco;
	}	

	private String titular;
	private String domicilio;
	private String dominio;
	private String marca;
	private String modelo;
	private String importe_anual;
	private String categoria;
	private String anio;
	private String cuota;
	private String tipo;
	private String codigo;
	private String baseImponible;
	private String codigoBanelco;
	private String cuotasAdeudadas;

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getImporte_anual() {
		return importe_anual;
	}

	public void setImporte_anual(String importe_anual) {
		this.importe_anual = importe_anual;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getCuota() {
		return cuota;
	}

	public void setCuota(String cuota) {
		this.cuota = cuota;
	}

	public String getCodigoBanelco() {
		return codigoBanelco;
	}

	public void setCodigoBanelco(String codigoBanelco) {
		this.codigoBanelco = codigoBanelco;
	}

	public String getCuotasAdeudadas() {
		return cuotasAdeudadas;
	}

	public void setCuotasAdeudadas(String cuotasAdeudadas) {
		this.cuotasAdeudadas = cuotasAdeudadas;
	}
}
