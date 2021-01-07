package ar.com.cognisys.sat.v2.core.controlador.facade;

import ar.com.cognisys.sat.v2.core.modelo.view.BannerView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IBannerView;

public class FacadeBanners {

	/**
	 * Método utilizado para recuperar los banners.
	 * 
	 * @return banners
	 */
	public IBannerView[] recuperar() {
		
		return new IBannerView[] { 
				new BannerView( "http://www.cognisys.com.ar/MVL/SATMobile_Banner.jpg",
								"http://www.cognisys.com.ar/MVL/SATMobile_Banner.jpg") };
	}
}