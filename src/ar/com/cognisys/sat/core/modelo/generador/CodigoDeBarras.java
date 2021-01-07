package ar.com.cognisys.sat.core.modelo.generador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CodigoDeBarras {

	public static String generarCodigo48CaracteresCompleto(String valorFijo, String comprobante, 
														   String fechaPrimerVencimiento, String importePrimerVencimiento, 
														   String cantidadDeDias, String ImporteSegunVencimiento, 
														   String tasa, String anio, String cuota) {
		
		String codigoSinVerificacion = valorFijo +
									   completar(comprobante, 8) +
									   completar(fechaPrimerVencimiento, 8) +
									   completarImporte(importePrimerVencimiento, 8) +
									   completar(cantidadDeDias, 2) +
									   completarImporte(ImporteSegunVencimiento, 8) +
									   completar(tasa, 3) +
									   completar(anio, 4) +
									   completar(cuota, 2);
		
		return codigoSinVerificacion + generarCodigoVerificacion(codigoSinVerificacion);
	}
	
	public static String generarCodigo48CaracteresCompletoNatatorio(
			String valorFijo, String comprobante, Date vencimiento, String deuda,
			String completo, String completo2, String tasa,
			Date fechahoy) {

		String fecha = (new SimpleDateFormat("yyyyddMM")).format(vencimiento);
		String fechaHoyAnio = (new SimpleDateFormat("yyyy")).format(fechahoy);
		String fechaHoyMes = (new SimpleDateFormat("MM")).format(fechahoy);		
		String comprobanteRecorte = comprobante.substring(3);
		//110 + n_comprob (sin los 3 primeros dígitos (324)) + anio(f_vto)dia(f_vto)mes(f_vto) + i_deuda + 00 + 00000000 + 910 + anio(today)mes(today) + digito verificador
		
		String codigoSinVerificacion = 	valorFijo +  
										completar(comprobanteRecorte, 8) +
										completar(fecha, 8) +
										completarImporte(deuda, 8) +
										completo +
										completo2 +
										completar(tasa, 3) +
										fechaHoyAnio +
										completar(fechaHoyMes, 2);
		
		String codigoCompleto = codigoSinVerificacion + generarCodigoVerificacion(codigoSinVerificacion);
		
		return codigoCompleto;
	}
	
	private static String completarImporte(String dato, int cantidadPosiciones) {
		String output = dato;
		if (dato.contains(".")) {
			
			output = dato.substring(0, dato.indexOf(".")) + dato.substring(dato.indexOf(".")+1);
			
			if (dato.substring(dato.indexOf(".")+1).length() == 1) {
				output = output + 0;
			}
		}
		
		if (output.length() > cantidadPosiciones) {
			
			return output.substring(output.length() - cantidadPosiciones);
		} else {
			
			return String.format("%0" + cantidadPosiciones + "d", Integer.parseInt(output)) ;
		}
	}

	public static String completar(String dato, int cantidadPosiciones) {
	
		if (dato.contains(".")) {
			
			dato = dato.substring(0, dato.indexOf(".")) + dato.substring(dato.indexOf(".")+1);
			
			if (dato.length() == 3) {
				dato = dato + 0;
			}
		}
		
		if (dato.length() > cantidadPosiciones) {
			
			return dato.substring(dato.length() - cantidadPosiciones);
		} else {
			
			return String.format("%0" + cantidadPosiciones + "d", Integer.parseInt(dato)) ;
		}
	}
	
	private static String generarCodigoVerificacion(String codigoIncompleto) {
		
		String[] codigo = codigoIncompleto.split("");
		ArrayList<Integer> serie = recuperarSerie();
		long resultado = 0;
		
		for (int i = 0; i < codigo.length - 1; i++) {
			
			resultado += Integer.parseInt(codigo[i+1]) * serie.get(i);
		}
		
		return Long.toString(resultado).substring(Long.toString(resultado).length() -2, Long.toString(resultado).length());
	}
	
	private static ArrayList<Integer> recuperarSerie() {
		
		ArrayList<Integer> serie = new ArrayList<Integer>();
		
		for (int i = 0; i < 8; i++) {

			serie.add(9);
			serie.add(3);
			serie.add(1);
			serie.add(7);
			serie.add(9);
			serie.add(3);
		}
		
		return serie;
	}
}