package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;

public class FactoryCuentaCementerio {

	public static CuentaCementerio generarInstancia() {
		
		CuentaCementerio cuenta = new CuentaCementerio();
		
		return cuenta;
	}
	
	public static CuentaCementerio generarInstancia(Contribuyente contribuyente, String descripcion, Date fechaAlta, Date fechaRenovacion,
													String letraLote, String letraTablon, String lote, Integer numero, Integer numeroSeccion,
													String subLote, String subSeccion, String subTablon, String tablon,
													Integer numeroNomenclador, boolean baja, boolean deudaLegales) {
		
		CuentaCementerio cuenta = generarInstancia();
		cuenta.setContribuyente(contribuyente);
		cuenta.setDescripcion(descripcion == null ? "" : descripcion);
		cuenta.setFechaAlta(fechaAlta);
		cuenta.setFechaRenovacion(fechaRenovacion);
		cuenta.setLetraLote(letraLote);
		cuenta.setLetraTablon(letraTablon);
		cuenta.setLote(lote);
		cuenta.setNumero(numero);
		cuenta.setNumeroSeccion(numeroSeccion);
		cuenta.setSubLote(subLote);
		cuenta.setSubSeccion(subSeccion);
		cuenta.setSubTablon(subTablon);
		cuenta.setTablon(tablon);
		cuenta.setNumeroNomenclador(numeroNomenclador);
		cuenta.setBaja(baja);
		cuenta.setDeudaLegales(deudaLegales);
		
		return cuenta;
	}
	
	public static CuentaCementerio generarInstancia(Contribuyente contribuyente, String descripcion, Date fechaAlta, Date fechaRenovacion,
													String letraLote, String letraTablon, String lote, Integer numero, Integer numeroSeccion,
													String subLote, String subSeccion, String subTablon, String tablon,
													Integer numeroNomenclador, boolean baja, boolean deudaLegales, boolean deudaEspecial) {
		
		CuentaCementerio cuenta = generarInstancia();
		cuenta.setContribuyente(contribuyente);
		cuenta.setDescripcion(descripcion == null ? "" : descripcion);
		cuenta.setFechaAlta(fechaAlta);
		cuenta.setFechaRenovacion(fechaRenovacion);
		cuenta.setLetraLote(letraLote);
		cuenta.setLetraTablon(letraTablon);
		cuenta.setLote(lote);
		cuenta.setNumero(numero);
		cuenta.setNumeroSeccion(numeroSeccion);
		cuenta.setSubLote(subLote);
		cuenta.setSubSeccion(subSeccion);
		cuenta.setSubTablon(subTablon);
		cuenta.setTablon(tablon);
		cuenta.setNumeroNomenclador(numeroNomenclador);
		cuenta.setBaja(baja);
		cuenta.setDeudaLegales(deudaLegales);
		cuenta.setDeudaEspecial(deudaEspecial);
		
		return cuenta;
	}
}