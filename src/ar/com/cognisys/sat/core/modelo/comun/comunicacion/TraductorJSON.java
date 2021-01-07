package ar.com.cognisys.sat.core.modelo.comun.comunicacion;

import com.google.gson.Gson;

public class TraductorJSON<E> {

	public String generarJSON(E objeto) {
		
		return (new Gson()).toJson(objeto);
	}
	
	public E recuperarObjeto(String json, Class<E> clase) {
		
		return (new Gson()).fromJson(json, clase);
	}
}