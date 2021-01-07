package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ICarteleriaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IPadronRSView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRubroView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.ClaveValorView;

public class PadronRSView implements IPadronRSView {

	private static final long serialVersionUID = 2739974079932789654L;
	private String codigoCuenta;
	private String razonSocial;
	private String importe;
	private String codigoCuentaABL;
	private CarteleriaView[] cartelerias;
	private ClaveValorView[] ocupaciones;
	private String motoresSV;
	private String calderasSV;
	private String cantidadPersonas;
	private RubroView rubroPrincipal;
	private String[] rubrosSecundarios;
	private Date fechaHabilitacion;
	private boolean completo;

	@Override public String getCodigoCuenta() {
		return codigoCuenta;
	}

	@Override public String getRazonSocial() {
		return razonSocial;
	}

	@Override public String getImporte() {
		return importe;
	}

	@Override public String getCodigoCuentaABL() {
		return codigoCuentaABL;
	}

	@Override public ICarteleriaView[] getCartelerias() {
		return cartelerias;
	}

	@Override public IClaveValorView[] getOcupaciones() {
		return ocupaciones;
	}

	@Override public String getMotoresSV() {
		return motoresSV;
	}

	@Override public String getCalderasSV() {
		return calderasSV;
	}

	@Override public String getCantidadPersonas() {
		return cantidadPersonas;
	}

	@Override public IRubroView getRubroPrincipal() {
		return rubroPrincipal;
	}

	@Override public String[] getRubrosSecundarios() {
		return rubrosSecundarios;
	}

	@Override public Date getFechaHabilitacion() {
		return fechaHabilitacion;
	}

	@Override public boolean isCompleto() {
		return completo;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public void setCodigoCuentaABL(String codigoCuentaABL) {
		this.codigoCuentaABL = codigoCuentaABL;
	}

	public void setCartelerias(CarteleriaView[] cartelerias) {
		this.cartelerias = cartelerias;
	}

	public void setOcupaciones(ClaveValorView[] ocupaciones) {
		this.ocupaciones = ocupaciones;
	}

	public void setMotoresSV(String motoresSV) {
		this.motoresSV = motoresSV;
	}

	public void setCalderasSV(String calderasSV) {
		this.calderasSV = calderasSV;
	}

	public void setCantidadPersonas(String cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public void setRubroPrincipal(RubroView rubroPrincipal) {
		this.rubroPrincipal = rubroPrincipal;
	}

	public void setRubrosSecundarios(String[] rubrosSecundarios) {
		this.rubrosSecundarios = rubrosSecundarios;
	}

	public void setFechaHabilitacion(Date fechaHabilitacion) {
		this.fechaHabilitacion = fechaHabilitacion;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}
}