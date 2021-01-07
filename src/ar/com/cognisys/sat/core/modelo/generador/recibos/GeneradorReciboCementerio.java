package ar.com.cognisys.sat.core.modelo.generador.recibos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorRecibosComun;

public class GeneradorReciboCementerio extends GeneradorRecibosComun {
	
	public GeneradorReciboCementerio(Cuenta cuenta, String numeroComprobante, Date fechaVencimiento, Float monto, List<Cuota> listaCuotas) {
		super(cuenta, numeroComprobante, fechaVencimiento, monto, listaCuotas);
	}

	@Override
	public void generarRecibo() throws ExcepcionControladaError {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
        params.put("CUENTA", this.getCuenta().getNumero() + " - " + this.getCuenta().getDescripcion());
        params.put("TITULAR", this.getCuenta().getContribuyente().getNombreApellido());
        params.put("DOMICILIO", this.getCuenta().getContribuyente().getDomicilio().toString());
        params.put("COMPROBANTE", this.getNumeroComprobante());
        params.put("VENCIMIENTO", new SimpleDateFormat("dd-MM-yyyy").format(this.getFechaVencimiento()));
        params.put("IMPORTETOTAL", super.formatearDinero(this.getMonto()));
       	params.put("FECHAEMISION", super.formatearFecha(new Date()));
        params.put("CODIGOBARRASCOMPLETO", super.generarImagenCodigoBarras(super.generarCodigoBarras("600")));
        params.put("CODIGOBARRASCHICO", super.generarImagenCodigoBarras( this.getNumeroComprobante().toString() ));
        params.put("IMGCABEZA", super.recuperarPathImagenReporte("header-logo.jpg"));
        params.put("TIJERAS", super.recuperarPathImagenReporte("tijeras.png"));
        params.put("REPORTEUP", super.recuperarPathArchivoReporte("subreporte_cementerio_cabecera.jasper"));
        params.put("REPORTEDOWN", super.recuperarPathArchivoReporte("subreporte_tabla_cementerio.jasper"));
        params.put("DATA", super.generarDataSource(this.getListaCuotas()));
        params.put("DATA2", super.generarDataSource(this.getListaCuotas()));
        try {
            params.put("FECHAALTA", super.formatearFecha(this.getMiCuenta().getFechaAlta()));
		} catch (Exception e) {
			params.put("FECHAALTA", "-");
		}
        try {
            params.put("FECHAACTUALIZACION", super.formatearFecha(this.getMiCuenta().getFechaRenovacion()));
		} catch (Exception e) {
			params.put("FECHAACTUALIZACION", "-");
		}
        
        params.put("SECCION", this.getMiCuenta().getNumeroSeccion());
        params.put("TABLON", this.getMiCuenta().getTablon() + " - " + this.getMiCuenta().getLetraTablon());
        params.put("LOTE", this.getMiCuenta().getLote() + " - " + this.getMiCuenta().getLetraLote());
        params.put("SALTODEPAGINA", this.habilitarSaltoPaginaPDF());

        super.generarArchivo(Integer.toString(new Date().hashCode()), params, super.recuperarPathArchivoReporte("reporte_cementerio.jasper"));
	}
	
	public CuentaCementerio getMiCuenta() {
		return (CuentaCementerio) this.getCuenta();
	}

	@Override
	public boolean habilitarSaltoPaginaPDF() {
		if(this.getListaCuotas().size() > 6){
			return true;
		}else{
			return false;
		}
	}
}