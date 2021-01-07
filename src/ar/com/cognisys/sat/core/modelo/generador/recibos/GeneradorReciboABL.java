package ar.com.cognisys.sat.core.modelo.generador.recibos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorRecibosComun;

public class GeneradorReciboABL extends GeneradorRecibosComun {

	public GeneradorReciboABL(Cuenta cuenta, String numeroComprobante, Date fechaVencimiento, 
							  Float monto, List<Cuota> listaCuotas) {
		
		super(cuenta, numeroComprobante, fechaVencimiento, monto, listaCuotas);
	}

	@Override
	public void generarRecibo() throws ExcepcionControladaError {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
        params.put("CUENTA", this.getCuenta().getNumero().toString());
        //Catastro
        params.put("C",  this.getMiCuenta().getCatastro().getC());
		params.put("S",  this.getMiCuenta().getCatastro().getS());
		params.put("F",  this.getMiCuenta().getCatastro().getF());
		params.put("M",  this.getMiCuenta().getCatastro().getM());
		params.put("LM", this.getMiCuenta().getCatastro().getLm());
		params.put("P",  this.getMiCuenta().getCatastro().getP());
		params.put("LP", this.getMiCuenta().getCatastro().getLp());
		params.put("UC", this.getMiCuenta().getCatastro().getUc());
		params.put("UF", this.getMiCuenta().getCatastro().getUf());
		params.put("P2", this.getMiCuenta().getCatastro().getP2());
        params.put("TITULAR",this.getCuenta().getContribuyente().getNombreApellido());
        params.put("DOMICILIO", this.getCuenta().getDescripcion());
        params.put("COMPROBANTE", this.getNumeroComprobante().toString());
        params.put("VENCIMIENTO", super.formatearFecha(this.getFechaVencimiento()));
        params.put("IMPORTETOTAL", super.formatearDinero(this.getMonto()));
        params.put("FECHAEMISION", super.formatearFecha(new Date()));
        params.put("CODIGOBARRASCOMPLETO", super.generarImagenCodigoBarras( this.generarCodigoBarras("100") ));
        params.put("CODIGOBARRASCHICO", super.generarImagenCodigoBarras( this.getNumeroComprobante().toString() ));
        params.put("IMGCABEZA", super.recuperarPathImagenReporte("header-logo.jpg"));
        params.put("TIJERAS", super.recuperarPathImagenReporte("tijeras.png"));
        params.put("REPORTEUP", super.recuperarPathArchivoReporte("reporte_abl_vencido_subreport2.jasper"));
        params.put("REPORTEDOWN", super.recuperarPathArchivoReporte("ablvencido_subreportprueba.jasper"));
        params.put("DATA", super.generarDataSource(this.getListaCuotas()));
        params.put("DATA2", super.generarDataSource(this.getListaCuotas()));
        params.put("SALTODEPAGINA", this.habilitarSaltoPaginaPDF());
        
        super.generarArchivo(Integer.toString(new Date().hashCode()), params, super.recuperarPathArchivoReporte("reporte_abl_vencido.jasper"));
	}
	
	public CuentaABL getMiCuenta() {
		return (CuentaABL) this.getCuenta();
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