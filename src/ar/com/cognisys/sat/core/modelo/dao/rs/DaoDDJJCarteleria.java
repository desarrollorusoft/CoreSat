package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryTipoCartel;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorCartelerias;

public class DaoDDJJCarteleria extends DAO {

	private static final String SQL_RECUPERO_CARTELERIA = "Select codigo_pyp, metros, descripcion, imagen \n"
														+ "From web_recau:rs_carteleria \n"
														+ "Where id_rs_declaracion_version = ? \n"
														+ "order by codigo_pyp ";
	
	private static final String SQL_DELETE_CARTELERIA = "Delete From web_recau:rs_carteleria Where id_rs_declaracion_version = ? ";

	private static final String SQL_INSERT_CARTELERIA = "Insert Into web_recau:rs_carteleria(id_rs_declaracion_version, codigo_pyp, metros, descripcion, imagen) \n" 
			   										  + "Values ( ? , ? , ? , ?  , ? )";

	public DaoDDJJCarteleria(Connection connection) {
		super(connection);
	}

	public List<DDJJCarteleria> recuperar(Integer idVersion, Integer ano) throws ErrorRecuperacionDatosException {
		
		List<DDJJCarteleria> lista = new ArrayList<DDJJCarteleria>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( SQL_RECUPERO_CARTELERIA );
			ps.setInt(1, idVersion);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int codigo = rs.getInt( "codigo_pyp" );
				TipoCartel tipoCartel = null;
				
				if (codigo == 0)
					tipoCartel = FactoryTipoCartel.generar(codigo, TipoCartel.OTROS);
				else
					tipoCartel = ContenedorCartelerias.getInstancia().get( ano, codigo );
				
				lista.add( FactoryDDJJCarteleria.generar( tipoCartel, rs.getFloat( "metros" ), rs.getString( "descripcion" ), rs.getString( "imagen" ) ) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
		
		return lista;
	}

	public void guardar(Integer idVersion, List<DDJJCarteleria> listaCarteleria) throws ExcepcionControladaError {
		this.borrar( idVersion );
		
		for (DDJJCarteleria c : listaCarteleria)
			this.registrar( idVersion, c );
	}

	public void borrar(Integer idVersion) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_DELETE_CARTELERIA );
			ps.setInt( 1, idVersion );

			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	private void registrar(Integer idVersion, DDJJCarteleria c) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( SQL_INSERT_CARTELERIA );
			ps.setInt( 1, idVersion );
			ps.setInt( 2, c.getTipo().getCodigo() );
			ps.setFloat( 3, c.getMetros() );
			ps.setString( 4, c.getDescripcion() );
			ps.setString( 5, c.getUrlImagen() );
			
			ps.executeUpdate();
		} catch ( Exception e ) {
			throw new ErrorActualizandoRegistracionException( e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
}