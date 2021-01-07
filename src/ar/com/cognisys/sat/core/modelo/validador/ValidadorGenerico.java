package ar.com.cognisys.sat.core.modelo.validador;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Perfil;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.excepcion.DatosIncompletosException;
import ar.com.cognisys.sat.core.modelo.excepcion.EmailInvalidoException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.NoHayPerfilesAsignadosException;
import ar.com.cognisys.sat.core.modelo.excepcion.PasswordsDistintasException;

public abstract class ValidadorGenerico {

	public static void validarCorreo(String correo) throws ExcepcionControladaAlerta {
		
		if (! correo.matches("[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
			throw new EmailInvalidoException();
		}
	}

	public static void validarClaves(String clave, String claveRepetida) throws ExcepcionControladaAlerta {
		
		if (! clave.equals(claveRepetida)) {
			throw new PasswordsDistintasException();
		}
	}

	public static void validarPerfiles(List<Perfil> listaPerfiles) throws ExcepcionControladaAlerta {
		
		if (listaPerfiles == null || listaPerfiles.isEmpty()) {
			throw new NoHayPerfilesAsignadosException();
		}
	}

	public static void validarCambioClave(String claveAnterior, String claveNueva, String claveNuevaRepeticion) throws ExcepcionControladaAlerta {
		
		validarClaves(claveNueva, claveNuevaRepeticion);
	}
	
	@Deprecated
	public static void validarClaveActual(String clave, Usuario usuario) throws ExcepcionControladaAlerta {
		
//		if (usuario.getClave().equals(SegurizadorClaves.codificar(clave))) {
//			throw new ClaveInvalidaException();
//		}
	}

	public static void validarCambioClave(String cuit, String clave) throws ExcepcionControladaAlerta {
		if (!(cuit != null && !cuit.equals("") && clave != null && !clave.equals("")))
			throw new DatosIncompletosException();
	}
	
	/**
	 * Metodo para el HSAT valida si el usuario tiene cuit para poder hacer el reset de clave
	 * @param cuit
	 * @throws ExcepcionControladaAlerta
	 */
	public static boolean validarCUIT( String cuit  ) throws ExcepcionControladaAlerta {
		return validar(cuit);
	}

	private static boolean validar(String cuit) {
		if ( cuit != null && cuit.length() == 11 ) {
			String c12 = "" + cuit.charAt(0) + cuit.charAt(1);
			if ( c12.equals("20") ||
			     c12.equals("23") ||
			     c12.equals("24") ||
			     c12.equals("27") ||
			     c12.equals("30") ||
			     c12.equals("33") ||
			     c12.equals("34")) {
				Integer contador = ( cuit.charAt(0) * 5 ) +
						           ( cuit.charAt(1) * 4 ) +
						           ( cuit.charAt(2) * 3 ) +
						           ( cuit.charAt(3) * 2 ) +
						           ( cuit.charAt(4) * 7 ) +
						           ( cuit.charAt(5) * 6 ) +
						           ( cuit.charAt(6) * 5 ) +
						           ( cuit.charAt(7) * 4 ) +
						           ( cuit.charAt(8) * 3 ) +
						           ( cuit.charAt(9) * 2 ) +
						           ( cuit.charAt(10)* 1 ) ;
				Integer division = contador / 11;
				if ( division == Math.floor(division) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		validar("20352435784");
	}
}