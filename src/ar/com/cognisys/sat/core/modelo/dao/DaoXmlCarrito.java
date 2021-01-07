package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.carrito.Direccion;
import ar.com.cognisys.sat.core.modelo.comun.carrito.ItemOrden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.Orden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.PagoOrden;
import ar.com.cognisys.sat.core.modelo.comun.carrito.UsuarioCarrito;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.carrito.FactoryDireccion;
import ar.com.cognisys.sat.core.modelo.factory.carrito.FactoryItemOrden;
import ar.com.cognisys.sat.core.modelo.factory.carrito.FactoryOrden;
import ar.com.cognisys.sat.core.modelo.factory.carrito.FactoryPagoOrden;
import ar.com.cognisys.sat.core.modelo.factory.carrito.FactoryUsuarioCarrito;

public class DaoXmlCarrito extends Dao {
	
	public DaoXmlCarrito(Connection connection) {
		super(connection);
	}

	public PagoOrden getXmlCarrito(Integer idCarrito, Integer tipoDocumento, Integer nroDocumento) throws ExcepcionControladaError {
		
		CallableStatement cs1 = null, cs2 = null;
		ResultSet rs1 = null, rs2 = null, rs3 = null, rs4 = null;
		PreparedStatement ps1 = null, ps2 = null, ps3 = null, ps4 = null;
		
		PagoOrden pagoOrden = null;
		
		try {
			String procedure = "{call sp_trae_carrito_pu( ? )}";
			cs1 = this.getConnection().prepareCall(procedure);
			cs1.setInt(1, idCarrito);
			
			cs1.execute();
			
			String query = " select * from tmp_carrito_det";
			ps1 = this.getConnection().prepareStatement(query);
			
			rs1 = ps1.executeQuery();
			ArrayList<ItemOrden> items = new ArrayList<ItemOrden>();
			
            while (rs1.next()) {
            	ItemOrden item = FactoryItemOrden.getInstance(
		            	Integer.toString(rs1.getInt(3)).trim(),
		            	getStringFromRs(rs1, 4),
		            	Integer.toString(rs1.getInt(5)).trim(),
		            	Float.toString(rs1.getFloat(6)).trim(),
		            	Float.toString(rs1.getFloat(7)).trim(),
		            	Integer.toString(rs1.getInt(8)).trim(),
		            	Integer.toString(rs1.getInt(9)).trim(),
		            	getStringFromRs(rs1, 10),
		            	getStringFromRs(rs1, 11));
            	
            	items.add(item);
            }
            
            query = "select * from tmp_carrito_cab";
            ps2 = this.getConnection().prepareStatement(query);
            rs2 = ps2.executeQuery();
            Orden orden = null;
            
            if (rs2.next()) {
            	orden = FactoryOrden.getInstance(Integer.toString(idCarrito).trim(),
            			getStringFromRs(rs2, 2),
						Float.toString(rs2.getFloat(3)).trim(),
						Integer.toString(rs2.getInt(4)).trim(),
						Integer.toString(rs2.getInt(5)).trim(),
						Float.toString(rs2.getFloat(6)).trim(),
						Float.toString(rs2.getFloat(7)).trim(),
						Integer.toString(rs2.getInt(8)).trim(),
						Integer.toString(rs2.getInt(9)).trim(),
						Integer.toString(rs2.getInt(10)).trim(),
						getStringFromRs(rs2, 11),
						Integer.toString(rs2.getInt(12)).trim(),
						Integer.toString(rs2.getInt(13)).trim(),
						Integer.toString(rs2.getInt(14)).trim(),
						getStringFromRs(rs2, 15),
						Integer.toString(rs2.getInt(16)).trim(),
						items);
            }
            
            procedure = "execute procedure sp_usr_regis_pu(?,?)";
            cs2 = this.getConnection().prepareCall(procedure);
			cs2.setInt(1, tipoDocumento);
			cs2.setInt(2, nroDocumento);
			cs2.execute();

            query = "select * from tmp_reg_user";
			ps3 = this.getConnection().prepareStatement(query);
			rs3 = ps3.executeQuery();
			
			UsuarioCarrito usuarioCarrito = null;
			
			if (rs3.next()) {
				usuarioCarrito = FactoryUsuarioCarrito.getInstance(getStringFromRs(rs3, 1),
						getStringFromRs(rs3, 2),
						getStringFromRs(rs3, 3),
	                	getStringFromRs(rs3, 4),
	                	getStringFromRs(rs3, 5).equals("") ? "AR_F" : getStringFromRs(rs3, 5), 
	                    getStringFromRs(rs3, 6).equals("") ? "porfavor@completar.com" : getStringFromRs(rs3, 6),
	                    getStringFromRs(rs3, 7).equals("") ? "ESP_SR" : getStringFromRs(rs3, 7),
	                    getStringFromRs(rs3, 8).equals("") ? "ESP_OTROS" : getStringFromRs(rs3, 8),
		                getStringFromRs(rs3, 9),
		                getStringFromRs(rs3, 10),
		                getStringFromRs(rs3, 11),
		                getStringFromRs(rs3, 12).equals("") ? "1960-01-1" : getStringFromRs(rs3, 12),
		                rs3.getString(13).trim(),
		                getStringFromRs(rs3, 14).equals("") ? "0" : getStringFromRs(rs3, 14));
			}
			
			query = "select * from tmp_reg_address";
			ps4 = this.getConnection().prepareStatement(query);
			rs4 = ps4.executeQuery();
			
			Direccion direccion = null;
			
			if (rs4.next()) {
				direccion = FactoryDireccion.getInstance(usuarioCarrito.getDocNo(),
														getStringFromRs(rs4, 2),
										                getStringFromRs(rs4, 3),
										                getStringFromRs(rs4, 4),
										                getStringFromRs(rs4, 5), 
										                getStringFromRs(rs4, 6),
										                getStringFromRs(rs4, 7),
										                getStringFromRs(rs4, 8),
										                getStringFromRs(rs4, 9),
										                getStringFromRs(rs4, 10),
										                getStringFromRs(rs4, 11),
										                getStringFromRs(rs4, 12),
								                		getStringFromRs(rs4, 13));
			}
			
			ArrayList<Direccion> listaDirecciones = new ArrayList<Direccion>();
			listaDirecciones.add(direccion);
			
			pagoOrden = FactoryPagoOrden.getInstance(orden, usuarioCarrito, listaDirecciones, null);
			
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs1 != null) {try {rs1.close();} catch (SQLException e) {}}
			if (rs2 != null) {try {rs2.close();} catch (SQLException e) {}}
			if (rs3 != null) {try {rs3.close();} catch (SQLException e) {}}
			if (rs4 != null) {try {rs4.close();} catch (SQLException e) {}}
			if (cs1 != null) {try {cs1.close();} catch (SQLException e) {}}
			if (cs2 != null) {try {cs2.close();} catch (SQLException e) {}}
			if (ps1 != null) {try {ps1.close();} catch (SQLException e) {}}
			if (ps2 != null) {try {ps2.close();} catch (SQLException e) {}}
			if (ps3 != null) {try {ps3.close();} catch (SQLException e) {}}
			if (ps4 != null) {try {ps4.close();} catch (SQLException e) {}}
		}
		
