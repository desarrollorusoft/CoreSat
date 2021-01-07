package ar.com.cognisys.sat.v2.vista.modelo.v2.view.creator.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.PadronRSView;

public class PadronRSViewCreator {

	private PadronRSView padron;

	public PadronRSViewCreator() {
		this.padron = new PadronRSView();
	}

	public PadronRSViewCreator codigoCuenta(String codigoCuenta) {
		this.padron.setCodigoCuenta( codigoCuenta );

		return this;
	}

	public PadronRSView create() {
		return this.padron;
	}

}
