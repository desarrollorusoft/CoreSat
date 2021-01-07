package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.core.modelo.comun.tramite.TramitePagoMultiple;
import ar.com.cognisys.sat.core.modelo.comun.tramite.pagoMultiple.PagoMultiple;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.FactoryArchivo;
import ar.com.cognisys.sat.core.modelo.factory.tramite.FactoryTramitePagoMultiple;
import ar.com.cognisys.sat.core.modelo.factory.tramite.pagoMultiple.FactoryPagoMultiple;

public class DaoTramitePagoDoble extends Dao {

	public DaoTramitePagoDoble(Connection con) {
		super(con);
	}

	public List<TramitePagoMultiple> recuperarTodos() throws ExcepcionControladaError {
		
		List<TramitePagoMultiple> lista = new ArrayList<TramitePagoMultiple>(); 
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( Queries.BUSCAR_TRAMITE_PAGO_DOBLE.getSql() );
			rs = ps.executeQuery();
			
			while (rs.next()) {
				lista.add( FactoryTramitePagoMultiple.generarInstancia(rs.getLong("ID_TRAMITE_PAGO_MULTIPLE"),
																	   rs.getString("CUENTA"), 
																	   rs.getString("PERIODO"), 
																	   rs.getString("TRIBUTO"), 
																	   this.recuperarPagoMultiple(rs.getLong("ID_TRAMITE_PAGO_MULTIPLE")), 
																	   rs.getString("ESTADO"),
																	   rs.getDate("FECHA_CREACION"),
																	   rs.getDate("FECHA_ACTUALIZACION"),
																	   rs.getString("CORREO")) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
		}
		
		return lista;
	}

	private List<PagoMultiple> recuperarPagoMultiple(Long idTramite) throws ExcepcionControladaError {

		List<PagoMultiple> lista = new ArrayList<PagoMultiple>(); 
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( Queries.BUSCAR_PAGO_DOBLE.getSql() );
			ps.setLong(1, idTramite);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				lista.add( FactoryPagoMultiple.generarInstancia(rs.getLong("ID_PAGO_MULTIPLE"),
															    rs.getFloat("IMPORTE"), 
															    rs.getDate("FECHA"), 
															    rs.getLong("NUMERO_COMPROBANTE"), 
															    rs.getString("LUGAR_PAGO"), 
															    this.recuperarArchivo(rs.getLong("ID_PAGO_MULTIPLE"))) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
		}
		
		return lista;
	}

	private List<Archivo> recuperarArchivo(Long idPagoMultiple) throws ExcepcionControladaError {
		
		List<Archivo> lista = new ArrayList<Archivo>(); 
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement( Queries.BUSCAR_ARCHIVO_PAGO_DOBLE.getSql() );
			ps.setLong(1, idPagoMultiple);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				lista.add( FactoryArchivo.generarInstancia(rs.getLong("ID_ARCHIVO"),
														   rs.getString("NOMBRE"), 
														   rs.getString("TIPO_CONTENIDO"),
														   rs.getString("RUTA")) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
		}
		
		return lista;
	}
}