package ar.com.cognisys.sat.core.modelo.comun.rs.recuperador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJActividades;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJOEP;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoDDJJSV;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class RecuperadorVersionesRS2018 extends RecuperadorVersionesRS {

	public RecuperadorVersionesRS2018(DDJJRS ddjj, Connection con) {
		super(ddjj, con);
	}

	@Override
	protected void cargarDatosVersion(VersionPadronRS version) throws ExcepcionControladaError {
		VersionPadronRS2018 v = (VersionPadronRS2018) version;
		
		v.setListaCarteleria( (new DaoDDJJCarteleria(this.getCon())).recuperar(version.getId(), version.getAno()) );
		v.setListaOEP( (new DaoDDJJOEP(this.getCon())).recuperar(version.getId()) );
		v.setServiciosVarios( (new DaoDDJJSV(this.getCon())).recuperar(version.getId()) );
		v.setActividades( (new DaoDDJJActividades(this.getCon())).recuperar(version.getId(), version.getAno()) );
	}
}