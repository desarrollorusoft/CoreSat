package ar.com.cognisys.sat.core.administrador;

import ar.com.cognisys.sat.core.modelo.comun.Tasa;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSDeudaPadron;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.comun.rs.DDJJRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.PadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.recuperador.RecuperadorVersionesRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.dao.DaoActividadComercial;
import ar.com.cognisys.sat.core.modelo.dao.DaoRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.dao.DaoTasaComercio;
import ar.com.cognisys.sat.core.modelo.dao.rs.*;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.rs.FactoryRecuperadorRS;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class AdministradorTasasComercio {

	public static Map<Integer, Tasa> buscar() throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoTasaComercio(con)).buscar();
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
}