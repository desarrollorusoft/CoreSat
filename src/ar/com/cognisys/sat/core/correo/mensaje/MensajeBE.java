package ar.com.cognisys.sat.core.correo.mensaje;

import java.util.List;

import ar.com.cognisys.sat.core.correo.MensajeCorreo;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;

public class MensajeBE extends MensajeCorreo {

	private String correo;
	private List<Cuenta> listaCuentas;
	
	public MensajeBE(String correo, List<Cuenta> listaCuentas) {
		this.correo = correo;
		this.listaCuentas = listaCuentas;
	}
	
	@Override
	public Contribuyente getRemitente() {
		return null;
	}

	@Override
	public List<Contribuyente> getDestinatarios() {
		return null;
	}

	@Override
	public String getEncabezado() {
		return "Adhesión a Boleta Electrónica";
	}

	@Override
	public String getMensaje() {
		return "Estimado Contribuyente,\n\n"
			 + "Le enviamos este correo para informarle que han registrado las adhesiones a Boleta Electrónica de las siguients cuentas: " + this.obtenerCuentas() + "."
	 		 + "El acceso a al sistema lo puede hacer ingresando a https://www.vicentelopez.gov.ar/ingresos-publicos/, o directamente a través de https://www.vicentelopez.gov.ar/SAT/pages/pub/login.xhtml \n"
	 		 + "\n\nCorreo generado automáticamente, por favor, no lo responda.";
	}

	private String obtenerCuentas() {
		String cuentas = "";
		for (Cuenta c : this.listaCuentas)
			cuentas += (cuentas.equals("") ? "" : ", ") + c.getDatoCuenta();
		return cuentas;
	}
	
	@Override
	public String getDestinatario() {
		return this.correo;
	}

	@Override
	public String getPathArchivo() {
		return null;
	}
}