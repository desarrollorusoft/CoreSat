package ar.com.cognisys.sat.core.modelo.generador.recibos;

import java.util.Date;
import java.util.HashMap;

import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorRecibosNatatorios;

public class GeneradorReciboNatatorios extends GeneradorRecibosNatatorios {
	
	public GeneradorReciboNatatorios(CuentaPileta usuario, String numeroComprobante, Date fechaVencimiento, Float monto) {
		super(usuario, numeroComprobante, fechaVencimiento, monto);
	}

	@Override
	public void generarRecibo() throws ExcepcionControladaError {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("NOMBREAPELLIDO", (this.getUsuario().getNombre()).trim() +" "+ (this.getUsuario().getApellido()).trim() );
        params.put("NROSOCIO", this.getUsuario().getSede() + "-" +this.getUsuario().getNroSocio().toString() );
		params.put("FECHAEMISION", super.formatearFecha(new Date()));
		params.put("IMPORTETOTAL", super.formatearDinero(super.getMonto())); 
		params.put("COMPROBANTE", super.getNumeroComprobante());
		params.put("VENCIMIENTO", super.formatearFecha(super.getFechaVencimiento()));
		params.put("CODIGOBARRASCOMPLETO", super.generarImagenCodigoBarras(super.generarCodigoBarras()));
		params.put("CODIGOBARRASCORTO", super.generarImagenCodigoBarras(this.getUsuario().getComprobante().toString()));
		params.put("IDUNICO", "ID Unico: " + this.getUsuario().getId().toString());
        params.put("DNI", this.getUsuario().getNumeroDocumento().toString());
		params.put("IMGCABEZA", super.recuperarPathImagenReporte("header-logo.jpg"));
        params.put("IMGTIJERA", super.recuperarPathImagenReporte("tijeras.png"));

        super.generarArchivo(Integer.toString(new Date().hashCode()), params, super.recuperarPathArchivoReporte("recibo_natatorio.jasper"));
	}
}