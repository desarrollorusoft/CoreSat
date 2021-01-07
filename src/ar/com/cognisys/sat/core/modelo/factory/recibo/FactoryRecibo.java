package ar.com.cognisys.sat.core.modelo.factory.recibo;

import java.sql.Date;

import ar.com.cognisys.sat.core.modelo.comun.recibos.Recibo;

public class FactoryRecibo {

	public static Recibo generarIntanciaVacia() {
		
		Recibo recibo = new Recibo();
		
		return recibo;
	}

	public static Recibo generarIntanciaCompleta(Integer anio, Integer cuota, Integer tasa, 
												  Date fechaVencimiento) {
		
		Recibo recibo = generarIntanciaVacia();
		recibo.setAnio(anio);
		recibo.setCuota(cuota);
		recibo.setTasa(tasa);
		recibo.setFechaVencimiento(fechaVencimiento);
		
		return recibo;
	}
}