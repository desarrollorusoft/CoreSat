package ar.com.cognisys.sat.core.modelo.factory.usuario;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;

public class FactoryCaracterSAT {

	public static CaracterSAT generar() {
		
		CaracterSAT caracter = new CaracterSAT();
		
		return caracter;
	}
	
	public static CaracterSAT generar(Integer id, String clave, String descripcion) {
		
		CaracterSAT caracter = generar();
		caracter.setId(id);
		caracter.setClave(clave);
		caracter.setNombre(descripcion);
		
		return caracter;
	}
}