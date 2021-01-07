package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryTipoCartel;

public class DaoCarteleria extends Dao {

	public DaoCarteleria(Connection connection) {
		super(connection);
	}
	
	public Map<Integer, List<TipoCartel>> recuperar() throws ExcepcionControladaError {
		Map<Integer, List<TipoCartel>> mapa = new HashMap<Integer, List<TipoCartel>>();
		Calendar c = Calendar.getInstance();
		
		for (int ano = 2018; ano <= c.get(Calendar.YEAR); ano++)
			mapa.put(ano, this.recuperar(ano));
		
		return mapa;
	}
	
	public List<TipoCartel> recuperar(int ano) throws ExcepcionControladaError {
		List<TipoCartel> lista = new ArrayList<TipoCartel>();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.getConnection().prepareCall("{call recaudaciones:sp_rs_devuelve_pyp_cog( ? )}");
			cs.setInt( 1, ano );
			cs.execute();
			
			rs = cs.getResultSet();
			
			if ( rs.next() ) {
				String r = rs.getString(1);
				String[] datos = r.substring(0, r.length()-2).split(",");
				
				for (int i = 0; i < datos.length; i=i+2)
					lista.add( FactoryTipoCartel.generar( Integer.parseInt(datos[i]), datos[i+1] ) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(cs, rs);
		}
		
		return lista;
	}
}