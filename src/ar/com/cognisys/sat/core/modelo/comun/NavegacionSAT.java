package ar.com.cognisys.sat.core.modelo.comun;

import ar.com.cognisys.sat.core.modelo.abstracto.NavegacionAplicacion;

public class NavegacionSAT extends NavegacionAplicacion {

	private static final long serialVersionUID = -7034500519617728674L;

	@Override
	public String getAplicacion() {
		return "SAT";
	}
}