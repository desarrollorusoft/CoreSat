package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.contenedor.ContenedorActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.Actividades;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryActividades;

public class DaoDDJJActividades extends DAO {

	private static final String SQL_RECUPERO_ACTIVIDADES = "Select c.c_sub_cod as codigo, trim(c.d_sub_cod) as nombre, r.flag_principal \n" 
													     + "From web_recau:rs_actividades r, recaudaciones:codificaciones c \n"
													     + "Where r.id_rs_declaracion_version = ? \n" 
													     + "And c.c_codificacion = 5 \n" 
													     + "And r.codigo_recaudaciones = c.c_sub_cod \n" 
													     + "And c.c_sub_cod not in(0,1) ";
	
	private static final String SQL_DELETE_ACTIVIDAD = "Delete From web_recau:rs_actividades Where id_rs_declaracion_version = ? ";

	private static final String SQL_INSERT_ACTIVIDAD = "Insert Into web_recau:rs_actividades(id_rs_declaracion_version, codigo_recaudaciones, flag_principal) \n"
													 + "Values ( ? , ? , ? ) ";

	public DaoDDJJActividades(Connection connection) {
		super(connection);
	}

	public Actividades recuperar(Integer idVersion, Integer ano) throws ErrorRecuperacionDatosException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Actividades actividades = FactoryActividades.generar();
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_ACTIVIDADES );
			ps.setInt( 1, idVersion );
			rs = ps.executeQuery();

			while (rs.next()) {
				ActividadComercial a = ContenedorActividadComercial.getInstancia().recuperarActividad( rs.getInt("codigo"), ano );
				
				if ( rs.getInt( "flag_principal" ) == 1 )
					actividades.setActividadPrincipal( a );
				else
					actividades.getOtrasActividades().add( a );
			}
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return actividades;
	}

	public void guardar(Integer idVersion, Actividades actividades) throws ExcepcionControladaError {
		this.borrar( idVersion );
		
		this.registrar( idVersion, actividades.getActividadPrincipal(), true );

		for ( ActividadComercial ac : actividades.getOtrasActividades() )
			this.registrar( idVersion, ac, false );
	}

	public void borrar(Integer idVersion) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_DELETE_ACTIVIDAD );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void registrar(Integer idVersion, ActividadComercial ac, boolean principal) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_INSERT_ACTIVIDAD );
			ps.setInt( 1, idVersion );
			ps.setInt( 2, ac.getCodigo() );
			ps.setInt( 3, principal ? 1 : 0 );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}