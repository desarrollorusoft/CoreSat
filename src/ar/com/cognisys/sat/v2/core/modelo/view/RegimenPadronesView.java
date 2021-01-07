package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IPadronRSView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IRegimenPadronesView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.rs.IRegimenView;

public class RegimenPadronesView implements IRegimenPadronesView {

	private static final long serialVersionUID = -6761701014643058192L;
	private IRegimenView regimen;
	private IPadronRSView[] padrones;

	// @formatter:off
	public RegimenPadronesView() { }
	// @formatter:on

	public RegimenPadronesView(IRegimenView regimen, IPadronRSView[] padrones) {
		this.regimen = regimen;
		this.padrones = padrones;
	}

	@Override
	public IRegimenView getRegimen() {
		return regimen;
	}

	@Override
	public IPadronRSView[] getPadrones() {
		return padrones;
	}

	@Override
	public String getAuthToken() {
		// TODO [RODRI]
		return null;
	}

	@Override
	public void setAuthToken(String authToken) {
		// TODO [RODRI]
	}
}