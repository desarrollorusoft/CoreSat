package ar.com.cognisys.sat.core.administrador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentaPileta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.PaquetePago;
import ar.com.cognisys.sat.core.modelo.generador.pagoOnline.URLsPago;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;

public class AdministradorPiletas extends Administrador {
	
	private static final long serialVersionUID = 1L;
	
	public static CuentaPileta buscarCuenta(String tipoDocumento, String documento) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			return (new DaoCuentaPileta(con)).recuperarCuenta(tipoDocumento, new BigDecimal(documento));
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
	
	public static PaquetePago generarRecibo(CuentaPileta cuenta) throws ExcepcionControladaError {
		try {
			String path = AdministradorGeneracionRecibos.generarReciboNatatorios(cuenta);
			
			String directorioFtp = AdministradorProperties.getInstancia().getPropiedad( "DIRECTORIO_RECIBOS_FTP" ) + "/" + cuenta.getNroSocio() + "/";
			
			SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-YYYY" );
			
			String nombre = cuenta.getNroSocio() + "-" + sdf.format( new Date() ) + "-" + new Date().getTime() + ".pdf";

			AdministradorArchivo.guardarEnFTP( directorioFtp , nombre, new FileInputStream( path ) );

			path = AdministradorProperties.getInstancia().getPropiedad( "ENCABEZADO_FTP_PUBLICO_DESCARGAS" ) + directorioFtp + nombre;
			
			HashMap<String, String> parametros = new HashMap<String, String>();
			parametros.put("URL", path);
			return (new PaquetePago((new URLsPago()).getURL_PMC(), parametros));
		} catch (ErrorLecturaPropertiesException e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new ExcepcionControladaError(e.getMessage(), e);
		}
	}

	@Deprecated
	public static byte[] generarReciboDatos(CuentaPileta usuario) throws ExcepcionControladaError {
		return AdministradorGeneracionRecibos.generarReciboNatatorios_Datos(usuario);
	}
}