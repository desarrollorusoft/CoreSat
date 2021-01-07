package ar.com.cognisys.sat.v2.vista.modelo.v2.view.creator.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RubroView;

public class RubroViewCreator {

	private RubroView rubro;

	public RubroViewCreator() {
		this.rubro = new RubroView();
	}

	public RubroViewCreator nombre(String nombre) {
		this.rubro.setNombre( nombre );

		return this;
	}
	
	public RubroViewCreator permitido() {
		this.rubro.setPermitido( true );

		return this;
	}
	
	public RubroViewCreator permitido(boolean permitido) {
		this.rubro.setPermitido( permitido );

		return this;
	}

	public RubroView create() {
		return this.rubro;
	}

}
