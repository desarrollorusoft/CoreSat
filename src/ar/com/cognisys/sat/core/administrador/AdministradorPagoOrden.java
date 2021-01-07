package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.carrito.PagoOrden;
import ar.com.cognisys.sat.core.modelo.dao.DaoXmlCarrito;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorPagoOrden {
	
	public static PagoOrden getPagoOrden(Integer idCarrito, Integer tipoDocumento, Integer nroDocumento) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoXmlCarrito(con)).getXmlCarrito(idCarrito, tipoDocumento, nroDocumento);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void confirmarCarrito(Integer idCarrito) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoXmlCarrito(con)).confirmarCarrito(idCarrito);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void grabarXml(Integer idOrdenPago, String xml) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoXmlCarrito(con)).grabarXml(idOrdenPago, xml);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}