package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJOEP;

public class DaoDDJJOEP extends DAO {

	private static final String SQL_RECUPERO_OEP = "Select tipo, valor \n"
												 + "From web_recau:rs_oep \n"
												 + "Where id_rs_declaracion_version = ? \n"
												 + "Order By fecha_alta ";
	
	private static final String SQL_DELETE_OEP = "Delete From web_recau:rs_oep Where id_rs_declaracion_version = ? ";
	
	private static final String SQL_INSERT_OEP = "Insert Into web_recau:rs_oep(id_rs_declaracion_version, tipo, valor) " 
											   + "Values ( ? , ? , ? )";
	
	public DaoDDJJOEP(Connection connection) {
		super(connection);
	}
	
	public List<DDJJOEP> recuperar(Integer idVersion) throws ErrorRecuperacionDatosException {
		
		List<DDJJOEP> lista = new ArrayList<DDJJOEP>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_OEP );
			ps.setInt(1, idVersion);
			
			rs = ps.executeQuery();
			
			while (rs.next())
				lista.add( FactoryDDJJOEP.generar(TipoOEP.valueOf(rs.getString("tipo")), 
												  rs.getFloat("valor")) );
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return lista;
	}

	public void guardar(Integer idVersion, List<DDJJOEP> listaOEP) throws ExcepcionControladaError {
		this.borrar( idVersion );
		
		for (DDJJOEP oep : listaOEP)
			this.registrar( idVersion, oep );
	}

	public void borrar(Integer idVersion) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_DELETE_OEP );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void registrar(Integer idVersion, DDJJOEP oep) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_INSERT_OEP );
			ps.setInt( 1, idVersion );
			ps.setString( 2, oep.getTipo().name() );
			ps.setFloat( 3, oep.getValor() );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}