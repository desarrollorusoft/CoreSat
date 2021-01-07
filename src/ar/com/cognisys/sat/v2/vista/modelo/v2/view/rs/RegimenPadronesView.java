package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IPadronRSView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenPadronesView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenView;

public class RegimenPadronesView implements IRegimenPadronesView {

	private static final long serialVersionUID = -7051605858316257322L;

	private RegimenView regimen;
	private PadronRSView[] padrones;

	public RegimenPadronesView() {}
	
	public RegimenPadronesView(RegimenView regimen) {
		this.regimen = regimen;
	}
	
	public RegimenPadronesView(RegimenView regimen, PadronRSView[] padrones) {
		this.regimen = regimen;
		this.padrones = padrones;
	}

	@Override public IRegimenView getRegimen() {
		return regimen;
	}

	@Override public IPadronRSView[] getPadrones() {
		return padrones;
	}

	public void setRegimen(RegimenView regimen) {
		this.regimen = regimen;
	}

	public void setPadrones(PadronRSView[] padrones) {
		this.padrones = padrones;
	}
}