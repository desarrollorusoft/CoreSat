package ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ICuitPadronView;

public class CuitPadronView implements ICuitPadronView {
	
	private static final long serialVersionUID = -1894702661453903658L;
	private String cuit;
	private String padron;
	
	public CuitPadronView() {}
	
	public CuitPadronView(String cuit, String padron) {
		this.cuit = cuit;
		this.padron = padron;
	}

	@Override
	public String getCuit() {
		return cuit;
	}

	@Override
	public String getPadron() {
		return padron;
	}
}