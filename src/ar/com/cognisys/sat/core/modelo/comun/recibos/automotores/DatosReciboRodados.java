package ar.com.cognisys.sat.core.modelo.comun.recibos.automotores;


public class DatosReciboRodados {
	
	
	public DatosReciboRodados(String titular, String domicilio, String dominio,
			String marca, String modelo, String cilindrada, String comprobante,
			String vencimiento, String importe_cuota, String importe_anual,
			String periodo, String banelco) {
		this.titular = titular;
		this.domicilio = domicilio;
		this.dominio = dominio;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindrada = cilindrada;
		this.comprobante = comprobante;
		this.vencimiento = vencimiento;
		this.importe_cuota = importe_cuota;
		this.importe_anual = importe_anual;
		this.periodo = periodo;
		this.banelco = banelco;
	}
	private String titular;             
	private String  domicilio;          
	private String  dominio;            
	private String  marca;              
	private String modelo;              
	private String cilindrada;           
	private String comprobante;          
	private String vencimiento;          
	private String importe_cuota;        
	private String importe_anual;        
	private String periodo;              
	private String banelco;
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
	public String getCilindrada() {
		return cilindrada;
	}
	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public String getImporte_cuota() {
		return importe_cuota;
	}
	public void setImporte_cuota(String importe_cuota) {
		this.importe_cuota = importe_cuota;
	}
	public String getImporte_anual() {
		return importe_anual;
	}
	public void setImporte_anual(String importe_anual) {
		this.importe_anual = importe_anual;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getBanelco() {
		return banelco;
	}
	public void setBanelco(String banelco) {
		this.banelco = banelco;
	}
	public String getCuotasAdeudadas() {
		return cuotasAdeudadas;
	}
	public void setCuotasAdeudadas(String cuotasAdeudadas) {
		this.cuotasAdeudadas = cuotasAdeudadas;
	}
}