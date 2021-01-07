package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import ar.com.cognisys.sat.core.administrador.AdministradorTracePagoOnline;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.pago.FactoryDatosPago;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorPagosOnline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GeneradorPagoMercadoPago extends GeneradorPagosOnline {

	private String correo;

	public GeneradorPagoMercadoPago(String tasa, String cuenta, String titular, String comprobante, Date fecha, Float total, String retorno, String correo) {
		super(cuenta, comprobante, fecha, total, tasa, retorno);
		this.setTitular(titular);
		this.setCorreo(correo);
	}

	@Override
	public PaquetePago emitirPago(Sistema sistema) {
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		parametros.put("funcion", generarLLamadoAPI(sistema));
		
		return (new PaquetePago((new URLsPago()).getURL_TARJETA_CREDITO(), parametros));
	}
	
	public String generarLLamadoAPI(Sistema sistema) {
		String funcion = "";
		String fecha = ( new SimpleDateFormat("dd/MM/yyyy").format( this.getFechaVencimiento() ) );
		
		if (sistema == Sistema.SAT) {
			funcion = "mercadopagoParam('_TASA_', '_CUENTA_', '_RAZONSOCIAL_', '_RECIBO_', '_FECHA_', '_VALOR_', '_MAIL_', '_RETORNO_')";
		} else if (sistema == Sistema.SAT_Mobile) {
			funcion = "mercadopagoParam('_TASA_', '_CUENTA_', '_RAZONSOCIAL_', '_RECIBO_', '_FECHA_', '_VALOR_', '_MAIL_', '_RETORNO_')";
		}
		
		funcion = funcion.replace( "_TASA_" , this.getTasa() );
		funcion = funcion.replace( "_CUENTA_" , this.getCuenta() );
		funcion = funcion.replace( "_RAZONSOCIAL_" , super.escapeTilde(this.getTitular()) );
		funcion = funcion.replace( "_RECIBO_" , this.getNumeroComprobante() );
		funcion = funcion.replace( "_FECHA_" ,  fecha );
		funcion = funcion.replace( "_VALOR_" , super.ajustarMonto( this.getMonto() ) );
		funcion = funcion.replace( "_MAIL_" , this.getCorreo());
		funcion = funcion.replace( "_RETORNO_" , this.getRetorno() );
		
		try {
			AdministradorTracePagoOnline.registrarPago(FactoryDatosPago.generarInstancia(funcion, 
																						 this.getTasa(), 
																						 this.getCuenta(), 
																						 this.getTitular(), 
																						 this.getNumeroComprobante(), 
																						 new Date(), 
																						 this.getMonto()),
																						 this.getMedioPago());
		} catch (ExcepcionControladaError e) {
			// Esto tiene que llamar al Logger
		}
		
		return funcion;
	}
	
	@Override
	public MediosPago getMedioPago() {
		return MediosPago.MERCADO_PAGO;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}