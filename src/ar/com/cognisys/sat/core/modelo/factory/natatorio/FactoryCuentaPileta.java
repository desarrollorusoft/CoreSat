package ar.com.cognisys.sat.core.modelo.factory.natatorio;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;

public class FactoryCuentaPileta {

	public static CuentaPileta generarInstancia() {
		CuentaPileta un = new CuentaPileta();
		return un;
	}
	
	public static CuentaPileta generarInstancia(String nombre, String apellido, BigDecimal numeroDocumento,
                                                TiposDocumento tipoDocumento, String sede, Integer nroSocio,
                                                Integer id, Long comprobante, Float deuda, Date vencimiento) {
		
		CuentaPileta un = generarInstancia();
		un.setApellido(apellido);
		un.setNombre(nombre);
		un.setNumeroDocumento(numeroDocumento);
		un.setSede(sede);
		un.setTipoDocumento(tipoDocumento);
		un.setComprobante(comprobante);
		un.setDeuda(deuda);
		un.setId(id);
		un.setNroSocio(nroSocio);
		un.setVencimiento(vencimiento);
		un.setDescripcion((nombre + " " + apellido).toUpperCase());
		
		return un;
	}
}