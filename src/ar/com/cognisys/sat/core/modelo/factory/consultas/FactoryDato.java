package ar.com.cognisys.sat.core.modelo.factory.consultas;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Dato;

public class FactoryDato {

	public static Dato generarInstancia(){
		return new Dato();
	}

	public static Dato generarInstancia(Long id, String nombre, Integer opcional, String expresion, String mensajeError){
		Dato d = generarInstancia();
		d.setId(id);
		d.setNombre(nombre);
		d.setOpcional(!opcional.equals(1));
		d.setExpresionRegular(expresion);
		d.setMensajeError( mensajeError );
		
		return d;
	}
}
