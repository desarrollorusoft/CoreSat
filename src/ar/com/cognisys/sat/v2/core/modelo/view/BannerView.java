package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IBannerView;

public class BannerView implements IBannerView {

	private static final long serialVersionUID = 7255412643674243054L;
	private String url;
	private String redireccion;

	// @formatter:off
	public BannerView() { }
	// @formatter:on
	
	public BannerView(String url, String redireccion) {
		this.url = url;
		this.redireccion = redireccion;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getRedireccion() {
		return redireccion;
	}

}
