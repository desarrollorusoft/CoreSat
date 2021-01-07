package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;

public class FactoryCuenta {
	
	public static CuentaABL generarIntanciaCompletaABL(Integer numero, String descripcion) {
		
		CuentaABL cuenta = new CuentaABL();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		
		return cuenta;
	}
	
	public static CuentaABL generarIntanciaCompletaABL(Integer numero, String descripcion, String alias) {
		
		CuentaABL cuenta = new CuentaABL();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		
		return cuenta;
	}
	
	public static CuentaABL generarIntanciaCompletaABL(Integer numero, String descripcion, String alias, boolean be) {
		
		CuentaABL cuenta = generarIntanciaCompletaABL(numero, descripcion, alias);
		cuenta.setAceptaBE(be);
		
		return cuenta;
	}

	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion) {
		
		CuentaComercios cuenta = new CuentaComercios();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		
		return cuenta;
	}
	
	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion, String alias, Date fecha) {
		
		CuentaComercios cuenta = new CuentaComercios();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		cuenta.setRazonSocial(descripcion);
		cuenta.setFechaHabilitacion(fecha);
		
		return cuenta;
	}

	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion, String alias, String cuit) {

		CuentaComercios cuenta = new CuentaComercios();
		cuenta.setCuit(cuit);
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		cuenta.setRazonSocial(descripcion);

		return cuenta;
	}

	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion, String alias, Date fecha, boolean be) {
		
		CuentaComercios cuenta = generarIntanciaCompletaComercios(numero, descripcion, alias, fecha);
		cuenta.setAceptaBE(be);
		
		return cuenta;
	}

	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion, String alias, boolean be, String nroCuit) {

		CuentaComercios cuenta = generarIntanciaCompletaComercios(numero, descripcion, alias, nroCuit);
		cuenta.setAceptaBE(be);

		return cuenta;
	}

	public static CuentaComercios generarIntanciaCompletaComercios(Integer numero, String descripcion, String alias) {

		CuentaComercios cuenta = new CuentaComercios();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		cuenta.setRazonSocial(descripcion);

		return cuenta;
	}


	public static CuentaRodados generarIntanciaCompletaRodados(Integer numero, String descripcion,String dominio) {
		
		CuentaRodados cuenta = new CuentaRodados();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		
		return cuenta;
	}

	public static CuentaRodados generarIntanciaCompletaRodados(String dominio, String descripcion, String alias) {
		
		CuentaRodados cuenta = new CuentaRodados();
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		cuenta.setAlias(alias);
		
		return cuenta;
	}

	public static CuentaRodados generarCuentaMotovehiculos(Integer numeroCuenta, String dominio, String descripcion, String alias) {

		CuentaRodados cuenta = new CuentaRodados();
		cuenta.setNumero(numeroCuenta);
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		cuenta.setAlias(alias);

		return cuenta;
	}
	
	public static CuentaRodados generarCuentaMotovehiculos(Integer numeroCuenta, String dominio, String descripcion, String alias, boolean be) {

		CuentaRodados cuenta = generarCuentaMotovehiculos(numeroCuenta, dominio, descripcion, alias);
		cuenta.setAceptaBE(be);
		
		return cuenta;
	}

	public static CuentaVehiculos generarIntanciaCompletaVehiculos(Integer numero, String descripcion, String dominio) {
		
		CuentaVehiculos cuenta = new CuentaVehiculos();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		
		return cuenta;
	}

	public static CuentaVehiculos generarIntanciaCompletaVehiculos(String dominio, String descripcion, String alias) {
		
		CuentaVehiculos cuenta = new CuentaVehiculos();
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		cuenta.setAlias(alias);
		
		return cuenta;
	}
	
	public static CuentaVehiculos generarCuentaVehiculos(Integer numeroCuenta, String dominio, String descripcion, String alias) {
		
		CuentaVehiculos cuenta = new CuentaVehiculos();
		cuenta.setNumero(numeroCuenta);
		cuenta.setDescripcion(descripcion);
		cuenta.setDominio(dominio);
		cuenta.setAlias(alias);
		
		return cuenta;
	}
	
	public static CuentaVehiculos generarCuentaVehiculos(Integer numeroCuenta, String dominio, String descripcion, String alias, boolean be) {
		
		CuentaVehiculos cuenta = generarCuentaVehiculos(numeroCuenta, dominio, descripcion, alias);
		cuenta.setAceptaBE(be);
		
		return cuenta;
	}

	public static CuentaCementerio generarIntanciaCompletaCementerio(Integer numero, String descripcion, String alias) {
		
		CuentaCementerio cuenta = new CuentaCementerio();
		cuenta.setNumero(numero);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		
		return cuenta;
	}

	public static Cuenta generarInstanciaABL(Integer numero, String descripcion, String alias) {
		
		CuentaABL cuenta = new CuentaABL();
		
		cuenta.setNumero( numero );
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias( alias );
		
		return cuenta;
	}
	
	public static Cuenta generarInstanciaABL(Integer numero, String descripcion, String alias, boolean aceptaBE) {
		
		CuentaABL cuenta = new CuentaABL();
		
		cuenta.setNumero( numero );
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias( alias );
		cuenta.setAceptaBE(aceptaBE);
		
		return cuenta;
	}

	public static Cuenta generarInstanciaComercios(Integer numero, String descripcion, String alias) {
		CuentaComercios cuenta = new CuentaComercios();
		
		cuenta.setNumero( numero );
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias( alias );
		
		return cuenta;
	}
	
	public static Cuenta generarInstanciaComercios(Integer numero, String descripcion, String alias, boolean aceptaBE) {
		CuentaComercios cuenta = new CuentaComercios();
		
		cuenta.setNumero( numero );
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias( alias );
		cuenta.setAceptaBE(aceptaBE);
		
		return cuenta;
	}

	public static Cuenta generarInstanciaVehiculos(String dominio, String descripcion, String alias) {
		
		CuentaVehiculos cuenta = new CuentaVehiculos();

		cuenta.setDominio(dominio);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		
		return cuenta;
	}

	public static Cuenta generarInstanciaVehiculos(String dominio, String descripcion, String alias, boolean aceptaBE, Integer numeroCuenta) {
		
		CuentaVehiculos cuenta = new CuentaVehiculos();

		cuenta.setDominio(dominio);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		cuenta.setAceptaBE(aceptaBE);
		cuenta.setNumero(numeroCuenta);
		
		return cuenta;
	}
	
	public static Cuenta generarInstanciaRodados(String dominio, String descripcion, String alias) {
		
		CuentaRodados cuenta = new CuentaRodados();

		cuenta.setDominio(dominio);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		
		return cuenta;
	}
	
	public static Cuenta generarInstanciaRodados(String dominio, String descripcion, String alias, boolean aceptaBE, Integer numeroCuenta) {
		
		CuentaRodados cuenta = new CuentaRodados();

		cuenta.setDominio(dominio);
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias(alias);
		cuenta.setAceptaBE(aceptaBE);
		cuenta.setNumero(numeroCuenta);
		
		return cuenta;
	}

	public static CuentaCementerio generarInstanciaCementerio(Integer numero, String descripcion, String alias) {

		CuentaCementerio cuenta = new CuentaCementerio();

		cuenta.setNumero( numero );
		cuenta.setDescripcion(descripcion);
		cuenta.setAlias( alias );

		return cuenta;
	}

	public static CuentaPileta generarInstanciaPiletas(String tipoDoc, String numeroDoc, String alias) {

		CuentaPileta cuenta = new CuentaPileta();

		cuenta.setTipoDocumento( TiposDocumento.getTipoDocumentoPorNombrePiletas(tipoDoc) );
		cuenta.setNumeroDocumento( new BigDecimal(numeroDoc) );
		cuenta.setAlias( alias );

		return cuenta;
	}

    public static Cuenta generarBasica(int numeroCuenta, int codigoSistema) {
		Cuenta cuenta = null;
		switch ( TiposCuentas.recuperarTipoCuenta( codigoSistema ) ) {
			case ABL: cuenta = new CuentaABL(); break;
			case CEMENTERIO: cuenta = new CuentaCementerio(); break;
			case RODADOS: cuenta = new CuentaRodados(); break;
			case VEHICULOS: cuenta = new CuentaVehiculos(); break;
			case COMERCIOS: cuenta = new CuentaComercios(); break;
		}
		cuenta.setNumero( numeroCuenta );
		return cuenta;
    }
}