		return pagoOrden;
	}
	
	public String getStringFromRs(ResultSet rs, int col) throws SQLException {
        if( rs.getString(col)==null) return " ";
        return rs.getString(col).trim();
    }
	
	public void grabarXml(Integer idOrdenPago, String xml) throws ExcepcionControladaError {
		String query = "{call sp_graba_xml_pu( ? , ? , ? )}";
		CallableStatement cs = null;
		try{
			cs = this.getConnection().prepareCall(query);
			cs.setInt(1,idOrdenPago);
			cs.setString(2,"S");
			cs.setString(3,xml);
			cs.execute();
		} catch (SQLException e) {
			throw new ErrorRecuperacionDatosException(e);
		}finally{
			if (cs != null) {try {cs.close();} catch (SQLException e) {}}
		}
	}
	
	public void confirmarCarrito(Integer idCarrito) throws ExcepcionControladaError  {
		String query = "{call sp_conf_carrito( ? )}";
		CallableStatement cs = null;
		try {
			cs = this.getConnection().prepareCall(query);
			cs.setInt(1, idCarrito);
			cs.execute();
		} catch (SQLException e) {
			throw new ErrorRecuperacionDatosException(e);
		}finally{
			if (cs != null) {try {cs.close();} catch (SQLException e) {}}
		}
	}
}
