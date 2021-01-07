package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import java.util.Date;
import java.util.HashMap;

import ar.com.cognisys.sat.core.administrador.AdministradorTracePagoOnline;
import ar.com.cognisys.sat.core.modelo.enums.MediosPago;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.pago.FactoryDatosPago;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorPagosOnline;

public class GeneradorPagoInterbanking extends GeneradorPagosOnline {

	private String correo;
	
	public GeneradorPagoInterbanking(String cuenta, String numeroComprobante, Date fechaVencimiento, Float monto, 
									 String tasa, String retorno, String correo, String titular) {
		
		super(cuenta, numeroComprobante, fechaVencimiento, monto, tasa, retorno);
		this.setCorreo(correo);
		this.setTitular(titular);
	}
	
	@Override
	public PaquetePago emitirPago(Sistema sistema) throws ExcepcionControladaError {
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		String token = this.generarToken(sistema);

		if (token == null || "".equals(token)) {
			throw new ExcepcionControladaError("No se pudo generar el token de seguridad", null);
		}
		
		String url = "";
		
		if (sistema == Sistema.SAT) {
			url = "https://www.vicentelopez.gov.ar/interbanking/interbanking.MVL.App/PagosB2B.aspx?token=_TOKEN_&retorno=_RETORNO_";
		} else if (sistema == Sistema.SAT_Mobile) {
			url = "https://www.vicentelopez.gov.ar/interbanking/interbanking.MVL.Mobile/pagosB2B.aspx?token=_TOKEN_&retorno=_RETORNO_";
		}
		
		url = url.replace("_TOKEN_", token);
		url = url.replace("_RETORNO_", this.getRetorno());

		try {
			AdministradorTracePagoOnline.registrarPago(url, this.getMedioPago());
		} catch (Exception e) {
			// Esto tiene que llamar al Logger
		}
		
		parametros.put("URL", url);
				
		return (new PaquetePago((new URLsPago()).getURL_INTERBANKING(), parametros));
	}

	private String generarToken(Sistema sistema) throws ExcepcionControladaError {
		
		String url = "";
		
		if (sistema == Sistema.SAT) {
			url = "https://www.vicentelopez.gov.ar/interbanking/interbanking.MVL.Json/Services/Interbanking.ashx?data=0";
		} else if (sistema == Sistema.SAT_Mobile) {
			url = "https://www.vicentelopez.gov.ar/interbanking/interbanking.MVL.Json.Mobile/Services/interbanking.ashx?data=0";
		}
		
		url = url
		    + "&tasa=_TASA_"
		    + "&cuenta=_CUENTA_"
		    + "&razon=_RAZON_"
		    + "&recibo=_RECIBO_"
		    + "&fecha=_FECHA_" 
		    + "&valor=_VALOR_" 
		    + "&mail=_MAIL_";
			
		url = url.replace("_TASA_", this.getTasa());
		url = url.replace("_CUENTA_", this.getCuenta());
		url = url.replace("_RAZON_", this.getTitular());
		url = url.replace("_RECIBO_", this.getNumeroComprobante());
		url = url.replace("_FECHA_", super.formatearFechaBarras(this.getFechaVencimiento()));
		url = url.replace("_VALOR_", super.ajustarMonto(this.getMonto()));
		url = url.replace("_MAIL_", this.getCorreo());
		
		try {
			AdministradorTracePagoOnline.registrarPago(FactoryDatosPago.generarInstancia(url, 
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
		
		return Token.obtenerToken(url.replace(" ", "%20"));
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@Override
	public MediosPago getMedioPago() {
		return MediosPago.INTERBANKING;
	}
}