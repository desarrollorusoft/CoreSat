package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJSV;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJSV;

public class DaoDDJJSV extends DAO {

	private static final String SQL_RECUPERO_SERVICIOS_VARIOS = "Select motores, calderas \n"
															  + "From web_recau:rs_servicios_varios \n"
															  + "Where id_rs_declaracion_version = ? ";
	
	private static final String SQL_INSERT_SV = "Insert Into web_recau:rs_servicios_varios(id_rs_declaracion_version, motores, calderas) \n"
											  + "Values ( ? , ? , ? )";

	private static final String SQL_UPDATE_SV = "Update web_recau:rs_servicios_varios \n"
											  + "Set motores = ?, calderas = ?, fecha_actualizacion = current \n"
											  + "Where id_rs_declaracion_version = ? ";

	private static final String SQL_DELETE_SV = "Delete From web_recau:rs_servicios_varios \n"
											  + "Where id_rs_declaracion_version = ? ";

	public DaoDDJJSV(Connection connection) {
		super(connection);
	}

	public DDJJSV recuperar(Integer idVersion) throws ErrorRecuperacionDatosException {
		
		DDJJSV serviciosVarios = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_SERVICIOS_VARIOS );
			ps.setInt(1, idVersion);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				serviciosVarios = FactoryDDJJSV.generar(rs.getInt("motores"), rs.getInt("calderas"));
			
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return serviciosVarios;
	}

	public void guardar(Integer idVersion, DDJJSV serviciosVarios) throws ExcepcionControladaError {
		DDJJSV sv = this.recuperar(idVersion);
		
		if (sv == null)
			this.registrar(idVersion, serviciosVarios);
		else
			this.actualizar(idVersion, serviciosVarios);
	}

	private void registrar(Integer id, DDJJSV sv) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_INSERT_SV );
			ps.setInt( 1, id );
			ps.setInt( 2, sv.getMotores() );
			ps.setInt( 3, sv.getCalderas() );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void actualizar(Integer idVersion, DDJJSV sv) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_UPDATE_SV );
			ps.setInt( 1, sv.getMotores() );
			ps.setInt( 2, sv.getCalderas() );
			ps.setInt( 3, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public void borrar(Integer idVersion) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_DELETE_SV );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}