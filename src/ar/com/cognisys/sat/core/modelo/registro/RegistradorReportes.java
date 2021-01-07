package ar.com.cognisys.sat.core.modelo.registro;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Date;

import ar.com.cognisys.sat.core.correo.AdministradorMails;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeProblemasConBase;
import ar.com.cognisys.sat.core.modelo.abstracto.ExcepcionControlada;
import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;
import ar.com.cognisys.sat.core.modelo.dao.DaoReportes;
import ar.com.cognisys.sat.core.modelo.enums.Sistema;

public class RegistradorReportes implements Runnable {
	
	private Class<?>[] parametros;
	private Object[] objetos;
	private String nombreMetodo;
	
	public void registrarReportes(NavegacionSAT acceso, String nombre_usuario, Date fecha, Sistema sistema) {
		
		try {
			parametros = new Class[3];
			parametros[0] = String.class;
			parametros[1] = Date.class;
			parametros[2] = Sistema.class;
			
			objetos = new Object[3];
			objetos[0] = nombre_usuario;
			objetos[1] = fecha;
			objetos[2] = sistema;
			
			nombreMetodo = this.recuperarNombreMetodo(acceso);
			
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			this.enviarMail( getMensajeStackTrace(e) );
		}
	}
	
	private String recuperarNombreMetodo(NavegacionSAT acceso) {
		
		String metodo = "";
		
		if (acceso.getEtiqueta().equals("ingreso")) {
			metodo = "ingresaUsuario";
		} else if (acceso.getEtiqueta().equals("abl_consultar_deuda")) {
			metodo = "ablConsultarDeuda";
		} else if (acceso.getEtiqueta().equals("abl_boleta_de_pago")) {
			metodo = "ablBoletaPago";
		} else if (acceso.getEtiqueta().equals("abl_simulacion_ppc")) {
			metodo = "ablSimulacionPPC";
		} else if (acceso.getEtiqueta().equals("abl_plan_de_pago")) {
			metodo ="ablPlanDePago";
		} else if (acceso.getEtiqueta().equals("automotores_consultar_deuda")) {
			metodo = "automotoresConsultarDeuda";
		} else if (acceso.getEtiqueta().equals("automotores_boleta_de_pago")) {
			metodo = "automotoresBoletaPago";
		} else if (acceso.getEtiqueta().equals("automotores_simulacion_ppc")) {
			metodo = "automotoresSimulacionPPC";
		} else if (acceso.getEtiqueta().equals("automotores_plan_de_pago")) {
			metodo = "automotoresPlanPago";
		} else if (acceso.getEtiqueta().equals("abl_generacion_recibo")) {
			metodo = "ablGeneracionRecibo";
		} else if (acceso.getEtiqueta().equals("abl_pagar_interbanking")) {
			metodo = "ablPagarInterbanking";
		} else if (acceso.getEtiqueta().equals("abl_pagar_credito")) {
			metodo = "ablPagarCredito";
		} else if (acceso.getEtiqueta().equals("abl_pagar_PMC")) {
			metodo = "ablPagarPMC";
		} else if (acceso.getEtiqueta().equals("automotores_generacion_recibo")) {
			metodo = "automotoresGeneracionRecibo";
		} else if (acceso.getEtiqueta().equals("automotores_pagar_interbankig")) {
			metodo = "automotoresPagarInterbanking";
		} else if (acceso.getEtiqueta().equals("automotores_pagar_PMC")) {
			metodo = "automotoresPagarCredito";
		} else if (acceso.getEtiqueta().equals("automotores_pagar_credito")) {
			metodo = "automotoresPagarPMC";
		
		} else if (acceso.getEtiqueta().equals("abl_simulacion_ppc_calculo")) {
			metodo =  "ablSimulacionPPCCalculado";
		} else if (acceso.getEtiqueta().equals("automotores_simulacion_ppc_calculo")) {
			metodo = "automotoresSimulacionPPCCalculado";
			
		} else if (acceso.getEtiqueta().equals("abl_plan_interbanking")) {
			metodo = "ablPlanInterbanking";
		} else if (acceso.getEtiqueta().equals("abl_plan_PMC")) {
			metodo = "ablPlanPMC";
		} else if (acceso.getEtiqueta().equals("abl_plan_credito")) {
			metodo =  "ablPlanCredito";
		} else if (acceso.getEtiqueta().equals("automotores_plan_PMC")) {
			metodo = "automotoresPlanPMC";
		} else if (acceso.getEtiqueta().equals("automotores_plan_interbanking")) {
			metodo =  "automotoresPlanInterbanking";
		} else if (acceso.getEtiqueta().equals("automotores_plan_credito")) {
			metodo = "automotoresPlanCredito";
		
		} else if (acceso.getEtiqueta().equals("cementerio_consultar_deuda")) {
			metodo = "cementerioConsultarDeuda";
		} else if (acceso.getEtiqueta().equals("cementerio_boleta_de_pago")) {
			metodo = "cementerioBoletaPago";
		} else if (acceso.getEtiqueta().equals("cementerio_simulacion_ppc")) {
			metodo = "cementerioSimulacionPPC";
		} else if (acceso.getEtiqueta().equals("cementerio_generacion_recibo")) {
			metodo = "cementerioGeneracionRecibo";
		} else if (acceso.getEtiqueta().equals("cementerio_pagar_interbanking")) {
			metodo = "cementerioPagarInterbanking";
		} else if (acceso.getEtiqueta().equals("cementerio_pagar_credito")) {
			metodo = "cementerioPagarCredito";
		} else if (acceso.getEtiqueta().equals("cementerio_pagar_PMC")) {
			metodo = "cementerioPagarPMC";
		} else if (acceso.getEtiqueta().equals("cementerio_simulacion_ppc_calculo")) {
			metodo =  "cementerioSimulacionPPCCalculado";
		
		} else if (acceso.getEtiqueta().equals("natatorios_ingreso")) {
			metodo = "natatoriosIngreso";
		} else if (acceso.getEtiqueta().equals("natatorios_generacion_recibo")) {
			metodo = "natatoriosGeneracionRecibo";
		
		} else if (acceso.getEtiqueta().equals("pago_multiple_ingreso")) {
			metodo = "pagoMultipleIngreso";
		} else if (acceso.getEtiqueta().equals("pago_multiple_generacion")) {
			metodo = "pagoMultipleGeneracion";
		
		} else if (acceso.getEtiqueta().equals("abl_pagar_link_pagos")) {
			metodo = "ablPagarLinkPagos";
		} else if (acceso.getEtiqueta().equals("automotores_pagar_link_pagos")) {
			metodo = "automotoresPagarLinkPagos";
		} else if (acceso.getEtiqueta().equals("cementerio_pagar_link_pagos")) {
			metodo = "cementerioPagarLinkPagos";

		} else if (acceso.getEtiqueta().equals("abl_pagar_mp")) {
			metodo = "ablPagarMercadoPago";
		} else if (acceso.getEtiqueta().equals("automotores_pagar_mp")) {
			metodo = "automotoresPagarMercadoPago";
		} else if (acceso.getEtiqueta().equals("cementerio_pagar_mp")) {
			metodo = "cementerioPagarMercadoPago";
		} else if (acceso.getEtiqueta().equals("rs_pagar_mp")) {
			metodo = "rsPagarMercadoPago";
		} else if (acceso.getEtiqueta().equals("abl_plan_mp")) {
			metodo = "ablPlanMercadoPago";
		} else if (acceso.getEtiqueta().equals("automotores_plan_mp")) {
			metodo = "automotoresPlanMercadoPago";
		} else if (acceso.getEtiqueta().equals("cementerio_plan_mp")) {
			metodo = "cementerioPlanMercadoPago";
		} else if (acceso.getEtiqueta().equals("rs_plan_mp")) {
			metodo = "rsPlanMercadoPago";

		} else if (acceso.getEtiqueta().equals("rs_consultar_deuda")) {
			metodo = "rsConsultarDeuda";
		} else if (acceso.getEtiqueta().equals("rs_boleta_de_pago")) {
			metodo = "rsBoletaPago";
		} else if (acceso.getEtiqueta().equals("rs_generacion_recibo")) {
			metodo = "rsGeneracionRecibo";
		} else if (acceso.getEtiqueta().equals("rs_pagar_interbanking")) {
			metodo = "rsPagarInterbanking";
		} else if (acceso.getEtiqueta().equals("rs_pagar_credito")) {
			metodo = "rsPagarCredito";
		} else if (acceso.getEtiqueta().equals("rs_pagar_PMC")) {
			metodo = "rsPagarPMC";
		} else if (acceso.getEtiqueta().equals("rs_pagar_link_pagos")) {
			metodo = "rsPagarLinkPagos";
		} else if (acceso.getEtiqueta().equals("rs_plan_interbanking")) {
			metodo = "rsPlanInterbanking";
		} else if (acceso.getEtiqueta().equals("rs_plan_credito")) {
			metodo = "rsPlanCredito";
		} else if (acceso.getEtiqueta().equals("rs_plan_PMC")) {
			metodo = "rsPlanPMC";
		} else if (acceso.getEtiqueta().equals("rs_plan_link_pagos")) {
			metodo = "rsPlanLinkPagos";
		} else if (acceso.getEtiqueta().equals("cementerio_plan_interbanking")) {
			metodo = "cementerioPlanInterbanking";
		} else if (acceso.getEtiqueta().equals("cementerio_plan_credito")) {
			metodo = "cementerioPlanPMC";
		} else if (acceso.getEtiqueta().equals("cementerio_plan_PMC")) {
			metodo = "cementerioPlanCredito";
		} else if (acceso.getEtiqueta().equals("cementerio_plan_link_pagos")) {
			metodo = "cementerioPlanLinkPagos";
		} else if (acceso.getEtiqueta().equals("rs_simulacion_ppc")) {
			metodo = "rsSimulacionPPC";
		} else if (acceso.getEtiqueta().equals("rs_simulacion_ppc_calculo")) {
			metodo = "rsSimulacionPPCCalculado";
		}
		
		return metodo;
	}

