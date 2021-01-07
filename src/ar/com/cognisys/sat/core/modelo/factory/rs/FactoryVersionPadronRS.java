package ar.com.cognisys.sat.core.modelo.factory.rs;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2018;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;

public class FactoryVersionPadronRS {

	public static VersionPadronRS generar(int ano) {
		
		VersionPadronRS version = null;
		
		if (ano <= 2018)
			version = new VersionPadronRS2018();
		else if (ano == 2019)
			version = new VersionPadronRS2019();
		else if (ano == 2020)
			version = new VersionPadronRS2020();
		
		return version;
	}
	
	public static VersionPadronRS generar(int ano, Integer id, Integer numero, Integer cantidadPersonas, String cuentaABL,
										  Float facturacion, Date fechaHabilitacion, Date fechaActualizacion, boolean completo) {
		
		VersionPadronRS version = generar(ano);
		version.setId(id);
		version.setVersion(numero);
		version.setCantidadPersonas(cantidadPersonas);
		version.setCompleto(completo);
		version.setCuentaABL(cuentaABL);
		version.setFacturacion(facturacion);
		version.setFechaHabilitacion(fechaHabilitacion);
		version.setFechaActualizacion(fechaActualizacion);
		
		return version;
	}

	public static VersionPadronRS generarVacio(Integer ano) {
		
		Date fechaHabilitacion = null;
		
		try {
			fechaHabilitacion = (new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1900"));
		} catch (Exception e) {
			fechaHabilitacion = new Date();
		}
		
		return generar(ano, null, 0, 0, "0", 0f, fechaHabilitacion, (new Date()), false);
	}
}