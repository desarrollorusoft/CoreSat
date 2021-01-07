package ar.com.cognisys.sat.core.modelo.validador;

import java.math.BigDecimal;

import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

public class ValidadorContribuyente extends ValidadorGenerico {

	public static boolean sonDatosValidos(String cuit, String clave) throws ExcepcionControladaAlerta {
		
		if (cuit == null || cuit.equals("") ||
			clave == null || clave.equals("")) {
			
			// TODO [RODRI] <- [FEDE] VALIDAR CUIT
			
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean esCorreoValido(String correo) throws ExcepcionControladaAlerta {
		
		if (sonDatosValidos(correo)) {
			
			//validarCorreo(correo);
			
			return correo.matches("[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
		} else {
			return false;
		}
	}
	
	public static boolean sonDatosValidos(String dato) throws ExcepcionControladaAlerta {
		
		if (dato == null || dato.equals("")) {
			
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validarRegistracion(String nombreTitular, String tipoDocumento, String numeroDocumento, String tipoCuenta, 
											  String cuentaDominio, String direccion, String correo) throws ExcepcionControladaAlerta {
		
		if (nombreTitular == null || nombreTitular.equals("") ||
			tipoDocumento == null || tipoDocumento.equals("") ||
			tipoCuenta == null || tipoCuenta.equals("") ||
			cuentaDominio == null || cuentaDominio.equals("") ||
			direccion == null || direccion.equals("") || direccion.length() < 10 ||
			correo == null || correo.equals("") ||
			numeroDocumento == null || numeroDocumento.toString().length() < 7) {
			
			return false;
		}
		
		validarCorreo(correo);
		
		return true;
	}

	public static boolean validarRegistracion(String cuit, String correo, String clave) throws ExcepcionControladaAlerta {

		if (clave == null || clave.equals(""))
			return false;
		
		validarCorreo(correo);
		
		return true;
	}
	
	public static boolean validarRegistracion(String cuit, String correo, String clave, String claveRepetida) throws ExcepcionControladaAlerta {

		if (clave == null || clave.equals("") || claveRepetida == null || claveRepetida.equals("")) {
			
			return false;
		}
		
		validarClaves(clave, claveRepetida);
			
		validarCorreo(correo);
		
		return true;
	}

	public static boolean validarAutenticacion(String tipoCuenta, String cuentaDominio) {

		if (tipoCuenta == null || tipoCuenta.equals("") ||
			cuentaDominio == null || cuentaDominio.equals("")) {
			
			return false;
		}
		
		return true;
	}

	public static boolean validarDatosActualizacion(String nombreUsuario, String correo, String telefono, String telefono2) {
		
		if (nombreUsuario == null || nombreUsuario.equals("") ||
			correo == null || correo.equals("")) {
			
			return false;
		}
		
		return true;
	}
	
	public static boolean validarDatosActualizacion(String correo) {
		return (correo != null && !correo.equals(""));
	}

	public static boolean validarAutenticacion(String tipoCuenta, String cuentaDominio, String tipoDocumento, String numeroDocumento) {
		
		if (tipoCuenta == null || tipoCuenta.equals("") ||
			cuentaDominio == null || cuentaDominio.equals("") || 
			tipoDocumento==null || tipoDocumento.equals("") || 
			numeroDocumento == null || numeroDocumento.equals("")) {
			
			return false;
		}
		
		return true;
	}

	public static boolean validarOlvidoDatos(String correo, String tipoCuenta, String cuentaDominio) throws ExcepcionControladaAlerta {
		
		if (tipoCuenta == null || tipoCuenta.equals("") ||
			cuentaDominio == null || cuentaDominio.equals("") || 
			correo == null || correo.equals("")) {
			
			return false;
		}
		
		validarCorreo(correo);
		
		return true;
	}

	public static boolean esDocumentoValido(String documento) {
		
		try {
			new BigDecimal(documento);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void validarDatosCuenta(TiposCuentas tipoCuenta, String cuentaDominio, String digitoVerificador) throws ExcepcionControladaAlerta {
		
		if (AdministradorCuenta.validarCuentaIngresada(cuentaDominio, tipoCuenta))
			if (tipoCuenta == TiposCuentas.ABL && (digitoVerificador == null || digitoVerificador.equals("")))
				throw new ExcepcionControladaAlerta("Para este Tributo es necesario el D�gito Verificador", null);
	}

	public static void validarDatosCuentaSinDV(TiposCuentas tipoCuenta, String cuentaDominio) throws ExcepcionControladaAlerta {
		AdministradorCuenta.validarCuentaIngresada(cuentaDominio, tipoCuenta);
	}

	public static void validarDatosCuentaSinDV(String tipoDoc, String nroDoc) throws ExcepcionControladaAlerta {
		AdministradorCuenta.validarCuentaPiletaIngresada(tipoDoc, nroDoc);
	}

	//TODO VALIDAR CORRECTAMENTE
	public static boolean esCuitValido(String cuit) {
		return cuit != null || cuit != "";
	}
}