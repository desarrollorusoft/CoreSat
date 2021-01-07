package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;

import ar.com.cognisys.sat.core.modelo.comun.pago.DatosPago;
import ar.com.cognisys.sat.core.modelo.dao.DaoTracePagos;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorTracePagoOnline {

	public static void registrarPago(String pago, MediosPago mp) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoTracePagos(con)).tracear(pago, mp);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static void registrarPago(DatosPago datos, MediosPago mp) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoTracePagos(con)).tracear(datos, mp);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}