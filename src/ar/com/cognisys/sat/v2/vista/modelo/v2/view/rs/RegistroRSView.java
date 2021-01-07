package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegistroRSView;

public class RegistroRSView implements IRegistroRSView {

	private static final long serialVersionUID = -412925665650760836L;
	private RegimenView regimen;
	private String clave;

	@Override public IRegimenView getRegimen() {
		return regimen;
	}

	@Override public String getClave() {
		return clave;
	}

	public void setRegimen(RegimenView regimen) {
		this.regimen = regimen;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
}
