package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryCaracterSAT;

public class DaoCaracterSAT extends Dao {

	private static final String SQL_RECUPERO_LISTA_RS = "select id_caracter, clave, descripcion\n"
													  + "from web_recau:caracter\n"
													  + "where flag_rs = 1 ";

	public DaoCaracterSAT(Connection connection) {
		super(connection);
	}
	
	public List<CaracterSAT> recuperarCaracteresRS() throws ExcepcionControladaError {
		
		List<CaracterSAT> lista = new ArrayList<CaracterSAT>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERO_LISTA_RS );
			rs = ps.executeQuery();
			
			while (rs.next())
				lista.add(FactoryCaracterSAT.generar(rs.getInt("id_caracter"), rs.getString("clave"), rs.getString("descripcion")));
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return lista;
	}
}