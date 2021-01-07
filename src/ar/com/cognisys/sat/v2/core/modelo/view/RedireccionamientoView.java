package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRedireccionamientoView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class RedireccionamientoView implements IRedireccionamientoView {

	private static final long serialVersionUID = 1749728831400011L;
	private String redireccionamiento;
	private IClaveValorView[] parametros;

	// @formatter:off
	public RedireccionamientoView() { }
	// @formatter:on
	
	public RedireccionamientoView(String redireccionamiento, IClaveValorView[] parametros) {
		this.redireccionamiento = redireccionamiento;
		this.parametros = parametros;
	}

	@Override
	public String getRedireccionamiento() {
		return redireccionamiento;
	}

	@Override
	public IClaveValorView[] getParametros() {
		return parametros;
	}
}