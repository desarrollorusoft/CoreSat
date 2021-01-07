package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.rs.Comercio;
import ar.com.cognisys.sat.core.modelo.dao.rs.DaoSolicitanteRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorSolicitanteRS {

	public static void registrar(Comercio comercio) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			DaoSolicitanteRS dao = new DaoSolicitanteRS(con);
			
			Integer idSolicitante = dao.buscarSolicitanteRS(comercio.getCuit(), comercio.getSolicitanteRS());
			
			if (idSolicitante == null)
				idSolicitante = dao.registrar(comercio.getCuit(), comercio.getSolicitanteRS());
			else
				dao.actualizar(idSolicitante, comercio.getSolicitanteRS());
			
			boolean existe = dao.existeRelacionSolicitanteComercio(comercio.getCuit(), idSolicitante);
			
			if (!existe)
				dao.relacionar(comercio.getCuit(), idSolicitante);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}