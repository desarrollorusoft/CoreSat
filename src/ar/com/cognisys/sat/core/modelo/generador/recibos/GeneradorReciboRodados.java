package ar.com.cognisys.sat.core.modelo.generador.recibos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorRecibosComun;

public class GeneradorReciboRodados extends GeneradorRecibosComun {

	public GeneradorReciboRodados(Cuenta cuenta, String numeroComprobante, Date fechaVencimiento, 
								  Float monto, List<Cuota> listaCuotas) {
		
		super(cuenta, numeroComprobante, fechaVencimiento, monto, listaCuotas);
	}

	@Override
	public void generarRecibo() throws ExcepcionControladaError {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("CUENTA", this.getMiCuenta().getDominio());
        params.put("TITULAR", this.getCuenta().getContribuyente().getNombreApellido());
        params.put("DOMICILIO", this.getCuenta().getContribuyente().getDomicilio().toString());
        params.put("COMPROBANTE", this.getNumeroComprobante().toString());
        params.put("VENCIMIENTO", super.formatearFecha(this.getFechaVencimiento()));
        params.put("IMPORTETOTAL", super.formatearDinero(this.getMonto()));
        params.put("FECHAEMISION", super.formatearFecha(new Date()));
        params.put("CODIGOBARRASCOMPLETO", super.generarImagenCodigoBarras(this.generarCodigoBarras("300")));
        params.put("CODIGOBARRASCHICO", super.generarImagenCodigoBarras( this.getNumeroComprobante().toString() ));
        params.put("MARCA", this.getMiCuenta().getMarca());
		params.put("MODELO", this.getMiCuenta().getModelo());
		params.put("CILINDRADA", this.getMiCuenta().getCilindrada());
        
        params.put("IMGCABEZA", super.recuperarPathImagenReporte("header-logo.jpg"));
        params.put("TIJERAS", super.recuperarPathImagenReporte("tijeras.png"));
        params.put("REPORTEUP", super.recuperarPathArchivoReporte("reporte_rodados_vencido_subreport2.jasper"));
        params.put("REPORTEDOWN", super.recuperarPathArchivoReporte("rodadosVencido_subreportprueba.jasper"));
        params.put("DATA", super.generarDataSource(this.getListaCuotas()));
        params.put("DATA2", super.generarDataSource(this.getListaCuotas()));
        params.put("SALTODEPAGINA", this.habilitarSaltoPaginaPDF());

        super.generarArchivo(Integer.toString(new Date().hashCode()), params, super.recuperarPathArchivoReporte("reporte_rodados_vencido.jasper"));
	}

	public CuentaRodados getMiCuenta() {
		
		return (CuentaRodados) this.getCuenta();
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