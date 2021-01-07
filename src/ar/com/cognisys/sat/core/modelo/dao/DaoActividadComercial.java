package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividadComercial;

public class DaoActividadComercial extends Dao {

	private static final String SQL_RECUPERO_ACTIVIDADES = "Select c_sub_cod as codigo, trim(d_sub_cod) as nombre, 1 as aceptado\n"
														 + "From codificaciones c\n"
														 + "Where c_codificacion = 5\n"
														 + "And c_sub_cod not in(0,1)\n" 
														 + "and not exists ( select 1\n" 
														 + "			  from rubro_sin_rs r\n" 
														 + "			  where c.c_sub_cod = r.c_rubro\n"
														 + "			  and r.n_ano = ? )\n"
														 + "and c_sub_cod != 999\n"
														 + "union\n"
														 + "Select c_sub_cod as codigo, trim(d_sub_cod) as nombre, 0 as aceptado\n" 
														 + "From codificaciones c\n"
														 + "Where c_codificacion = 5\n" 
														 + "And c_sub_cod not in(0,1)\n"
														 + "and exists ( select 1\n" 
														 + "		   from rubro_sin_rs r\n" 
														 + "		   where c.c_sub_cod = r.c_rubro\n"
														 + "		   and r.n_ano = ? )\n"
														 + "and c_sub_cod != 999\n"
														 + "order by 2";

	public DaoActividadComercial(Connection connection) {
		super(connection);
	}

	public Map<Integer, List<ActividadComercial>> recuperar() throws ExcepcionControladaError {

		Map<Integer, List<ActividadComercial>> mapa = new HashMap<Integer, List<ActividadComercial>>();

		int anoActual = Calendar.getInstance().get(Calendar.YEAR);
		for (int ano = 2018; ano <= anoActual; ano++)
			mapa.put(ano, this.recuperar( ano ));
		
		return mapa;
	}

	private List<ActividadComercial> recuperar(Integer ano) throws ExcepcionControladaError {

		List<ActividadComercial> lista = new ArrayList<ActividadComercial>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERO_ACTIVIDADES );
			ps.setInt(1, ano);
			ps.setInt(2, ano);
			rs = ps.executeQuery();

			while ( rs.next() )
				lista.add( FactoryActividadComercial.generar( rs.getInt("codigo"), rs.getString("nombre"), rs.getInt("aceptado") == 1 ) );

		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}
}