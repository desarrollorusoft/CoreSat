package ar.com.cognisys.sat.v2.vista.modelo.v2.view.creator.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.PadronRSView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RegimenPadronesView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RegimenView;

public class RegimenPadronesViewCreator {

	private RegimenPadronesView regimenPadrones;

	public RegimenPadronesViewCreator() {
		this.regimenPadrones = new RegimenPadronesView();
	}

	public RegimenPadronesViewCreator regimen(RegimenView regimen) {
		this.regimenPadrones.setRegimen( regimen );

		return this;
	}
	
	public RegimenPadronesViewCreator padrones(PadronRSView[] padrones) {
		this.regimenPadrones.setPadrones( padrones );

		return this;
	}

	public RegimenPadronesView create() {
		return this.regimenPadrones;
	}

}
