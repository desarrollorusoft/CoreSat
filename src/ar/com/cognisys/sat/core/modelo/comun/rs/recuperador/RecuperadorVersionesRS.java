package ar.com.cognisys.sat.core.modelo.comun.rs.recuperador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoVersionDDJJ;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public abstract class RecuperadorVersionesRS {

	private DDJJRS ddjj;
	private Connection con;
	
	public RecuperadorVersionesRS(DDJJRS ddjj, Connection con) {
		this.setCon(con);
		this.setDdjj(ddjj);
	}

	public void cargar() throws ExcepcionControladaError {
		
		for (PadronRS p : ddjj.getListaPadrones()) {
			List<VersionPadronRS> listaVersiones = (new DaoVersionDDJJ(con)).recuperar(ddjj.getAno(), p);
			p.agregar(listaVersiones);
			
			for (VersionPadronRS v : listaVersiones)
				this.cargarDatosVersion( v );
		}
	}

	protected abstract void cargarDatosVersion(VersionPadronRS version) throws ExcepcionControladaError;
	
	public DDJJRS getDdjj() {
		return ddjj;
	}

	private void setDdjj(DDJJRS ddjj) {
		this.ddjj = ddjj;
	}

	public Connection getCon() {
		return con;
	}

	private void setCon(Connection con) {
		this.con = con;
	}
}