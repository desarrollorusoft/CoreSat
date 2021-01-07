package ar.com.cognisys.sat.core.modelo.factory.deudas;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;

public class FactoryCuota {

	public static Cuota generarIntanciaVacia() {
		
		Cuota cuota = new Cuota();
		
		return cuota;
	}
	
	public static Cuota generarIntanciaCompleta(String tasa, String estado, Date fechaVencimiento, String periodo, 
												Float capital, Float multa, Float recargo, Float total,Integer numeroEstado, Integer numeroOrigen, Integer numeroTasa) {
		
		Cuota cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setEstado(estado);
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		cuota.setTotal(total);
		cuota.setNumeroEstado(numeroEstado);
		cuota.setNumeroOrigen(numeroOrigen);
		cuota.setNumeroTasa(numeroTasa);
		
		return cuota;
	}
	
	public static Cuota generarIntanciaCompleta(String tasa, String estado, Date fechaVencimiento, String periodo, 
												Float capital, Float multa, Float recargo, Float total,Integer numeroEstado, Integer numeroOrigen, Integer numeroTransaccion,
												boolean mora) {
		
		Cuota cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setEstado(estado);
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		cuota.setTotal(total);
		cuota.setNumeroEstado(numeroEstado);
		cuota.setNumeroOrigen(numeroOrigen);
		cuota.setNumeroTransaccion(numeroTransaccion);
		cuota.setMora(mora);
		
		return cuota;
	}
	
	public static Cuota generarIntanciaCompleta(String tasa, String estado, Date fechaVencimiento, String periodo, 
												Float capital, Float multa, Float recargo, Float total, Integer numeroTransaccion, 
												boolean mora,Integer numeroEstado, Integer numeroOrigen, Integer numeroTasa) {
		
		Cuota cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setEstado(estado);
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		cuota.setTotal(total);
		cuota.setNumeroTransaccion(numeroTransaccion);
		cuota.setNumeroTasa(numeroTasa);
		cuota.setMora(mora);
		cuota.setNumeroEstado(numeroEstado);
		cuota.setNumeroOrigen(numeroOrigen);
		
		return cuota;
	}


	public static Cuota generarIntanciaCompleta(String tasa, String estado, Date fechaVencimiento, String periodo,
												Float capital, Float multa, Float recargo, Float total, Integer numeroTransaccion,
												boolean mora, Integer numeroTasa) {

		Cuota cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setEstado(estado);
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		cuota.setTotal(total);
		cuota.setNumeroTransaccion(numeroTransaccion);
		cuota.setNumeroTasa(numeroTasa);
		cuota.setMora(mora);

		return cuota;
	}

	
	public static Cuota generarIntanciaAutomotoresCompleta(String tasa, String estado, Date fechaVencimiento, String periodo, 
														   Float capital, Float multa, Float recargo) {
		
		Cuota cuota = generarIntanciaVacia();
		cuota.setTasa(tasa);
		cuota.setPeriodo(periodo);
		cuota.setEstado(estado);
		cuota.setFechaVencimiento(fechaVencimiento);
		cuota.setCapital(capital);
		cuota.setMulta(multa);
		cuota.setRecargo(recargo);
		
		return cuota;
	}
}