package ar.com.cognisys.sat.v2.persistencia.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.ContribuyenteDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.DomicilioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.excepcion.ErrorRecuperandoDatos;

public class CementerioDAO extends DAO {

	private static final String OBTENER_DATOS = "{call spl_top_obt_cementerio_cogmvl( ? , ? )}";

	public CementerioDAO(Connection connection) {
		super( connection );
	}

	public CementerioDTO recuperarDatos(Integer numeroCuenta) throws ErrorRecuperandoDatos {
		
		CementerioDTO datos = null;
		
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = super.prepareCall( OBTENER_DATOS );

			cs.setInt( 1, numeroCuenta );
			cs.setInt( 2, 9 /* TODO [RODRI] VER DONDE UBICAR CODIGO SISTEMA */ );
			
			cs.execute();
			
			rs = cs.getResultSet();
			
			if ( rs.next() )
				datos = this.crearDatos( rs );
			
		} catch ( SQLException e ) {
			throw new ErrorRecuperandoDatos( numeroCuenta, e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
		
		return datos;
	}

	private CementerioDTO crearDatos(ResultSet rs) throws SQLException {
		ContribuyenteDTO contribuyente = this.crearContribuyente( rs );
		Integer numeroNomenclador = rs.getInt("c_cuenta_nom");
		String nomenclador = rs.getString("d_nomenclador");
		Date fechaAlta = rs.getDate("f_alta");
		Date fechaRenovacion = rs.getDate("f_renovacion");
		String lote = rs.getString("c_lote");
		String subLote = rs.getString("c_sub_lote");
		String letraLote = rs.getString("d_rep_lote");
		String tablon = rs.getString("c_tablon");
		String subTablon = rs.getString("c_sub_tablon");
		String letraTablon = rs.getString("d_rep_tablon");
		Integer numeroSeccion = rs.getInt("n_seccion");
		String subSeccion = rs.getString("c_sub_seccion");
		String deudaLegales = rs.getString("s_deuda_leg");
		// TODO [FEDE] <- [RODRI] Esta linea estaba comentada. Revisar
		// String deudaEspecial = rs.getString("s_deuda_esp");
		String deudaEspecial = null;

		return new CementerioDTO( contribuyente, numeroNomenclador, nomenclador, fechaAlta, fechaRenovacion, lote, subLote, letraLote, tablon, subTablon, letraTablon, numeroSeccion, subSeccion, deudaLegales, deudaEspecial );
	}

	private ContribuyenteDTO crearContribuyente(ResultSet rs) throws SQLException {

		String nombre = rs.getString("d_nombre");
		String apellido = rs.getString("d_apellido");
		Integer tipoDocumento = 5;
		Integer numeroDocumento = rs.getInt("n_documento");
		String correo = rs.getString("d_mail");
		String telefono = rs.getString("d_telefono");
		
		DomicilioDTO domicilio = this.crearDomicilio( rs );
		
		return new ContribuyenteDTO( nombre, apellido, tipoDocumento, numeroDocumento, correo, telefono, domicilio );
	}

	private DomicilioDTO crearDomicilio(ResultSet rs) throws SQLException {
		
		String calle = rs.getString("d_calle");
		Integer numero = rs.getInt("n_nro");
		String piso = rs.getString("c_piso");
		String departamento = rs.getString("c_dpto");
		Integer codigoPostal = rs.getInt("c_postal");
		
		return new DomicilioDTO(
				calle,
				numero,
				piso,
				departamento,
				codigoPostal);
	}

}