	public void run() {
		
		try {
			Class<?> cls = Class.forName("ar.com.cognisys.sat.core.modelo.dao.DaoReportes");
			
			Constructor<?> constructor = cls.getConstructor(new Class[] { Connection.class });
			DaoReportes instanciaDao = (DaoReportes) constructor.newInstance(new Object[] { null });
			
			Method method = cls.getDeclaredMethod(nombreMetodo, parametros);
			method.invoke(instanciaDao, objetos[0], objetos[1], objetos[2]);
		} catch (Exception e) {
			this.enviarMail( e.getMessage() + "\n\n" + getMensajeStackTrace(e) + "\n\n"
						   + "Datos:\n"
						   + "\t- Metodo:\t["+nombreMetodo+"]\n"
						   + "\t- Usuario:\t["+objetos[0]+"]\n"
						   + "\t- Fecha:\t["+objetos[1].toString()+"]\n"
					   	   + "\t- Sistema:\t["+objetos[2].toString()+"]" );
		}
	}
	
	public static String getMensajeStackTrace(Throwable e) {
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		return stringWriter.toString();
	}
	
	private void enviarMail(String mensaje) {
		try {
			AdministradorMails.enviar( new MensajeProblemasConBase(mensaje) );
		} catch (ExcepcionControlada e1) {
		}
	}
}