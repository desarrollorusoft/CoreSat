package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IPadronRSView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IPadronesRSView;

public class PadronesRSView implements IPadronesRSView {
	
	private static final long serialVersionUID = -4687978882754041073L;
	private PadronRSView[] padrones;

	public PadronesRSView() {}
	
	public PadronesRSView(PadronRSView[] padrones) {
		this.padrones = padrones;
	}

	@Override
	public IPadronRSView[] getPadrones() {
		return padrones;
	}

	public void setPadrones(PadronRSView[] padrones) {
		this.padrones = padrones;
	}
}