package ar.com.cognisys.sat.v2.core.modelo.view;

import java.util.Date;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.ICarteleriaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IPadronRSView;

public class PadronRSView implements IPadronRSView {

	private static final long serialVersionUID = -6940930951899141952L;
	private String codigoCuenta;
	private String importe;
	private String razonSocial;
	private ICarteleriaView[] cartelerias;
	private String metrosOEP;
	private String unidadesOEP;
	private String motoresSV;
	private String calderasSV;
	private boolean completo;
	private String cantidadPersonas;
	private String rubroPrincipal;
	private String[] rubrosSecundarios;
	private Date fechaHabilitacion;

	// @formatter:off
	public PadronRSView() { }
	// @formatter:on

	public PadronRSView(String codigoCuenta, String importe, ICarteleriaView[] cartelerias, String metrosOEP, String unidadesOEP, String motoresSV, String calderasSV, String cantidadPersonas,
			String rubroPrincipal, String[] rubrosSecundarios, Date fechaHabilitacion, String razonSocial) {
		this.codigoCuenta = codigoCuenta;
		this.importe = importe;
		this.cartelerias = cartelerias;
		this.metrosOEP = metrosOEP;
		this.unidadesOEP = unidadesOEP;
		this.motoresSV = motoresSV;
		this.calderasSV = calderasSV;
		this.cantidadPersonas = cantidadPersonas;
		this.rubroPrincipal = rubroPrincipal;
		this.rubrosSecundarios = rubrosSecundarios;
		this.fechaHabilitacion = fechaHabilitacion;
		this.razonSocial = razonSocial;
	}

	@Override
	public String getCodigoCuenta() {
		return codigoCuenta;
	}

	@Override
	public String getImporte() {
		return importe;
	}

	@Override
	public ICarteleriaView[] getCartelerias() {
		return cartelerias;
	}

	@Override
	public String getMetrosOEP() {
		return metrosOEP;
	}

	@Override
	public String getUnidadesOEP() {
		return unidadesOEP;
	}

	@Override
	public String getMotoresSV() {
		return motoresSV;
	}

	@Override
	public String getCalderasSV() {
		return calderasSV;
	}

	@Override
	public boolean isCompleto() {
		return completo;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public void setCartelerias(ICarteleriaView[] cartelerias) {
		this.cartelerias = cartelerias;
	}

	public void setMetrosOEP(String metrosOEP) {
		this.metrosOEP = metrosOEP;
	}

	public void setUnidadesOEP(String unidadesOEP) {
		this.unidadesOEP = unidadesOEP;
	}

	public void setMotoresSV(String motoresSV) {
		this.motoresSV = motoresSV;
	}

	public void setCalderasSV(String calderasSV) {
		this.calderasSV = calderasSV;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	@Override
	public String getCantidadPersonas() {
		return cantidadPersonas;
	}

	@Override
	public String getRubroPrincipal() {
		return rubroPrincipal;
	}

	@Override
	public String[] getRubrosSecundarios() {
		return rubrosSecundarios;
	}
	@Override
	public Date getFechaHabilitacion() {
		return fechaHabilitacion;
	}
	@Override
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setCantidadPersonas(String cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public void setRubroPrincipal(String rubroPrincipal) {
		this.rubroPrincipal = rubroPrincipal;
	}

	public void setRubrosSecundarios(String[] rubrosSecundarios) {
		this.rubrosSecundarios = rubrosSecundarios;
	}

	public void setFechaHabilitacion(Date fechaHabilitacion) {
		this.fechaHabilitacion = fechaHabilitacion;
	}
	
}
