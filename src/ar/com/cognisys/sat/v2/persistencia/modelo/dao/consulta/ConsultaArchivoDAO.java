package ar.com.cognisys.sat.v2.persistencia.modelo.dao.consulta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta.ConsultaArchivoDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;

public class ConsultaArchivoDAO extends DAO {

	private static final String CREAR = 
			"insert into web_recau:consulta_archivo (id_consulta_formulario, nombre, tipo_contenido, archivo_consultante, ruta) \n" + 
			"values (?, ?, ?, ?, ?) \n";
	
	private static final String RECUPERAR_POR_CONSULTA =
			"select * \n" + 
			"from web_recau:consulta_archivo \n" + 
			"where id_consulta_formulario = ? \n";
	
	private static final String ELIMINAR_ARCHIVO="delete from web_recau:consulta_archivo where ruta = ?";

	public ConsultaArchivoDAO(Connection connection) {
		super( connection );
	}
	
	public void crear(IConsultaArchivoDTO dto) throws ErrorEnBaseException {

		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( CREAR );
			
			ps.setInt( 1, dto.getIdConsultaFormulario() );
			ps.setString( 2, dto.getNombre() );
			ps.setString( 3, dto.getTipoContenido() );
			ps.setInt( 4, dto.getArchivoConsultante() );
			ps.setString( 5, dto.getRuta() );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}
	
	public Long crearArchivo( IConsultaArchivoDTO dto ) throws ErrorEnBaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = super.prepareStatement( CREAR, Statement.RETURN_GENERATED_KEYS );
			
			ps.setInt( 1, dto.getIdConsultaFormulario() );
			ps.setString( 2, dto.getNombre() );
			ps.setString( 3, dto.getTipoContenido() );
			ps.setInt( 4, dto.getArchivoConsultante() );
			ps.setString( 5, dto.getRuta() );
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if ( rs.next() )
				return rs.getLong( 1 );
			
			return null;
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
	}
	
	public List<ConsultaArchivoDTO> recuperar(Integer idConsultaFormulario) throws ErrorEnBaseException {

		List<ConsultaArchivoDTO> lista = new ArrayList<ConsultaArchivoDTO>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = super.prepareStatement( RECUPERAR_POR_CONSULTA );
			
			ps.setInt( 1, idConsultaFormulario );
			
			rs = ps.executeQuery();
			
			while ( rs.next() ) 
				lista.add( this.extraer( rs ) );
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return lista;
	}

	private ConsultaArchivoDTO extraer(ResultSet rs) throws SQLException {
		
		ConsultaArchivoDTO dto = new ConsultaArchivoDTO();
		
		dto.setId( rs.getInt( "id_consulta_archivo" ) );
		dto.setIdConsultaFormulario( rs.getInt( "id_consulta_formulario" ) );
		dto.setNombre( rs.getString( "nombre" ) );
		dto.setTipoContenido( rs.getString( "tipo_contenido" ) );
		dto.setArchivoConsultante( rs.getInt( "archivo_consultante" ) );
		dto.setRuta( rs.getString( "ruta" ) );
		
		return dto;
	}

	public void eliminar(String ruta) throws ErrorEnBaseException {
		PreparedStatement ps = null;
		
		try {
			ps = super.prepareStatement( ELIMINAR_ARCHIVO );
			
			ps.setString( 1, ruta );
			
			ps.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new ErrorEnBaseException( e.getMessage(), e );
		} finally {
			super.cerrarRecursoST( ps );
		}		
	}

}
